package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.NotificationResultEntity
import checkvn.com.viettiepbhdt.domain.entities.NotificationDataResult
import checkvn.com.viettiepbhdt.domain.entities.NotificationResult
import checkvn.com.viettiepbhdt.domain.entities.NotificationTitleResult

object NotificationResultEntityMapper : EntityMapper<NotificationResultEntity, NotificationResult> {
    override fun mapToDomains(entity: NotificationResultEntity): NotificationResult {
        return NotificationResult(
            notification = entity.notification?.let {
                NotificationTitleResultEntityMapper.mapToDomains(
                    it
                )
            } ?: NotificationTitleResult(""),
            data = entity.data?.let { NotificationDataResultEntityMapper.mapToDomains(it) }
                ?: NotificationDataResult(-1, "", -1),
            token = entity.token ?: "",
            topic = entity.topic ?: ""
        )
    }

    override fun mapToEntities(domain: NotificationResult): NotificationResultEntity {
        return NotificationResultEntity(
            notification = NotificationTitleResultEntityMapper.mapToEntities(domain.notification),
            data = NotificationDataResultEntityMapper.mapToEntities(domain.data),
            token = domain.token,
            topic = domain.topic
        )
    }
}