package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListLocationResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListProvinceUseCase : BaseUseCase<GetListProvinceUseCaseImpl.Param, ListLocationResult>

class GetListProvinceUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetListProvinceUseCase {

    override suspend fun execute(param: Param): Result<ListLocationResult> {
        return warrantyRepository.getListProvince(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Location_ID: String = ""
    ) : BaseParam()
}