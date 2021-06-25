package checkvn.com.viettiepbhdt.presentation.ui.authentication

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.AgencyResult
import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.UpdateProfile
import checkvn.com.viettiepbhdt.presentation.ui.personal.PersonalViewModel
import checkvn.com.viettiepbhdt.utils.*
import com.michaldrabik.classicmaterialtimepicker.CmtpDateDialogFragment
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnDatePickedListener
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_enter_user_info.btnContinue
import kotlinx.android.synthetic.main.fragment_walk_though_info.*
import kotlinx.android.synthetic.main.layout_information.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

class EnterUserInfoFragment : BaseAuthenticationFragment() {
    private val personalViewModel: PersonalViewModel by viewModel()
    override var layoutId: Int = R.layout.fragment_walk_though_info
    private var userTypes: ArrayList<String> = ArrayList()
    private var userGenders: ArrayList<String> = ArrayList()
    private var userTypeSelected = -1
    private var genderSelected = -1
    private var birthDate = ""
    private var agencyId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun observeData() {
        personalViewModel.getAgencyInfoResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleAgencyInfoResult(it)
        })
    }

    private fun initView() {
        visibleViews(userInfo)
        goneViews(llEnterAgencyCode)
        activity?.let { hideKeyBoardWhenClickOutSide(rl_container, it) }
    }

    private fun initData() {
        context?.let {
            userTypes = it.resources.getStringArray(R.array.user_type).toCollection(ArrayList())
            userGenders = it.resources.getStringArray(R.array.user_gender).toCollection(ArrayList())
            setupSpinner()
        }
    }

    override fun initViewListener() {
        btnContinue.setOnClickListener {
            if (validateDataUser()) {
                updateProfile()
            }
        }
        setOnTextChangedListener()
    }

    override fun onResume() {
        super.onResume()
        activity?.apply {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    private fun setupSpinner() {
        spnUserType.item = userTypes
        spnUserType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                userTypeSelected = position
                spnUserType.isEnableErrorLabel = false
                when (userTypeSelected) {
                    0 -> goneViews(llEnterAgencyCode)
                    1 -> {
                        visibleViews(llEnterAgencyCode)
                        edtAgencyCode.hint = getString(R.string.enter_agency_code)
                    }
                    2 -> {
                        visibleViews(llEnterAgencyCode)
                        edtAgencyCode.hint = getString(R.string.enter_verify_code)
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        spnGender.item = userGenders
        spnGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                genderSelected = position
                spnGender.isEnableErrorLabel = false
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        rlBirthday.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val dialog = CmtpDateDialogFragment.newInstance(
            getString(R.string.confirm),
            getString(R.string.cancel)
        )
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        dialog.setInitialDate(day, month + 1, year)
        dialog.setOnDatePickedListener {
            birthDate = "" + it.year + "-" + it.month + "-" + it.day
            tvBirthday.text = "" + it.day + "-" + it.month + "-" + it.year
            tvBirthdayError.isVisible = false
        }
        dialog.show(parentFragmentManager, "DateTimePicker")
    }

    private fun updateProfile() {
        val userId = Prefs.getString(Constants.USER_ID, "")
        val fullName = edtUserName.text.toString()
        val gender = userGenders[genderSelected]
        val birth = birthDate
        val roleType = (userTypeSelected + 1).toString()

        showLoading()
        personalViewModel.updateProfileFirstTime(
            userId, fullName, birth,
            gender, roleType, agencyId
        )
            .observe(viewLifecycleOwner, Observer {
                hideLoading()
                handleUpdateResult(it)
            })
//        if (userTypeSelected == 1) { // Register account agency
//            showLoading()
//            personalViewModel.registerAgencyResult.observe(viewLifecycleOwner, Observer {
//                hideLoading()
//                registerAgencyResult(it)
//            })
//            personalViewModel.registerAgency(userId)
//        }
    }

    private fun handleUpdateResult(it: Result<UpdateProfile>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    saveData()
                    showToast(R.string.update_profile_success)
                    popAllFragment()
                } else if (!it.data.ErrMessage.isNullOrEmpty()) {
                    showToast(it.data.ErrMessage)
                }
            }
            else -> showToast(R.string.have_error_please_try_again)
        }
    }

    private fun registerAgencyResult(result: Result<CommonResult>?) {
        if (result is Result.Success) {
            if (result.data.ErrCode == 0) {
                showToast(R.string.register_agency_success)
            } else {
                showToast(R.string.register_agency_failure)
            }
        } else showToast(R.string.have_error_please_try_again)
    }

    private fun handleAgencyInfoResult(it: Result<AgencyResult>?) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    agencyId = it.data.Agency_ID.toString()
                    visibleViews(llAgencyInfo)
                    tvAgencyName.text = it.data.Name
                    tvAgencyAddress.text = getString(R.string.agency_address, it.data.Address)
                } else if (!it.data.ErrMessage.isNullOrEmpty()) {
                    showErrorAgencyCode()
                }
            }
            else -> {
                showErrorAgencyCode()
            }
        }
    }

    private fun showErrorAgencyCode() {
        goneViews(llAgencyInfo)
        resetAgencyInfo()
        tvAgencyCodeError.isVisible = true
        tvAgencyCodeError.text = getString(R.string.not_found_agency)
    }

    private fun validateDataUser(): Boolean {
        var isValidate = true
        if (spnUserType.selectedItem == null) {
            spnUserType.isEnableErrorLabel = true
            spnUserType.errorText = getString(R.string.msg_error_user_type)
            isValidate = false
        }
        if (edtUserName.text.isNullOrEmpty()) {
            tvUserNameError.isVisible = true
            isValidate = false
        }
        if (tvBirthday.text.isNullOrEmpty()) {
            tvBirthdayError.isVisible = true
            isValidate = false
        }
        if (spnGender.selectedItem == null) {
            spnGender.isEnableErrorLabel = true
            spnGender.errorText = getString(R.string.msg_error_gender)
            isValidate = false
        }
        if (userTypeSelected == 1) {
            if (edtAgencyCode.text.isNullOrEmpty()) {
                tvAgencyCodeError.isVisible = true
                tvAgencyCodeError.text = getString(R.string.msg_error_agency_code)
                isValidate = false
            } else if (agencyId.isNullOrEmpty() || tvAgencyCodeError.isVisible) {
                isValidate = false
            }
        } else if (userTypeSelected == 2) {
            if (edtAgencyCode.text.isNullOrEmpty()) {
                tvAgencyCodeError.isVisible = true
                tvAgencyCodeError.text = getString(R.string.msg_error_verify_code)
                isValidate = false
            } else if (edtAgencyCode.text.toString() != Constants.VERIFY_CODE_WARRANTY_STAFF) {
                tvAgencyCodeError.isVisible = true
                tvAgencyCodeError.text = getString(R.string.msg_error_wrong_verify_code)
                isValidate = false
            }
        }
        return isValidate
    }

    private fun saveData() {
        setFullName(edtUserName.text.toString())
        setBirth(tvBirthday.text.toString())
        setGender(userGenders[genderSelected])
        setRoleType(userTypeSelected + 1) // 1.Customer 2.Agency 3.Staff
        if (userTypeSelected == 1) {
            if (!edtAgencyCode.text.isNullOrEmpty()) {
                setAgencyId(edtAgencyCode.text.toString())
            }
            if (!tvAgencyName.text.isNullOrEmpty()) {
                setAgencyName(tvAgencyName.text.toString())
            }
            if (!tvAgencyAddress.text.isNullOrEmpty()) {
                setAgencyAddress(tvAgencyAddress.text.toString())
            }
        }
    }

    private fun setOnTextChangedListener() {
        edtUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    tvUserNameError.isVisible = false
                }
            }
        })
        edtAgencyCode.addTextChangedListener(object : TextWatcher {
            val handler = Handler()
            var runnable: Runnable? = null
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                handler.removeCallbacks(runnable)
                if (!s.isNullOrEmpty()) {
                    tvAgencyCodeError.isVisible = false
                    tvAgencyCodeError.text = getString(R.string.msg_error_agency_code)
                }
            }

            override fun afterTextChanged(s: Editable) {
                runnable = Runnable {
                    if (!s.toString().isNullOrEmpty()) {
                        if (userTypeSelected == 1) { // Agency
                            personalViewModel.getAgencyByCode(s.toString())
                        }
                    } else {
                        resetAgencyInfo()
                        goneViews(llAgencyInfo)
                    }
                }
                handler.postDelayed(runnable, 500)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
        })
    }

    private fun resetAgencyInfo() {
        agencyId = ""
        tvAgencyName.text = ""
        tvAgencyAddress.text = ""
    }
}