package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CommonResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?
) : EntityModel()