package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.LogoutResultEntity
import checkvn.com.viettiepbhdt.domain.entities.LogoutResult

object LogoutResultMapper : EntityMapper<LogoutResultEntity, LogoutResult> {

    override fun mapToDomains(entity: LogoutResultEntity): LogoutResult {
        return LogoutResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: ""
        )
    }

    override fun mapToEntities(domain: LogoutResult): LogoutResultEntity {
        return LogoutResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage
        )
    }
}
