package checkvn.com.viettiepbhdt.presentation.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.*
import checkvn.com.viettiepbhdt.domain.usecases.*
import checkvn.com.viettiepbhdt.presentation.ui.authentication.AuthenticationViewModel.AuthType.LOGIN
import checkvn.com.viettiepbhdt.utils.*
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

class AuthenticationViewModel(
    private val requestOtpUseCase: RequestOtpUseCase,
    private val verifyCodeUseCase: VerifyCodeUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val loginUseCase: LoginUseCase,
    private val recoverPasswordUseCase: RecoverPasswordUseCase
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val loginResult = MutableLiveData<LoginResultType>()
    val socialLoginMethod = MutableLiveData<SocialLoginMethod>()
    val isTokenValid = MutableLiveData<Boolean>()
    val requestOtpResult = MutableLiveData<Result<RequestOtpResult>>()
    val verifyCodeResult = MutableLiveData<Result<VerifyCode>>()
    val createPasswordResult = MutableLiveData<Result<CreatePassword>>()
    val recoverPasswordResult = MutableLiveData<Result<RecoverPassword>>()
    val loginData = MutableLiveData<Result<LoginResult>>()

    val sharedData = AuthData()
    var authType: AuthType = LOGIN

    fun saveToken(token: String, method: SocialLoginMethod) {
        setToken(token)
        setMethod(method.method)
    }

    fun savePersonalInfo(
        fullName: String? = null,
        email: String? = null,
        avatarUri: String? = null
    ) {
        setFullName(fullName)
        setEmail(email)
        setAvatarUri(avatarUri)
        setUsed()
    }

    fun saveZaloData(json: JSONObject) {
        try {
            val name = json.getString("name")
            val avatarUri = json.getJSONObject("picture").getJSONObject("data").getString("url")
            savePersonalInfo(fullName = name, avatarUri = avatarUri)
            loginResult.value = LoginResultType.OnSuccess
        } catch (exception: JSONException) {
            printError(exception)
        }
    }

    fun requestOtp(phoneNumber: String) {
        sharedData.UserName = phoneNumber
        viewModelScope.launch {
            val result = requestOtpUseCase.execute(RequestOtpUseCaseImpl.Param(phoneNumber))
            requestOtpResult.value = result
        }
    }

    fun verifyAuthenticationCode(code: String) {
        sharedData.Code = code
        viewModelScope.launch {
            val result = verifyCodeUseCase.execute(
                VerifyCodeUseCaseImpl.Param(sharedData.Transaction_ID, code)
            )
            verifyCodeResult.value = result
        }
    }

    fun createNewPassword(password: String) {
        sharedData.Password = password
        viewModelScope.launch {
            val result = createPasswordUseCase.execute(
                CreatePasswordUseCaseImpl.Param(
                    Code = sharedData.Code,
                    Transaction_ID = sharedData.Transaction_ID,
                    Password = password
                )
            )
            createPasswordResult.value = result
        }
    }

    fun loginWithAccount(phoneNumber: String, password: String, firebaseToken: String) {
        viewModelScope.launch {
            val param = LoginUseCaseImpl.Param(
                FireBaseToken = firebaseToken,
                Password = password,
                UserName = phoneNumber
            )
            val result = loginUseCase.execute(param)
            loginData.value = result
        }
    }

    fun recoverPassword(phoneNumber: String) {
        viewModelScope.launch {
            val result =
                recoverPasswordUseCase.execute(RecoverPasswordUseCaseImpl.Param(phoneNumber))
            recoverPasswordResult.value = result
        }
    }

    fun changePassword(oldPassword: String, newPassword: String, callBack: BooleanCallBack) {
        isLoading.value = true
        delayer {
            isLoading.postValue(false)
            callBack(true)
        }
    }

    data class AuthData(
        var Transaction_ID: String = "",
        var Code: String = "",
        var UserName: String = "",
        var Password: String = ""
    )

    enum class SocialLoginMethod(val method: String) {
        FACEBOOK("FACEBOOK"),
        GOOGLE("GOOGLE"),
        ZALO("ZALO")
    }

    enum class AuthType {
        LOGIN,
        FORGOT,
        REGISTER
    }
}
