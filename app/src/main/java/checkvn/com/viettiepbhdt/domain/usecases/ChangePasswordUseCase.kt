package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.ChangePassword
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface ChangePasswordUseCase : BaseUseCase<ChangePasswordUseCaseImpl.Param, ChangePassword>

class ChangePasswordUseCaseImpl(private val repository: WarrantyRepository) :
    ChangePasswordUseCase {

    override suspend fun execute(param: Param): Result<ChangePassword> {
        return repository.changePassword(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val OldPassword: String,
        val Password: String,
        val UserName: String
    )
}
