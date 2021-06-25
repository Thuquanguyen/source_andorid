package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ProductResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetProductDetailUseCase :
    BaseUseCase<GetProductDetailUseCaseImpl.Param, ProductResult>

class GetProductDetailUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetProductDetailUseCase {


    override suspend fun execute(param: Param): Result<ProductResult> {
        return warrantyRepository.getProductDetail(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val QRCodeContent: String = "",
        val User_ID: String = ""
    ) : BaseParam()
}