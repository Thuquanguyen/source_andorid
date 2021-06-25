package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class NotificationTitleResultEntity(
    val title: String?
) : EntityModel()