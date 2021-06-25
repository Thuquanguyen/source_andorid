package checkvn.com.viettiepbhdt.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.LogoutResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.usecases.LogoutUseCase
import checkvn.com.viettiepbhdt.domain.usecases.LogoutUseCaseImpl
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.Constants
import com.google.firebase.iid.FirebaseInstanceId
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.launch

class MainViewModel(private val logoutUseCase: LogoutUseCase) : BaseViewModel() {

    val logoutResult = MutableLiveData<Result<LogoutResult>>()

    fun logout() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            val token = it.result?.token ?: ""
            callApiLogout(token)
        }
    }

    private fun callApiLogout(fcmToken: String) {
        val userId = Prefs.getString(Constants.USER_ID, "")
        viewModelScope.launch {
            val result = logoutUseCase.execute(
                LogoutUseCaseImpl.Param(
                    FireBaseToken = fcmToken,
                    User_ID = userId
                )
            )
            logoutResult.value = result
        }
    }
}
