package checkvn.com.viettiepbhdt.presentation.ui.authentication


import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.RecoverPassword
import checkvn.com.viettiepbhdt.domain.entities.RequestOtpResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.VerifyCode
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.REGISTER
import checkvn.com.viettiepbhdt.utils.extensions.onTextChangeListener
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.fragment_otp.*

class OtpFragment : BaseAuthenticationFragment() {

    override var layoutId: Int = R.layout.fragment_otp

    override fun initViewListener() {
        imgClose.setOnClickListener(this)
        btnVerify.setOnClickListener(this)
        tvResendCode.setOnClickListener(this)

        edtOtp.onTextChangeListener {
            hideOtpMessage()
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.apply {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            btnVerify.id -> verifyCode()
            tvResendCode.id -> requestOtp()
        }
    }

    private fun requestOtp() {
        hideOtpMessage()
        showLoading()
        if (authViewModel.authType == REGISTER) authViewModel.requestOtp(authViewModel.sharedData.UserName)
        else authViewModel.recoverPassword(authViewModel.sharedData.UserName)
    }

    private fun verifyCode() {
        if (edtOtp.value.length != 6) {
            showOtpMessage()
            return
        } else {
            hideOtpMessage()
            showLoading()
            authViewModel.verifyAuthenticationCode(edtOtp.value)
        }
    }

    override fun observeData() {
        authViewModel.verifyCodeResult.observe(viewLifecycleOwner, Observer {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                hideLoading()
                handleVerifyResult(it)
            }
        })

        authViewModel.requestOtpResult.observe(viewLifecycleOwner, Observer {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                hideLoading()
                handleRequestResult(it)
            }
        })

        authViewModel.recoverPasswordResult.observe(viewLifecycleOwner, Observer {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
                hideLoading()
                handleRecoverResult(it)
            }
        })
    }

    private fun handleRecoverResult(it: Result<RecoverPassword>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    authViewModel.sharedData.Transaction_ID = it.data.Transaction_ID
                } else {
                    showToast(R.string.have_error_please_try_again)
                }
            }
            else -> {
                showErroNetwork()
            }
        }
    }

    private fun handleVerifyResult(it: Result<VerifyCode>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    authViewModel.sharedData.Transaction_ID = it.data.Transaction_ID
                    authViewModel.sharedData.Code = edtOtp.value
                    navigateToCreateNewPassword()
                } else if (it.data.ErrCode == 1) {
                    showOtpMessage()
                }
            }
            else -> {
                showErroNetwork()
            }
        }
    }

    private fun handleRequestResult(it: Result<RequestOtpResult>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    authViewModel.sharedData.Transaction_ID = it.data.Transaction_ID
                } else {
                    showToast(R.string.have_error_please_try_again)
                }
            }
            else -> {
                showErroNetwork()
            }
        }
    }

    private fun showOtpMessage() {
        tvCodeMessage.isVisible = true
    }

    private fun hideOtpMessage() {
        tvCodeMessage.isVisible = false
    }

    private fun navigateToCreateNewPassword() {
        findNavController().navigate(R.id.action_otpFragment_to_createPasswordFragment)
    }
}
