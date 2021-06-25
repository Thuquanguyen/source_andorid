package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestOtpResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val Transaction_ID: String?,
    val UserName: String?
) : EntityModel()
