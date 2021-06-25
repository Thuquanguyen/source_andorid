package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyCodeEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val Transaction_ID: String?
) : EntityModel()
