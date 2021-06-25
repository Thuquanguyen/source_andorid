package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.NotificationDataResultEntity
import checkvn.com.viettiepbhdt.domain.entities.NotificationDataResult

object NotificationDataResultEntityMapper :
    EntityMapper<NotificationDataResultEntity, NotificationDataResult> {

    override fun mapToDomains(param: NotificationDataResultEntity): NotificationDataResult {
        return NotificationDataResult(
            FCMMessage_ID = param.FCMMessage_ID ?: 0,
            CreateDate = param.CreateDate ?: "",
            NotificationType = param.NotificationType ?: 0
        )
    }

    override fun mapToEntities(param: NotificationDataResult): NotificationDataResultEntity {
        return NotificationDataResultEntity(
            FCMMessage_ID = param.FCMMessage_ID,
            CreateDate = param.CreateDate,
            NotificationType = param.NotificationType
        )
    }
}