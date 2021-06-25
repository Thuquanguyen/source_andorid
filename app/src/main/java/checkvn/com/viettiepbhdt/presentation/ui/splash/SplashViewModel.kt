package checkvn.com.viettiepbhdt.presentation.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.usecases.SendFcmTokenUseCase
import checkvn.com.viettiepbhdt.domain.usecases.SendFcmTokenUseCaseImpl
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class SplashViewModel(
    private val sendFcmTokenUseCase: SendFcmTokenUseCase
) : ViewModel() {

    val sendFcmToken = MutableLiveData<Result<CommonResult>>()

    fun sendFcmToken(fcmToken: String) {
        val param = SendFcmTokenUseCaseImpl.Param(
            FireBaseToken = fcmToken
        )
        viewModelScope.launch {
            ensureActive()
            sendFcmToken.value = sendFcmTokenUseCase.execute(param)
        }
    }

}