package checkvn.com.viettiepbhdt.presentation.ui.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.ListNotificationResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.usecases.GetListNotificationUseCase
import checkvn.com.viettiepbhdt.domain.usecases.GetListNotificationUseCaseImpl
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.getUserId
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val getListNotificationUseCase: GetListNotificationUseCase
) : BaseViewModel() {
    val getListNotificationResult = MutableLiveData<Result<ListNotificationResult>>()

    fun getListNotification() {
        val param = GetListNotificationUseCaseImpl.Param(
            User_ID = getUserId()
        )
        viewModelScope.launch {
            ensureActive()
            getListNotificationResult.value = getListNotificationUseCase.execute(param)
        }
    }
}