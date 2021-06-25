package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.NotificationTitleResultEntity
import checkvn.com.viettiepbhdt.domain.entities.NotificationTitleResult

object NotificationTitleResultEntityMapper :
    EntityMapper<NotificationTitleResultEntity, NotificationTitleResult> {

    override fun mapToDomains(entity: NotificationTitleResultEntity): NotificationTitleResult {
        return NotificationTitleResult(
            title = entity.title ?: ""
        )
    }

    override fun mapToEntities(domain: NotificationTitleResult): NotificationTitleResultEntity {
        return NotificationTitleResultEntity(
            title = domain.title
        )
    }
}