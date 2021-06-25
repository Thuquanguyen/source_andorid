package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LogoutResultEntity(
    val ErrCode: Int? = null,
    val ErrMessage: String? = null
) : EntityModel()
