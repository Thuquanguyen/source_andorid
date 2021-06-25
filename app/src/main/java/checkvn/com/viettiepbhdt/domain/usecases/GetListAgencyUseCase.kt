package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListAgencyResult
import checkvn.com.viettiepbhdt.domain.entities.ProductResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListAgencyUseCase : BaseUseCase<GetListAgencyUseCaseImpl.Param, ListAgencyResult>

class GetListAgencyUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetListAgencyUseCase {

    override suspend fun execute(param: Param): Result<ListAgencyResult> {
        return warrantyRepository.getListAgency(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Keyword: String = ""
    ) : BaseParam()
}

