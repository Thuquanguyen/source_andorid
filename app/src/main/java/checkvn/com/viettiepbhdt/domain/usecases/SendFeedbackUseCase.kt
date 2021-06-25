package checkvn.com.viettiepbhdt.domain.usecases

import checkvn.com.viettiepbhdt.domain.entities.FeedbackResult
import checkvn.com.viettiepbhdt.domain.entities.Result
import checkvn.com.viettiepbhdt.domain.repositories.WarrantyRepository
import checkvn.com.viettiepbhdt.domain.usecases.base.BaseUseCase
import com.squareup.moshi.JsonClass

interface SendFeedbackUseCase : BaseUseCase<SendFeedbackUseCaseImpl.Param, FeedbackResult>

class SendFeedbackUseCaseImpl(private val repository: WarrantyRepository) :
    SendFeedbackUseCase {

    override suspend fun execute(param: Param): Result<FeedbackResult> {
        return repository.sendFeedback(param)
    }

    @JsonClass(generateAdapter = true)
    data class Param(
        val FullName: String,
        val Phone: String,
        val Email: String,
        val Content: String
    )


}