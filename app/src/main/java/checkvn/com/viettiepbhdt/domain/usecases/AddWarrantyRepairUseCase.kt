package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface AddWarrantyRepairUseCase :
    BaseUseCase<AddWarrantyRepairUseCaseImpl.Param, CommonResult>

class AddWarrantyRepairUseCaseImpl(private val repository: WarrantyRepository) :
    AddWarrantyRepairUseCase {

    override suspend fun execute(param: Param): Result<CommonResult> {
        return repository.addWarrantyRepair(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Content: String = "",
        val QRCodeContent: String = "",
        val QRCodeWarrantyRepairCategory_IDs: String = "",
        val Serial: String = "",
        val User_ID: String = ""
    ) : BaseParam()
}