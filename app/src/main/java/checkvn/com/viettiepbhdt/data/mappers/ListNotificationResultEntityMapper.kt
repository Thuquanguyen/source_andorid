package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListNotificationResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListNotificationResult

object ListNotificationResultEntityMapper :
    EntityMapper<ListNotificationResultEntity, ListNotificationResult> {
    override fun mapToDomains(entity: ListNotificationResultEntity): ListNotificationResult {
        return ListNotificationResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Count = entity.Count ?: 0,
            data = entity.data?.map { NotificationResultEntityMapper.mapToDomains(it) }
                ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListNotificationResult): ListNotificationResultEntity {
        return ListNotificationResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Count = domain.Count,
            data = domain.data.map { NotificationResultEntityMapper.mapToEntities(it) }
        )
    }
}
