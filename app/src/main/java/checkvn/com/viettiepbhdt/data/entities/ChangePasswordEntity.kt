package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChangePasswordEntity(
    val ErrCode: Int?,
    val ErrMessage: String?
) : EntityModel()
