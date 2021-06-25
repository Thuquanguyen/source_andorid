package checkvn.com.viettiepbhdt.domain.entities

data class RecoverPassword(
    val ErrCode: Int,
    val ErrMessage: String,
    val Transaction_ID: String,
    val UserName: Any
) : DomainModel()
