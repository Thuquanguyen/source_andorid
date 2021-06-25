package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListProductResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListProductUseCase : BaseUseCase<GetListProductUseCaseImpl.Param, ListProductResult>

class GetListProductUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetListProductUseCase {

    override suspend fun execute(param: Param): Result<ListProductResult> {
        return warrantyRepository.getListProduct(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val ProductBrand_ID: String = "",
        val FromDate: String = "",
        val ToDate: String = "",
        val User_ID: String = "",
        val OrderBy: String = "",
        val PageSize: String = "",
        val CurrentPage: String = ""
    ) : BaseParam()
}
