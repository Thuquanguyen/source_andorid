package checkvn.com.viettiepbhdt.domain.entities

data class LogoutResult(
    val ErrCode: Int,
    val ErrMessage: String
) : DomainModel()
