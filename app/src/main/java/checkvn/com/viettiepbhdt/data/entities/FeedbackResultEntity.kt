package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class FeedbackResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?
) : EntityModel()