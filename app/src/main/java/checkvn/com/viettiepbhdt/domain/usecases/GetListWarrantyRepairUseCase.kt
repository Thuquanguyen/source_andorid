package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListWarrantyRepairResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListWarrantyRepairUseCase :
    BaseUseCase<GetListWarrantyRepairUseCaseImpl.Param, ListWarrantyRepairResult>

class GetListWarrantyRepairUseCaseImpl(private val repository: WarrantyRepository) :
    GetListWarrantyRepairUseCase {

    override suspend fun execute(param: Param): Result<ListWarrantyRepairResult> {
        return repository.getListWarrantyRepair(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val QRCodeContent: String = "",
        val Serial: String = ""
    )
}