package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotificationResultEntity(
    val notification: NotificationTitleResultEntity?,
    val data: NotificationDataResultEntity?,
    val token: String?,
    val topic: String?
) : EntityModel()