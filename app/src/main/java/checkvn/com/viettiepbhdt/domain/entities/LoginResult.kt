package checkvn.com.viettiepbhdt.domain.entities

data class LoginResult(
    val AvatarUrl: String,
    val ErrCode: Int,
    val ErrMessage: String,
    val FullName: String,
    val ProductBrandName: Any,
    val ProductBrand_ID: Int,
    val RoleType: Int,
    val RoleName: String,
    val UserName: String,
    val User_ID: String
) : DomainModel()
