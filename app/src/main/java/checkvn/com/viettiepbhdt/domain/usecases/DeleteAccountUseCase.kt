package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.CommonResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseParam
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface DeleteAccountUseCase :
    BaseUseCase<DeleteAccountUseCaseImpl.Param, CommonResult>

class DeleteAccountUseCaseImpl(private val repository: WarrantyRepository) :
    DeleteAccountUseCase {

    override suspend fun execute(param: Param): Result<CommonResult> {
        return repository.deleteAccount(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val UserName: String = "",
        val Password: String = "",
        val FireBaseToken: String = ""
    ) : BaseParam()
}