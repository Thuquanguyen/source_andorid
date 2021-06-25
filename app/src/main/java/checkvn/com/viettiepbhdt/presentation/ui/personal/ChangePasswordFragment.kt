package checkvn.com.viettiepbhdt.presentation.ui.personal

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.ChangePassword
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.extensions.isValidPassword
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.showToast
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.fragment_update_password.*

internal const val TAG_UPDATE_PASSWORD = "TAG_UPDATE_PASSWORD"

class ChangePasswordFragment : BasePersonalFragment() {

    override var layoutId: Int = R.layout.fragment_update_password

    override fun initToolbar() {
        toolbar = topBar
    }

    override fun initViewListener() {
        btnChangePassword.setOnClickListener(this)
    }

    private fun changePassword() {

        tvCurrentPasswordMessage.isVisible = !edtCurrentPassword.value.isValidPassword()
        tvNewPasswordMessage.isVisible = edtNewPassword.value != edtConfirmPassword.value

        if (!edtNewPassword.value.isValidPassword()) {
            tvNewPasswordMessage.setText(R.string.password_too_weak)
            tvNewPasswordMessage.isVisible = true
        }

        if (tvCurrentPasswordMessage.isVisible || tvNewPasswordMessage.isVisible) {
            return
        }

        showLoading()
        val userName = Prefs.getString(Constants.USERNAME, "")
        viewModel.changePassword(userName, edtCurrentPassword.value, edtNewPassword.value)
    }


    override fun initDataObserver() {
        viewModel.changePasswordResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleChangePasswordResult(it)
        })
    }

    private fun handleChangePasswordResult(result: Result<ChangePassword>) {
        when (result) {
            is Result.Success -> {
                if (result.data.ErrCode == 0) {
                    showToast(R.string.change_password_success)
                    clearEdittext()
                } else {
                    tvCurrentPasswordMessage.isVisible = true
                }
            }
            else -> showErroNetwork()
        }
    }

    private fun clearEdittext() {
        edtConfirmPassword.text.clear()
        edtNewPassword.text.clear()
        edtCurrentPassword.text.clear()
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            btnChangePassword.id -> changePassword()
        }
    }
}
