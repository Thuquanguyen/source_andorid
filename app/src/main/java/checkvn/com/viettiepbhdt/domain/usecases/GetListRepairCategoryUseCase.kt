package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListRepairCategoryResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListRepairCategoryUseCase :
    BaseUseCase<GetListRepairCategoryUseCaseImpl.Param, ListRepairCategoryResult>

class GetListRepairCategoryUseCaseImpl(private val repository: WarrantyRepository) :
    GetListRepairCategoryUseCase {

    override suspend fun execute(param: Param): Result<ListRepairCategoryResult> {
        return repository.getListRepairCategory(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val User_ID: String = ""
    )
}