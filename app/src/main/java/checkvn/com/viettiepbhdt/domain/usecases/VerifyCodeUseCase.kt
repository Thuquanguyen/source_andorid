package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.entities.VerifyCode
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface VerifyCodeUseCase : BaseUseCase<VerifyCodeUseCaseImpl.Param, VerifyCode>

class VerifyCodeUseCaseImpl(private val repository: WarrantyRepository) : VerifyCodeUseCase {

    override suspend fun execute(param: Param): Result<VerifyCode> {
        return repository.verifyCode(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Transaction_ID: String,
        val Code: String
    )
}
