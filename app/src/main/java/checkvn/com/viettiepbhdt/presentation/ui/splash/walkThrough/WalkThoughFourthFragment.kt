package checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel
import checkvn.com.viettiepbhdt.presentation.ui.splash.walkThrough.WalkThoughFourthFragment.STEP.*
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.extensions.isValidPhoneNumber
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.savePrefs
import checkvn.com.viettiepbhdt.utils.showToast
import checkvn.com.viettiepbhdt.utils.ui
import co.kyash.rkd.KeyboardDetector
import co.kyash.rkd.KeyboardStatus
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_walk_though_fourth.*
import org.koin.android.viewmodel.ext.android.viewModel


class WalkThoughFourthFragment : BaseWalkThoughFragment() {

    override var layoutId: Int = R.layout.fragment_walk_though_fourth
    private val authViewModel: AuthenticationViewModel by viewModel()
    private var keyboardIsShowing = false
    private var currentStep = PHONE

    private enum class STEP(val buttonContent: Int) {
        PHONE(R.string.send_verification_code),
        OTP(R.string.verify),
        PASSWORD(R.string.complete)
    }

//    override fun onResume() {
//        super.onResume()
//        seLayoutVisible(PHONE)
//    }

    override fun initViewListener() {
        button.setOnClickListener {
            handleRequest(currentStep)
        }
        keyboardListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataObserver()
    }

    private fun seLayoutVisible(step: STEP) {
        when (step) {
            PHONE -> {
                layoutEnterPhoneNumber.isVisible = true
                layoutEnterOtp.isVisible = false
                layoutCreatePassword.isVisible = false
            }
            OTP -> {
                layoutEnterOtp.isVisible = true
                layoutEnterPhoneNumber.isVisible = false
                layoutCreatePassword.isVisible = false
            }
            PASSWORD -> {
                layoutCreatePassword.isVisible = true
                layoutEnterPhoneNumber.isVisible = false
                layoutEnterOtp.isVisible = false
            }
        }
        button.setText(step.buttonContent)
        currentStep = step
    }

    override fun moveToNextFragment() {
        viewModel.screenIndex.value = 3
    }

    @SuppressLint("CheckResult")
    private fun keyboardListener() {
        KeyboardDetector(activity).observe().subscribe { status ->
            keyboardIsShowing = when (status) {
                KeyboardStatus.OPENED -> {
                    true
                }
                KeyboardStatus.CLOSED -> {
                    false
                }
            }
            setTextVisible(!keyboardIsShowing)
        }
    }

    private fun setTextVisible(isShow: Boolean) {
        ui {
            tvTitle.isVisible = isShow
            tvContent.isVisible = isShow
            indicator.isVisible = isShow
        }
    }

    private fun handleRequest(step: STEP) {
        when (step) {
            PHONE -> requestOtp()
            OTP -> verifyCode()
            PASSWORD -> createPassword()
        }
    }

    private fun requestOtp() {
        if (edtPhoneNumber.value.isValidPhoneNumber()) {
            showLoading()
            tvPhoneMessage.isVisible = false
            authViewModel.requestOtp(edtPhoneNumber.value)
        } else {
            tvPhoneMessage.setText(R.string.invalid_phone_number)
            tvPhoneMessage.isVisible = true
        }
    }

    private fun initDataObserver() {
        authViewModel.requestOtpResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleRequestOtpResult(it)
        })

        authViewModel.verifyCodeResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleVerifyCodeResult(it)
        })

        authViewModel.createPasswordResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleChangePasswordResult(it)
        })

        authViewModel.loginData.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleLoginResult(it)
        })
    }

    private fun handleRequestOtpResult(it: Result<RequestOtpResult>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                authViewModel.sharedData.Transaction_ID = it.data.Transaction_ID
                seLayoutVisible(OTP)
            } else showToast(R.string.this_phone_number_was_registered)
        } else
            showToast(R.string.have_error_please_try_again)
    }

    private fun handleVerifyCodeResult(it: Result<VerifyCode>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                authViewModel.sharedData.Transaction_ID = it.data.Transaction_ID
                seLayoutVisible(PASSWORD)
            } else showToast(R.string.wrong_authentication_code)
        } else
            showToast(R.string.have_error_please_try_again)
    }

    private fun handleChangePasswordResult(it: Result<CreatePassword>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                showToast(R.string.create_account_success)
//                viewModel.screenIndex.value = 3
                loginWithAccount()
            } else showToast(R.string.have_error_please_try_again)
        } else
            showToast(R.string.have_error_please_try_again)
    }

    private fun handleLoginResult(it: Result<LoginResult>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                saveData(it.data)
                moveToNextFragment()
            } else {
                showToast(R.string.have_error_please_try_again)
            }
        } else {
            showToast(R.string.have_error_please_try_again)
        }
    }

    private fun saveData(data: LoginResult) {
        savePrefs(Constants.USER_ID, data.User_ID)
        savePrefs(Constants.USERNAME, data.UserName)
        savePrefs(Constants.FULLNAME, data.FullName)
        savePrefs(Constants.ROLE_TYPE, data.RoleType)
        savePrefs(Constants.ROLENAME, data.RoleName)
        savePrefs(Constants.AvatarUrl, data.AvatarUrl)
    }

    private fun verifyCode() {
        showLoading()
        authViewModel.verifyAuthenticationCode(edtOtp.value)
    }

    private fun createPassword() {
        showLoading()
        authViewModel.createNewPassword(edtPassword.value)
    }

    private fun loginWithAccount() {
        showLoading()
        val phone = authViewModel.sharedData.UserName
        val password = authViewModel.sharedData.Password
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            authViewModel.loginWithAccount(phone, password, token)
        }
    }
}
