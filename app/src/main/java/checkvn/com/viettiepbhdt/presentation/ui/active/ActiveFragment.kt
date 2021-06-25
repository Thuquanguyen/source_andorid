package checkvn.com.viettiepbhdt.presentation.ui.active

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.BuildConfig.BASE_URL
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseFragment
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.presentation.ui.personal.PersonalViewModel
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.Constants.AGENCY
import checkvn.com.viettiepbhdt.utils.Constants.ActiveType.QR_CODE
import checkvn.com.viettiepbhdt.utils.Constants.ActiveType.SERIAL
import checkvn.com.viettiepbhdt.utils.Constants.DELAY
import checkvn.com.viettiepbhdt.utils.Constants.PHONE
import checkvn.com.viettiepbhdt.utils.Constants.ProductStatus.ACTIVATED
import checkvn.com.viettiepbhdt.utils.Constants.ProductStatus.NOT_ACTIVATED
import checkvn.com.viettiepbhdt.utils.custom.*
import checkvn.com.viettiepbhdt.utils.custom.TYPE_CONFIRM
import checkvn.com.viettiepbhdt.utils.custom.TYPE_CONFIRM_WITH_INPUT
import checkvn.com.viettiepbhdt.utils.custom.TYPE_NOTIFY_WITH_IMAGE
import checkvn.com.viettiepbhdt.utils.receiver.NetworkChangeReceiver
import com.facebook.FacebookSdk.getApplicationContext
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_active.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.android.viewmodel.ext.android.viewModel


internal const val TAG_ACTIVE_FRAGMENT = "ActiveFragment"

fun newInstance(): Fragment = ActiveFragment()

