package checkvn.com.viettiepbhdt.presentation.ui.personal

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Switch
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.presentation.ui.main.MainNavigator
import checkvn.com.viettiepbhdt.utils.*
import checkvn.com.viettiepbhdt.utils.custom.AwesomeDialog
import checkvn.com.viettiepbhdt.utils.custom.TYPE_CONFIRM_WITH_INPUT
import checkvn.com.viettiepbhdt.utils.custom.TYPE_SELECTION
import checkvn.com.viettiepbhdt.utils.custom.TYPE_TEXT_INPUT
import checkvn.com.viettiepbhdt.utils.extensions.getNotBlankValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_personal.*


internal const val TAG_PERSONAL_FRAGMENT = "PersonalFragment"

fun newInstance() = PersonalFragment()

class PersonalFragment : BasePersonalFragment() {

    override var layoutId: Int = R.layout.fragment_personal
    private lateinit var switchSound: Switch
    private lateinit var swNotification: Switch
    private lateinit var swPopup: Switch
    private lateinit var scanModes: ArrayList<String>
    private lateinit var scanModeDialog: AwesomeDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            mainNavigator = MainNavigator(it as MainActivity)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        initViews()
        initData()
        initSwitchListener()
        setToolbarListener()
    }

    fun getProfile() {
        viewModel.getProfile()
    }

    override fun initToolbar() {
        toolbar = topBar
    }

    private fun initViews() {
        swNotification = layoutNotification.findViewById(R.id.switchEnd)
        switchSound = layoutSound.findViewById(R.id.switchEnd)
        swPopup = layoutPopup.findViewById(R.id.switchEnd)
    }

    fun initData() {
        handleNameView(getNotBlankValue(getFullName()))
        tvEmail.text = getNotBlankValue(getEmail())

        context?.let {
            swNotification.isChecked = NotificationManagerCompat.from(it).areNotificationsEnabled()
        }
        switchSound.isChecked = Prefs.getBoolean(Constants.SOUND, true)

        var isShowActiveResult = isShowActiveResult()
        var isShowConfirmActive = isShowConfirmActive()
        swPopup.isChecked = isShowActiveResult && isShowConfirmActive

        scanModes = resources.getStringArray(R.array.scan_mode).toCollection(ArrayList())
        btnModeScan.setTextEnd(scanModes[getScanMode()])
        loadImage(getAvatarUri(), imgAvatar)
    }

    private fun setToolbarListener() {
        topBar.setOnClickBack(View.OnClickListener {
            // Navigation to overview
            (activity as MainActivity).apply {
                setSelectedNavigation(0)
            }
        })
        topBar.setOnClickMenu(View.OnClickListener {
            (activity as MainActivity).apply {
                showDrawer()
            }
        })
    }

    private fun initSwitchListener() {
        layoutNotification.setOnClickListener {
            openAppNotificationSetting()
        }

        swNotification.setOnTouchListener { view, event ->
            swNotification.onTouchEvent(event)
            openAppNotificationSetting()
            true
        }

        switchSound.setOnCheckedChangeListener { _, isChecked ->
            savePrefs(Constants.SOUND, isChecked)
        }

        swPopup.setOnCheckedChangeListener { _, isChecked ->
            savePrefs(Constants.IS_SHOW_CONFIRM_ACTIVE, isChecked)
            savePrefs(Constants.IS_SHOW_ACTIVE_RESULT, isChecked)
        }
    }

    private fun openAppNotificationSetting() {
        context?.let {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra(Settings.EXTRA_APP_PACKAGE, it.packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    intent.putExtra("app_package", it.packageName)
                    intent.putExtra("app_uid", it.applicationInfo.uid)
                }
                else -> {
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("package:" + it.packageName)
                }
            }
            startActivity(intent)
        }
    }

    override fun initDataObserver() {
        viewModel.logoutResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleLogoutResult(it)
        })

        viewModel.profileResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleProfileResult(it)
        })
        viewModel.sendFeedbackResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleSendFeedbackResult(it)
        })
        viewModel.deleteAccountResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleDeleteAccountResult(it)
        })
    }

    private fun handleDeleteAccountResult(result: Result<CommonResult>) {
        if (result is Result.Success) {
            if (result.data.ErrCode == 0) {
                showToast(R.string.delete_account_success)
                logoutLocal()
            } else {
                showToast(result.data.ErrMessage)
            }
        } else showErroNetwork()
    }

    private fun handleProfileResult(it: Result<Profile>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    tvName.text = it.data.FullName
                    tvEmail.text = it.data.Email
                    tvRole.text = it.data.RoleName
                    tvPoint.text = it.data.Point.toString()
                    saveData(it.data)
                } else if (it.data.ErrCode == 99) {
                    // User has been delete from server
                    showToast(it.data.ErrMessage)
                    logoutLocal()
                }
            }
            is Result.Fail -> {
                logoutLocal()
            }
        }
    }

    private fun saveData(data: Profile) {
        setFullName(data.FullName)
        setPhone(data.UserName)
        var birthDate = formatDate(
            data.BirthDate,
            DateUtils.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND,
            DateUtils.DATE_FORMAT_DAY_MONTH_YEAR
        )
        setBirth(birthDate)
        setGender(data.Gender)
        setEmail(data.Email)
        setRoleType(data.RoleType)
        setRoleName(data.RoleName)
        if (data.RoleType == Constants.UserRole.AGENCY) {
            setAgencyId(data.Agency_ID)
            setAgencyName(data.AgencyName)
            setAgencyAddress(data.Address)
        }
    }

    private fun logout() {
        showLoading()
        viewModel.logout()
    }

    private fun logoutLocal() {
        FirebaseAuth.getInstance().signOut()
        viewModel.clearData()
        navigateTo(AuthenticationActivity::class.java)
    }

    private fun handleNameView(name: String) {
        tvName.text = name
        if (name.length >= 19) {
            tvName.textSize = 18F
        }
    }

    private fun handleLogoutResult(result: Result<LogoutResult>) {
        FirebaseAuth.getInstance().signOut()
        viewModel.clearData()
        navigateTo(AuthenticationActivity::class.java)
    }

    private fun handleSendFeedbackResult(result: Result<FeedbackResult>) {
        if (result is Result.Success) {
            if (result.data.ErrCode == 0) {
                showToast(R.string.send_feedback_success)
            } else {
                showToast(R.string.send_feedback_failure)
            }
        } else showErroNetwork()
    }

    private fun showModeScanDialog() {
        if (!::scanModeDialog.isInitialized) {
            context?.let {
                scanModeDialog = AwesomeDialog(it)
                    .showDialog()
                    .setDialogType(TYPE_SELECTION)
                    .setDialogTitle(R.string.mode_scan)
                    .setItems(scanModes)
                    .setItemSelection(getScanMode())
                    .setOnItemSelectedListener { mode ->
                        btnModeScan.setTextEnd(scanModes[mode])
                        savePrefs(Constants.SCAN_MODE, mode)
                    }
            }
        } else {
            scanModeDialog.showDialog()
        }
    }

    private fun showFeedbackDialog() {
        context?.let {
            AwesomeDialog(it).showDialog()
                .setDialogTitle(R.string.feedback)
                .setDialogType(TYPE_TEXT_INPUT)
                .setHint(R.string.msg_feedback)
                .setOnPositivePressed { dialog ->
                    if (!dialog.getInputText().isNullOrEmpty()) {
                        dialog.dismiss()
                        viewModel.sendFeedback(dialog.getInputText());
                    } else {
                        showToast(R.string.please_enter_your_feedback)
                    }
                }
        }
    }

    override fun initViewListener() {
        btnLogout.setOnClickListener(this)
        btnEditProfile.setOnClickListener(this)
        btnActivateHistory.setOnClickListener(this)
        btnModeScan.setOnClickListener(this)
        btnFeedback.setOnClickListener(this)
        btnRateApp.setOnClickListener(this)
        btnDeleteAccount.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            btnLogout.id -> logout()
            btnEditProfile.id -> mainNavigator.toPersonalInfo()
            btnActivateHistory.id -> mainNavigator.toProducts(getString(R.string.history_activate))
            btnModeScan.id -> showModeScanDialog()
            btnFeedback.id -> showFeedbackDialog()
            btnRateApp.id -> openLinkAppStore()
            btnDeleteAccount.id -> deleteAccount()
        }
    }

    private fun openLinkAppStore() {
        activity?.let {
            val appPackageName: String = it.packageName
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }
    }

    private fun deleteAccount() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            context?.let { context ->
                AwesomeDialog(context)
                    .showDialog()
                    .setDialogType(TYPE_CONFIRM_WITH_INPUT)
                    .setDialogTitle(R.string.menu_notification)
                    .setContent(getString(R.string.confirm_delete_account))
                    .setHint(getString(R.string.current_password))
                    .setOnPositivePressed { dialog ->
                        if (dialog.getInputText().isNullOrEmpty()) {
                            showToast(R.string.please_enter_your_pass)
                        } else {
                            dialog.dismiss()
                            viewModel.deleteAccount(dialog.getInputText(), token)
                        }
                    }
                    .setOnNegativePressed { dialog ->
                        dialog.dismiss()
                    }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateSwitchNotification()
    }

    private fun updateSwitchNotification() {
        context?.let {
            swNotification.isChecked = NotificationManagerCompat.from(it).areNotificationsEnabled()
        }
    }
}
