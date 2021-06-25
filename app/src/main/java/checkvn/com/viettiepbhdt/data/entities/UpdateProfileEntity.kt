package checkvn.com.viettiepbhdt.data.entities

import checkvn.com.viettiepbhdt.domain.entities.UpdateProfile
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateProfileEntity(
    val ErrCode: Int?,
    val ErrMessage: String?
) : EntityModel() {

    fun mapToDomain(): UpdateProfile {
        return UpdateProfile(
            ErrCode = ErrCode,
            ErrMessage = ErrMessage
        )
    }
}
