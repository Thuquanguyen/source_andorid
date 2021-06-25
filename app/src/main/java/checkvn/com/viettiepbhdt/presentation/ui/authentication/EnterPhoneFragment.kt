package checkvn.com.viettiepbhdt.presentation.ui.authentication


import android.os.Bundle
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
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.FORGOT
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.REGISTER
import checkvn.com.viettiepbhdt.utils.extensions.isValidPhoneNumber
import checkvn.com.viettiepbhdt.utils.extensions.onTextChangeListener
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.showToast
import kotlinx.android.synthetic.main.fragment_register_phone_number.*

class EnterPhoneFragment : BaseAuthenticationFragment() {

    override var layoutId: Int = R.layout.fragment_register_phone_number

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initViewListener() {
        imgClose.setOnClickListener(this)
        btnSendCode.setOnClickListener(this)

        edtPhoneNumber.onTextChangeListener {
            hidePhoneNumberMessage()
        }
    }

    private fun initView() {
        var messageId = R.string.enter_phone_number_to_register
        if (authViewModel.authType == FORGOT) messageId = R.string.enter_phone_number_to_recover
        tvAuthType.text = getString(messageId)
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
            btnSendCode.id -> sendCode()
        }
    }

    private fun sendCode() {
        val phoneNumber = edtPhoneNumber.value
        if (!phoneNumber.isValidPhoneNumber()) {
            showPhoneNumberMessage()
            return
        } else {
            hidePhoneNumberMessage()
            showLoading()
            if (authViewModel.authType == REGISTER) authViewModel.requestOtp(phoneNumber)
            else authViewModel.recoverPassword(phoneNumber)
        }
    }

    override fun observeData() {
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
                    authViewModel.sharedData.UserName = edtPhoneNumber.value
                    toEnterAuthenticationCode()
                } else showToast(R.string.this_phone_not_register_yet)
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
                    authViewModel.sharedData.UserName = edtPhoneNumber.value
                    toEnterAuthenticationCode()
                } else showToast(R.string.this_phone_number_was_registered)
            }
            else -> {
                showErroNetwork()
            }
        }
    }

    private fun toEnterAuthenticationCode() {
        findNavController().navigate(R.id.action_enterPhoneFragment_to_otpFragment)
    }

    private fun showPhoneNumberMessage() {
        tvMsgPhoneNumber.isVisible = true
    }

    private fun hidePhoneNumberMessage() {
        tvMsgPhoneNumber.isVisible = false
    }
}
