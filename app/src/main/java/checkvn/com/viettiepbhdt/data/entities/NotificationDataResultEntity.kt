package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NotificationDataResultEntity(
    val FCMMessage_ID: Int?,
    val CreateDate: String?,
    val NotificationType: Int?
) : EntityModel()