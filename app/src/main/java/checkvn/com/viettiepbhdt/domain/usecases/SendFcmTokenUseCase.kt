package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface SendFcmTokenUseCase : BaseUseCase<SendFcmTokenUseCaseImpl.Param, CommonResult>

class SendFcmTokenUseCaseImpl(private val repository: WarrantyRepository) :
    SendFcmTokenUseCase {

    override suspend fun execute(param: Param): Result<CommonResult> {
        return repository.sendFcmToken(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val FireBaseToken: String
    )
}