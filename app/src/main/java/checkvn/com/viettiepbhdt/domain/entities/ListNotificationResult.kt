package checkvn.com.viettiepbhdt.domain.entities

class ListNotificationResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val Count: Int,
    val data: List<NotificationResult>
) : DomainModel()