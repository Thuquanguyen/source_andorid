package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.AgencyResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetAgencyInfoUseCase :
    BaseUseCase<GetAgencyInfoUseCaseImpl.Param, AgencyResult>

class GetAgencyInfoUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetAgencyInfoUseCase {

    override suspend fun execute(param: Param): Result<AgencyResult> {
        return warrantyRepository.getAgencyInfo(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Code: String = ""
    ) : BaseParam()
}