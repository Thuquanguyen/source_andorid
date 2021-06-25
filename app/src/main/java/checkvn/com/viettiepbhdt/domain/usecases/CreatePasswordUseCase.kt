package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.CreatePassword
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface CreatePasswordUseCase : BaseUseCase<CreatePasswordUseCaseImpl.Param, CreatePassword>

class CreatePasswordUseCaseImpl(private val repository: WarrantyRepository) :
    CreatePasswordUseCase {

    override suspend fun execute(param: Param): Result<CreatePassword> {
        return repository.createPassword(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val Code: String,
        val Password: String,
        val Transaction_ID: String
    )
}
