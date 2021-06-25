package checkvn.com.viettiepbhdt.domain.entities

data class VerifyCode(
    val ErrCode: Int,
    val ErrMessage: String,
    val Transaction_ID: String
)
