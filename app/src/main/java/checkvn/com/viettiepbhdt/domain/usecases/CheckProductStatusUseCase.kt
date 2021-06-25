package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ProductStatusResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface CheckProductStatusUseCase :
    BaseUseCase<CheckProductStatusUseCaseImpl.Param, ProductStatusResult>

class CheckProductStatusUseCaseImpl(private val repository: WarrantyRepository) :
    CheckProductStatusUseCase {

    override suspend fun execute(param: Param): Result<ProductStatusResult> {
        return repository.checkProductStatus(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val QRCodeContent: String = "",
        val Serial: String = "",
        val User_ID: String = ""
    )
}