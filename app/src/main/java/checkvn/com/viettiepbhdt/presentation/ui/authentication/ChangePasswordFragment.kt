package checkvn.com.viettiepbhdt.presentation.ui.authentication


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.view.isVisible
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.utils.extensions.isValidPassword
import checkvn.com.viettiepbhdt.utils.extensions.onTextChangeListener
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.showToast
import checkvn.com.viettiepbhdt.utils.ui
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : BaseAuthenticationFragment() {

    override var layoutId: Int = R.layout.fragment_change_password

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            activity?.finish()
        }
    }

    private fun changePassword() {
        val oldPassword = edtOldPassword.value
        val newPassword = edtPassword.value

        if (!oldPassword.isValidPassword()) {
            tvOldPassword.isVisible = true
            return
        }
        if (newPassword != edtConfirmPassword.value && newPassword.isValidPassword()) {
            tvMsgPassword.isVisible = true
            return
        }

        authViewModel.changePassword(oldPassword, newPassword) {
            ui {
                showToast(R.string.change_password_success)
                requireActivity().finish()
            }
        }
    }

    override fun initViewListener() {
        icBack.setOnClickListener(this)
        btnChangePassword.setOnClickListener(this)

        edtOldPassword.onTextChangeListener {
            tvOldPassword.isVisible = false
        }

        edtPassword.onTextChangeListener {
            tvMsgPassword.isVisible = false
        }

        edtConfirmPassword.onTextChangeListener {
            tvMsgPassword.isVisible = false
        }
    }

    override fun observeData() {

    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            icBack.id -> requireActivity().finish()
            btnChangePassword.id -> changePassword()
        }
    }

}
