package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.FeedbackResultEntity
import checkvn.com.viettiepbhdt.domain.entities.FeedbackResult

object FeedbackResultEntityMapper : EntityMapper<FeedbackResultEntity, FeedbackResult> {
    override fun mapToDomains(entity: FeedbackResultEntity): FeedbackResult {
        return FeedbackResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: ""
        )
    }

    override fun mapToEntities(domain: FeedbackResult): FeedbackResultEntity {
        return FeedbackResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage
        )
    }
}