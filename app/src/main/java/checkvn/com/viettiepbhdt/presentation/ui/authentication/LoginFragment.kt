package checkvn.com.viettiepbhdt.presentation.ui.authentication


import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import checkvn.com.viettiepbhdt.R
import checkvn.com.viettiepbhdt.domain.entities.LoginResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.LOGIN
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.REGISTER
import checkvn.com.viettiepbhdt.presentation.ui.main.MainActivity
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.extensions.*
import checkvn.com.viettiepbhdt.utils.navigateTo
import checkvn.com.viettiepbhdt.utils.savePrefs
import checkvn.com.viettiepbhdt.utils.setUsed
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseAuthenticationFragment() {

    override var layoutId: Int = R.layout.fragment_login

    override fun onResume() {
        super.onResume()
        authViewModel.authType = LOGIN
    }

    override fun initViewListener() {
        btnLogin.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        btnRegisterAccount.setOnClickListener(this)

        edtPhoneNumber.onTextChangeListener {
            edtPhoneNumber.changeBackgroundColor()
            tvMsgPhoneNumber.isVisible = false
        }

        edtPassword.onTextChangeListener {
            edtPassword.changeBackgroundColor()
            tvMsgPassword.isVisible = false
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            btnLogin.id -> handleAccountData()
            tvForgotPassword.id -> toForgotPassword()
            btnRegisterAccount.id -> toRegisterAccount()
        }
    }

    private fun toForgotPassword() {
        authViewModel.authType = AuthType.FORGOT
        navigateToPhoneFragment()
    }

    private fun toRegisterAccount() {
        authViewModel.authType = REGISTER
        navigateToPhoneFragment()
    }

    private fun navigateToPhoneFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_enterPhoneFragment)
    }

    private fun handleAccountData() {
        val phoneNumber = edtPhoneNumber.value
        val passWord = edtPassword.value

        val isValidPhoneNumber = phoneNumber.isValidPhoneNumber()
        val isValidPassword = passWord.isValidPassword()

        if (!isValidPassword) {
            tvMsgPassword.setText(R.string.invalid_password)
            tvMsgPassword.isVisible = true
            edtPassword.changeBackgroundColor(isValidPassword)
        }

        if (!isValidPhoneNumber) {
            edtPhoneNumber.changeBackgroundColor(isValidPhoneNumber)
            tvMsgPhoneNumber.isVisible = true
        }

        if (isValidPhoneNumber && isValidPassword) {
            showLoading()
            loginWithAccount(phoneNumber, passWord)
        }
    }

    override fun observeData() {
        authViewModel.loginData.observe(viewLifecycleOwner, Observer {
            hideLoading()
            handleRequestResult(it)
        })
    }

    private fun handleRequestResult(it: Result<LoginResult>) {
        if (it is Result.Success) {
            if (it.data.ErrCode == 0) {
                saveData(it.data)
                setUsed()
                navigateTo(MainActivity::class.java, true)
            } else {
                tvMsgPassword.setText(R.string.wrong_password)
                tvMsgPassword.isVisible = true
            }
        } else showErroNetwork()
    }

    private fun saveData(data: LoginResult) {
        savePrefs(Constants.USER_ID, data.User_ID)
        savePrefs(Constants.USERNAME, data.UserName)
        savePrefs(Constants.FULLNAME, data.FullName)
        savePrefs(Constants.ROLE_TYPE, data.RoleType)
        savePrefs(Constants.ROLENAME, data.RoleName)
        savePrefs(Constants.AvatarUrl, data.AvatarUrl)
    }

    private fun loginWithAccount(phoneNumber: String, password: String) {
        showLoading()
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            authViewModel.loginWithAccount(phoneNumber, password, token)
        }
    }
}
