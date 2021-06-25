package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.RecoverPassword
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface RecoverPasswordUseCase : BaseUseCase<RecoverPasswordUseCaseImpl.Param, RecoverPassword>

class RecoverPasswordUseCaseImpl(private val repository: WarrantyRepository) :
    RecoverPasswordUseCase {

    override suspend fun execute(param: Param): Result<RecoverPassword> {
        return repository.recoverPassword(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val UserName: String
    )
}
