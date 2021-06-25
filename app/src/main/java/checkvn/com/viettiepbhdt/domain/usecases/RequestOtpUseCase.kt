package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.RequestOtpResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface RequestOtpUseCase : BaseUseCase<RequestOtpUseCaseImpl.Param, RequestOtpResult>

class RequestOtpUseCaseImpl(private val repository: WarrantyRepository) : RequestOtpUseCase {

    override suspend fun execute(param: Param): Result<RequestOtpResult> {
        return repository.requestOtp(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val UserName: String
    )
}
