package checkvn.com.viettiepbhdt.domain.entities

data class CreatePassword(
    val ErrCode: Int,
    val ErrMessage: String,
    val Transaction_ID: String
) : DomainModel()
