package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val Image: String?,
    val NoItem: Int?,
    val ProductBrandName: String?,
    val ProductBrand_ID: Int?,
    val ProductName: String?,
    val Product_ID: Int?,
    val QRCodeSecretContent: String?,
    val QRCodeStatus_ID: Int?,
    val QRCodeType_ID: Int?,
    val StoreAddress: String?,
    val StoreName: String?,
    val UsedAddress: String?,
    val UsedIdentityCard: String?,
    val UsedName: String?,
    val UsedPhone: String?,
    val UsedWarrantySerial: String?,
    val User_ID: String?,
    val WarrantyActiveBy: String?,
    val WarrantyActiveByName: String?,
    val WarrantyEndDate: String?,
    val WarrantyMonth: Int?,
    val WarrantyStartDate: String?,
    val WarrantyStatus_ID: Int?,
    val ProductContent: String?,
    val ActiveAddress: String?
) : EntityModel()