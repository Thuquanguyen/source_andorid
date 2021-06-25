package checkvn.com.viettiepbhdt.presentation.ui.authentication


import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.CreatePassword
import checkvn.com.viettiepbhdt.domain.entities.LoginResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.extensions.isValidPassword
import checkvn.com.viettiepbhdt.utils.extensions.onTextChangeListener
import checkvn.com.viettiepbhdt.utils.extensions.value
import checkvn.com.viettiepbhdt.utils.savePrefs
import checkvn.com.viettiepbhdt.utils.showToast
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_create_password.*

class CreatePasswordFragment : BaseAuthenticationFragment() {

    override var layoutId: Int = R.layout.fragment_create_password

    override fun initViewListener() {
        btnComplete.setOnClickListener(this)
        imgClose.setOnClickListener(this)
        edtPassword.onTextChangeListener {
            hidePasswordMessage()
        }
        edtConfirmPassword.onTextChangeListener {
            hidePasswordMessage()
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
            btnComplete.id -> createNewPassword()
        }
    }

    private fun createNewPassword() {
        when {
            !edtPassword.value.isValidPassword() -> {
                tvMsgPassword.setText(R.string.password_too_weak)
                showPasswordMessage()
            }
            edtPassword.value != edtConfirmPassword.value -> {
                tvMsgPassword.setText(R.string.password_is_not_match)
                showPasswordMessage()
            }
            else -> {
                hidePasswordMessage()
                showLoading()
                authViewModel.createNewPassword(edtConfirmPassword.value)
            }
        }
    }

    override fun observeData() {
        authViewModel.createPasswordResult.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleRequestResult(it)
        })

        authViewModel.loginData.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleLoginResult(it)
        })
    }

    private fun handleRequestResult(it: Result<CreatePassword>) {
        when (it) {
            is Result.Success -> {
                if (it.data.ErrCode == 0) {
                    if (authViewModel.authType == AuthenticationViewModel.AuthType.REGISTER) {
                        showToast(R.string.create_account_success)
                        loginWithAccount()
                    } else {
                        showToast(R.string.change_password_success)
                        popAllFragment()
                    }
                } else {
                    showToast(R.string.have_error_please_try_again)
                }
            }
            else -> showErroNetwork()
        }
    }

    private fun handleLoginResult(it: Result<LoginResult>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                saveData(it.data)
                navigateToEnterUserInfo()
            } else {
                showToast(R.string.have_error_please_try_again)
            }
        } else {
            showToast(R.string.have_error_please_try_again)
        }
    }

    private fun navigateToEnterUserInfo() {
        findNavController().navigate(R.id.action_createPasswordFragment_to_enterUserInfoFragment)
    }

    private fun saveData(data: LoginResult) {
        savePrefs(Constants.USER_ID, data.User_ID)
        savePrefs(Constants.USERNAME, data.UserName)
        savePrefs(Constants.FULLNAME, data.FullName)
        savePrefs(Constants.ROLE_TYPE, data.RoleType)
        savePrefs(Constants.ROLENAME, data.RoleName)
        savePrefs(Constants.AvatarUrl, data.AvatarUrl)
    }

    private fun showPasswordMessage() {
        tvMsgPassword.isVisible = true
    }

    private fun hidePasswordMessage() {
        tvMsgPassword.isVisible = false
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
