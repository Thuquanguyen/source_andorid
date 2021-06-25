package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.RequestOtpResultEntity
import checkvn.com.viettiepbhdt.domain.entities.RequestOtpResult

object OtpResultMapper : EntityMapper<RequestOtpResultEntity, RequestOtpResult> {

    override fun mapToDomains(entity: RequestOtpResultEntity): RequestOtpResult {
        return RequestOtpResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Transaction_ID = entity.Transaction_ID ?: "",
            UserName = entity.UserName ?: ""
        )
    }

    override fun mapToEntities(domain: RequestOtpResult): RequestOtpResultEntity {
        return RequestOtpResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Transaction_ID = domain.Transaction_ID,
            UserName = domain.UserName
        )
    }
}
