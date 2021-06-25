package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ActivateResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

/**
 * Thực hiện kích hoạt sản phẩm.
 * */
interface ActivateProductUseCase : BaseUseCase<ActivateProductUseCaseImpl.Param, ActivateResult>

class ActivateProductUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : ActivateProductUseCase {

    override suspend fun execute(param: Param): Result<ActivateResult> {
        return warrantyRepository.activateProduct(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val QRCodeContent: String = "",
        val StoreAddress: String = "",
        val StoreName: String = "",
        val UsedAddress: String = "",
        val UsedIdentityCard: String = "",
        val UsedName: String = "",
        val UsedPhone: String = "",
        val UsedProductModel: String = "",
        val UsedWarrantySerial: String = "",
        val User_ID: String = "",
        val Agency_ID: String = "",
        val Serial: String = "",
        val Latitude: String = "",
        val Longitude: String = ""
    ) : BaseParam()
}
