package checkvn.com.viettiepbhdt.domain.entities

data class Profile(
    val AvatarUrl: String,
    val BirthDate: String,
    val Email: String,
    val ErrCode: Int,
    val ErrMessage: String,
    val FullName: String,
    val Gender: String,
    val Phone: String,
    val ProductBrandName: Any,
    val ProductBrand_ID: Any,
    val RoleName: String,
    val RoleType: Int,
    val UserName: String,
    val User_ID: String,
    val Agency_ID: String,
    val AgencyName: String,
    val Address: String,
    val Location_ID: String,
    val District_ID: String,
    val Ward_ID: String,
    val Point: Int
) : DomainModel()
