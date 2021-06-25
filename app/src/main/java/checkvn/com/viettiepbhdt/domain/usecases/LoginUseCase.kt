package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.LoginResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface LoginUseCase : BaseUseCase<LoginUseCaseImpl.Param, LoginResult>

class LoginUseCaseImpl(private val repository: WarrantyRepository) : LoginUseCase {

    override suspend fun execute(param: Param): Result<LoginResult> {
        return repository.login(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val FireBaseToken: String,
        val Password: String,
        val UserName: String
    )
}
