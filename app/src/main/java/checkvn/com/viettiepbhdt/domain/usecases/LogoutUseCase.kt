package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.LogoutResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface LogoutUseCase :
    BaseUseCase<LogoutUseCaseImpl.Param, LogoutResult>

class LogoutUseCaseImpl(private val repository: WarrantyRepository) :
    LogoutUseCase {

    override suspend fun execute(param: Param): Result<LogoutResult> {
        return repository.logout(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val FireBaseToken: String,
        val User_ID: String
    )
}
