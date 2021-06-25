package checkvn.com.viettiepbhdt.domain.entities

data class RequestOtpResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val Transaction_ID: String,
    val UserName: String
) : DomainModel()
