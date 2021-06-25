package checkvn.com.viettiepbhdt.data.entities

import checkvn.com.viettiepbhdt.domain.entities.Profile
import checkvn.com.viettiepbhdt.utils.extensions.getNotBlankValue
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileEntity(
    val AvatarUrl: String?,
    val BirthDate: String?,
    val Email: String?,
    val ErrCode: Int?,
    val ErrMessage: String?,
    val FullName: String?,
    val Gender: String?,
    val Phone: String?,
    val ProductBrandName: Any?,
    val ProductBrand_ID: Any?,
    val RoleName: String?,
    val RoleType: Int?,
    val UserName: String?,
    val User_ID: String?,
    val Agency_ID: String?,
    val AgencyName: String?,
    val Address: String?,
    val Location_ID: String?,
    val District_ID: String?,
    val Ward_ID: String?,
    val Point: Int?
) : EntityModel() {

    fun mapToDomains(): Profile {
        return Profile(
            AvatarUrl = this.AvatarUrl ?: "",
            BirthDate = getNotBlankValue(BirthDate),
            Email = getNotBlankValue(Email),
            ErrCode = this.ErrCode ?: 0,
            ErrMessage = this.ErrMessage ?: "",
            FullName = getNotBlankValue(FullName),
            Gender = getNotBlankValue(Gender),
            Phone = getNotBlankValue(Phone),
            ProductBrandName = this.ProductBrandName ?: "",
            ProductBrand_ID = this.ProductBrand_ID ?: "",
            RoleName = this.RoleName ?: "",
            RoleType = this.RoleType ?: 0,
            UserName = getNotBlankValue(UserName),
            User_ID = this.User_ID!!,  //This field is required.
            Agency_ID = this.Agency_ID ?: "",
            AgencyName = this.AgencyName ?: "",
            Address = this.Address ?: "",
            Location_ID = this.Location_ID ?: "",
            District_ID = this.District_ID ?: "",
            Ward_ID = this.Ward_ID ?: "",
            Point = this.Point ?: 0
        )
    }

}
