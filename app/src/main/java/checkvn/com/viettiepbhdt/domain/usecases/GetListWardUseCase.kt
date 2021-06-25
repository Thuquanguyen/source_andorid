package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ListLocationResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface GetListWardUseCase : BaseUseCase<GetListWardUseCaseImpl.Param, ListLocationResult>

class GetListWardUseCaseImpl(
    private val warrantyRepository: WarrantyRepository
) : GetListWardUseCase {

    override suspend fun execute(param: Param): Result<ListLocationResult> {
        return warrantyRepository.getListWard(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val District_ID: String = ""
    ) : BaseParam()
}