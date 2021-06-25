package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListNotificationResultEntity(
    val Count: Int?,
    val ErrCode: Int?,
    val ErrMessage: String?,
    val data: List<NotificationResultEntity>?
) : EntityModel()
