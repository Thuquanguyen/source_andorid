package checkvn.com.viettiepbhdt.domain.entities

data class AgencyResult(
    val Address: String,
    val Agency_ID: Int,
    val CreateBy: String,
    val CreateDate: String,
    val Description: String,
    val District_ID: Int,
    val Email: String,
    val LastEditBy: String,
    val LastEditDate: String,
    val Location_ID: Int,
    val Name: String,
    val Phone: String,
    val Phone1: String,
    val Phone2: String,
    val Phone3: String,
    val ProductBrand_ID: Int,
    val Ward_ID: Int,
    val ErrCode: Int,
    val ErrMessage: String,
    val Code: String
) : DomainModel()