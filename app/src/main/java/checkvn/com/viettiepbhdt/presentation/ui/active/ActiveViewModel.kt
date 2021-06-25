package checkvn.com.viettiepbhdt.presentation.ui.active

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import checkvn.com.viettiepbhdt.domain.entities.ActivateResult
import checkvn.com.viettiepbhdt.domain.entities.ProductStatusResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.usecases.ActivateProductUseCase
import checkvn.com.viettiepbhdt.domain.usecases.ActivateProductUseCaseImpl.Param
import checkvn.com.viettiepbhdt.domain.usecases.CheckProductStatusUseCase
import checkvn.com.viettiepbhdt.domain.usecases.CheckProductStatusUseCaseImpl
import checkvn.com.viettiepbhdt.domain.usecases.GetListAgencyUseCaseImpl
import checkvn.com.viettiepbhdt.presentation.ui.base.BaseViewModel
import checkvn.com.viettiepbhdt.utils.Constants
import checkvn.com.viettiepbhdt.utils.getLastLocation
import checkvn.com.viettiepbhdt.utils.getUserId
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class ActiveViewModel(
    private val activateProductUseCase: ActivateProductUseCase,
    private val checkProductStatusUseCase: CheckProductStatusUseCase
) : BaseViewModel() {

    val activeResult = MutableLiveData<Result<ActivateResult>>()
    val checkProductStatusResult = MutableLiveData<Result<ProductStatusResult>>()

    var phone: String = ""
    var latitude: String = ""
    var longitude: String = ""

    fun activateProductByQrCode(activeCode: String, agencyId: String) {
        updateLocation()
        val param = Param(
            QRCodeContent = activeCode,
            User_ID = getUserId(),
            UsedPhone = phone,
            Agency_ID = agencyId,
            Latitude = latitude,
            Longitude = longitude
        )
        viewModelScope.launch {
            ensureActive()
            activeResult.value = activateProductUseCase.execute(param)
        }
    }

    fun activateProductBySerial(activeCode: String, agencyId: String) {
        updateLocation()
        val param = Param(
            Serial = activeCode,
            User_ID = getUserId(),
            UsedPhone = phone,
            Agency_ID = agencyId,
            Latitude = latitude,
            Longitude = longitude
        )
        viewModelScope.launch {
            ensureActive()
            activeResult.value = activateProductUseCase.execute(param)
        }
    }

    private fun updateLocation() {
        var location = getLastLocation().split(":")
        if (location.size >= 2) {
            latitude = location[0]
            longitude = location[1]
        }
    }

    fun checkProductStatus(activeType: String, activeCode: String) {
        val param = if (activeType == Constants.ActiveType.QR_CODE) {
            CheckProductStatusUseCaseImpl.Param(
                QRCodeContent = activeCode,
                User_ID = getUserId()
            )
        } else {
            CheckProductStatusUseCaseImpl.Param(
                Serial = activeCode,
                User_ID = getUserId()
            )
        }
        viewModelScope.launch {
            checkProductStatusResult.value = checkProductStatusUseCase.execute(param)
        }
    }
}
