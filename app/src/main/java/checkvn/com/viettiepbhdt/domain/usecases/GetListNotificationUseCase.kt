package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListNotificationResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListNotificationUseCase :
    BaseUseCase<GetListNotificationUseCaseImpl.Param, ListNotificationResult>

class GetListNotificationUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetListNotificationUseCase {

    override suspend fun execute(param: Param): Result<ListNotificationResult> {
        return warrantyRepository.getListNotification(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String = ""
    ) : BaseParam()
}