class ActiveFragment :
    BaseFragment(),
    NetworkChangeReceiver.ConnectivityReceiverListener,
    ZXingScannerView.ResultHandler,
    View.OnClickListener {
    override var layoutId: Int = R.layout.fragment_active
    val viewModel: ActiveViewModel by viewModel()
    val personaViewModel: PersonalViewModel by viewModel()

    private val receiverNetwork = NetworkChangeReceiver()
    private lateinit var rxPermissions: RxPermissions
    private lateinit var mainNavigator: MainNavigator
    private var scannerView: ZXingScannerView? = null
    private var mCameraId = -1
    private lateinit var dialog: AwesomeDialog
    private lateinit var dialogConfirmActive: AwesomeDialog
    private var agencys: List<AgencyResult> = ArrayList()
    private var agencySelected = -1
    private val lastTimeScanning: Long = 0
    private var activeType: String = ""
    private var codeActive: String = ""
    private var agencyId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { mainNavigator = MainNavigator(it as MainActivity) }
        rxPermissions = RxPermissions(this)
    }

    override fun onStart() {
        super.onStart()
        context?.registerReceiver(
            receiverNetwork,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        NetworkChangeReceiver.connectivityReceiverListener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        activity?.apply {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog?.dismiss()
        }
        scannerView?.resumeCameraPreview(this)
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onStop() {
        super.onStop()
        context?.unregisterReceiver(receiverNetwork)
        activity?.apply {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private fun initView() {
        activity?.let { hideKeyBoardWhenClickOutSide(cl_container, it) }
        cifPhoneOwner.setTextInput(getPhone())
        checkCameraPermission()
        setListener()
    }

    private fun checkIsAgencyAccount() {
        ui {
            if (getRoleType() == Constants.UserRole.AGENCY) {
                visibleViews(llAgencyDefault)
                goneViews(spnAgency)
                tvAgencyName.text = getAgencyName()
            } else {
                visibleViews(spnAgency)
                goneViews(llAgencyDefault)
                personaViewModel.getListAgency()

                spnAgency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        adapterView: AdapterView<*>?, view: View?,
                        position: Int, id: Long
                    ) {
                        agencySelected = position
                        spnAgency.isEnableErrorLabel = false
                        tvAgencyError.isVisible = false
                        scannerView?.resumeCameraPreview(this@ActiveFragment);
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {}
                }
            }
        }
    }

    override fun observeData() {
        viewModel.activeResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            showResult(it)
        })

        personaViewModel.listAgencyResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleListAgencyResult(it)
        })

        viewModel.checkProductStatusResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            showProductStatusResult(it)
        })
    }

    private fun handleListAgencyResult(it: Result<ListAgencyResult>?) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    setDataAgency(it.data.data)
                } else if (!it.data.ErrMessage.isNullOrEmpty()) {
                    showToast(it.data.ErrMessage)
                }
            }
            else -> showToast(R.string.have_error_please_try_again)
        }
    }

    private fun setDataAgency(data: List<AgencyResult>) {
        if (data != null && data.isNotEmpty()) {
            agencys = data
            var list = ArrayList<String>()
            data.forEach {
                list.add(it.Name)
            }
            spnAgency.item = list
        }
    }

    private fun showResult(it: Result<ActivateResult>) {
        when (it) {
            is Result.Success -> {
                if (isShowActiveResult()) {
                    showResultDialog(it)
                } else {
                    onConfirmActiveResult(it.data)
                }
            }
            is Result.Fail -> {
                scannerView?.resumeCameraPreview(this@ActiveFragment);
                showToast(R.string.have_error_please_try_again)
            }
        }
    }

    private fun showProductStatusResult(it: Result<ProductStatusResult>?) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    if (it.data.WarrantyStatus_ID == NOT_ACTIVATED) {
                        if (getRoleType() == Constants.UserRole.AGENCY) {
                            checkActiveProduct()
                        } else {
                            if (agencyId.isNullOrEmpty() && isShowConfirmActive()) {
                                showDialogConfirmActive(AGENCY, getString(R.string.active_without_agency))
                            } else {
                                activeProduct()
                            }
                        }
                    } else {
                        activeProduct()
                    }
                } else if (!it.data.ErrMessage.isNullOrEmpty()) {
                    showToast(it.data.ErrMessage)
                    scannerView?.resumeCameraPreview(this)
                }
            }
            else -> {
                showToast(R.string.have_error_please_try_again)
                scannerView?.resumeCameraPreview(this)
            }
        }
    }

    private fun showResultDialog(result: Result.Success<ActivateResult>) {
        val dialogContent =
            getString(R.string.you_have_activated) + " " + result.data.ProductName.toLowerCase()
        dialog = AwesomeDialog(requireContext())
            .showDialog()
            .setDialogTitle(R.string.success)
            .setContent(dialogContent)
            .setDialogType(TYPE_NOTIFY_WITH_IMAGE)
            .setOnConfirmPressed {
                setShowActiveResult(!it.isCheckedNotShowAgain())
                it.dismiss()
                onConfirmActiveResult(result.data)
            }

        when (result.data.ErrCode) {
            0 -> {
                // Success do nothing
            }
            in 10..19 -> {
                // Error
                dialog.setImage(R.drawable.img_error)
                dialog.setDialogTitle(R.string.error)
                dialog.setContent(result.data.ErrMessage)
                dialog.setButtonColor(R.color.colorTextRed, requireContext())
            }
            in 20..29 -> {
                // Warning
                dialog.setImage(R.drawable.img_warning)
                dialog.setDialogTitle(R.string.warning)
                dialog.setContent(result.data.ErrMessage)
                dialog.setButtonColor(R.color.colorTextYellow, requireContext())
            }
        }
    }

    private fun onConfirmActiveResult(data: ActivateResult) {
        when (data.ErrCode) {
            0 -> {
                var qrCodeContent = data.QRCodeContent.replace("$BASE_URL/t2/", "")
//                var qrCodeContent = codeActive.replace("$BASE_URL/t2/", "")
                mainNavigator.toProductActiveDetail(getString(R.string.active), qrCodeContent, true)
            }
            in 10..19 -> {
                scannerView?.resumeCameraPreview(this@ActiveFragment);
            }
            in 20..29 -> {
                // Warning
                if (::dialog.isInitialized && dialog.isShowing) dialog.dismiss()
                var qrCodeContent = data.QRCodeContent.replace("$BASE_URL/t2/", "")
//                var qrCodeContent = codeActive.replace("$BASE_URL/t2/", "")
                mainNavigator.toProductActiveDetail(
                    getString(R.string.active),
                    qrCodeContent,
                    false
                )
            }
        }
        resetVariable()
        edt_code.setText("")
    }

    private fun resetVariable() {
        activeType = ""
        codeActive = ""
        agencyId = ""
    }

    private fun showLayoutActive() {
        visibleViews(cl_active)
        goneViews(cl_request_permission, lost_connection)
    }

    private fun showLayoutRequestCamera() {
        visibleViews(cl_request_permission)
        goneViews(cl_active, lost_connection)
    }

    private fun showLayoutNoConnection() {
        visibleViews(lost_connection)
        goneViews(cl_active, cl_request_permission)
    }

    private fun setListener() {
        toolbar.setOnClickBack(View.OnClickListener {
            (activity as MainActivity).setActionImage(true)
            activity?.onBackPressed()
        })

        toolbar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })

        btn_open_camera.setOnClickListener {
            openCamera()
        }

        edt_code.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                edt_code.clearFocus()
                var serial = edt_code.text.toString()
                if (!serial.isNullOrEmpty()) {
                    checkDataActive(SERIAL, serial)
                }
                return@setOnEditorActionListener true
            }
            false
        }

        iv_flash.setOnClickListener {
            scannerView?.let {
                it.flash = !it.flash
            }
        }

        rl_enter_code.setOnClickListener(this)

        edt_code.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            ll_info.isVisible = !hasFocus
        }

        cl_active.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> edt_code.clearFocus()
            }
            v?.onTouchEvent(event) ?: true
        }

    }

    private fun openCamera() {
        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            showChangePermissionSetting()
        } else {
            requestCameraPermission()
        }
    }

    private fun showChangePermissionSetting() {
        if (context != null) {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + requireActivity().packageName)
            )
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    @SuppressLint("CheckResult")
    private fun requestCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA).subscribe { granted ->
            if (granted) {
                showLayoutActive()
                initScannerView()
                checkIsAgencyAccount()
            } else {
                showToast(R.string.have_no_camera_permission)
            }
        }
    }

    private fun initScannerView() {
        if (scannerView == null) {
            scannerView = ZXingScannerView(context)
        }
        scannerView?.apply {
            setResultHandler(this@ActiveFragment)
            startCamera(mCameraId)
            setAutoFocus(true)
        }
        ui {
            frame_scanner.removeView(scannerView)
            frame_scanner.addView(scannerView)
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            checkCameraPermission()
        } else {
            showLayoutNoConnection()
        }
    }

    private fun checkCameraPermission() {
        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            showLayoutActive()
            delayer(300) {
                initScannerView()
                checkIsAgencyAccount()
            }
        } else {
            showLayoutRequestCamera()
        }
    }

    override fun handleResult(rawResult: com.google.zxing.Result?) {
        try {
            if (::dialog.isInitialized && dialog.isShowing) {
                scannerView?.resumeCameraPreview(this);
                return
            }
            val result = rawResult!!.text.trimIndent()
            if (result != null) {
                if (System.currentTimeMillis() - lastTimeScanning < DELAY) {
                    // Too soon after the last barcode - ignore.
                    scannerView?.resumeCameraPreview(this);
                    return
                }

                when (getScanMode()) {
                    0 -> playRingtone()
                    1 -> context?.let { vibratePhone(it) }
                }

                checkDataActive(QR_CODE, result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkDataActive(type: String, code: String) {
        if (!isLoading()) {
            activeType = type
            codeActive = code
            if (cifPhoneOwner.getTextInput().isNullOrEmpty() && isShowConfirmActive()) {
                showDialogConfirmActive(PHONE, getString(R.string.active_without_phone_number))
            } else {
                if (activeType == SERIAL || isQrCodePublish(code)) {
                    checkDataAgency()
                } else {
                    checkProductStatus()
                }
            }
        }
    }

    private fun isQrCodePublish(code: String): Boolean {
        return code.contains("$BASE_URL/t2/")
    }

    private fun checkDataAgency() {
        viewModel.phone = cifPhoneOwner.getTextInput()
        if (getRoleType() != Constants.UserRole.AGENCY) {
            if (agencySelected >= 0 && agencys.size > agencySelected) {
                agencyId = agencys[agencySelected].Agency_ID.toString()
            }
            checkProductStatus()
        } else {
            agencyId = getAgencyId()
            checkProductStatus()
        }
    }

    private fun checkProductStatus() {
        viewModel.checkProductStatus(activeType, codeActive)
    }

    private fun checkActiveProduct() {
        if ((activeType == SERIAL || isQrCodePublish(codeActive)) && getRoleType() == Constants.UserRole.AGENCY) {
            showDialogConfirmActiveForUser()
        } else {
            activeProduct()
        }
    }

    private fun activeProduct() {
        setLoading()
        if (activeType == QR_CODE) {
            viewModel.activateProductByQrCode(codeActive, agencyId)
        } else {
            viewModel.activateProductBySerial(codeActive, agencyId)
        }
    }

    private fun showDialogConfirmActiveForUser() {
        context?.let { context ->
            AwesomeDialog(context)
                .showDialog()
                .setDialogType(TYPE_CONFIRM_WITH_INPUT)
                .setDialogTitle(R.string.menu_notification)
                .setContent(getString(R.string.msg_active_for))
                .setHint(getString(R.string.enter_phone_number))
                .setInputText(cifPhoneOwner.getTextInput())
                .setInputType(InputType.TYPE_CLASS_PHONE)
                .setOnPositivePressed { dialog ->
                    dialog.dismiss()
                    if (!dialog.getInputText().isNullOrEmpty()) {
                        cifPhoneOwner.setTextInput(dialog.getInputText())
                        viewModel.phone = cifPhoneOwner.getTextInput()
                    }
                    activeProduct()
                }
                .setOnNegativePressed { dialog ->
                    dialog.dismiss()
                    scannerView?.resumeCameraPreview(this)
                }
        }

    }

    private fun showDialogConfirmActive(checkType: String, message: String) {
        if (::dialogConfirmActive.isInitialized && dialogConfirmActive.isShowing) {
            scannerView?.resumeCameraPreview(this)
            return
        }
        dialogConfirmActive = AwesomeDialog(requireContext())
            .showDialog()
            .setDialogType(TYPE_CONFIRM)
            .setImage(R.drawable.img_warning)
            .setDialogTitle(R.string.alert)
            .setContent(message)
            .setButtonColor(R.color.colorTextYellow, requireContext())
            .setOnNegativePressed {
                it.dismiss()
                scannerView?.resumeCameraPreview(this)
            }
            .setOnConfirmPressed {
                setShowConfirmActive(!it.isCheckedNotShowAgain())
                it.dismiss()
                if (checkType == PHONE) {
                    checkDataAgency()
                } else {
                    activeProduct()
                }
            }
    }

    private fun playRingtone() {
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification)
        ringtone.play()
    }

    private fun showWarningManualActive() {
        showToast(R.string.please_to_scan_qr_code)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            rl_enter_code.id -> showWarningManualActive()
        }
    }

    fun resumeCameraView() {
        scannerView?.resumeCameraPreview(this)
    }
}
