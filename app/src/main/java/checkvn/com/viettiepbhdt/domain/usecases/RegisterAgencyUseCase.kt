package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface RegisterAgencyUseCase : BaseUseCase<RegisterAgencyUseCaseImpl.Param, CommonResult>

class RegisterAgencyUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : RegisterAgencyUseCase {

    override suspend fun execute(param: Param): Result<CommonResult> {
        return warrantyRepository.registerAgency(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String = ""
    ) : BaseParam()
}
