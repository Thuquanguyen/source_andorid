package checkvn.com.viettiepbhdt.domain.entities

class NotificationResult(
    val notification: NotificationTitleResult,
    val data: NotificationDataResult,
    val token: String,
    val topic: String
) : DomainModel()