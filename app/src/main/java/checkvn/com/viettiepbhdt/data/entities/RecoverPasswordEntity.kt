package checkvn.com.viettiepbhdt.data.entities

import checkvn.com.viettiepbhdt.domain.entities.RecoverPassword
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecoverPasswordEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val Transaction_ID: String?,
    val UserName: String?
) : EntityModel() {

    fun mapToDomain(): RecoverPassword {
        return RecoverPassword(
            ErrCode = this.ErrCode ?: 0,
            ErrMessage = this.ErrMessage ?: "",
            Transaction_ID = this.Transaction_ID ?: "",
            UserName = this.UserName ?: ""
        )
    }
}

