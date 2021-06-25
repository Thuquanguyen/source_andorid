package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActivateResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val Image: String?,
    val ProductBrandName: String?,
    val ProductBrand_ID: Int?,
    val ProductName: String?,
    val Product_ID: Int?,
    val StoreAddress: String?,
    val StoreName: String?,
    val UsedAddress: String?,
    val UsedIdentityCard: String?,
    val UsedName: String?,
    val UsedPhone: String?,
    val UsedWarrantySerial: String?,
    val User_ID: String?,
    val WarrantyActiveByName: String?,
    val WarrantyEndDate: String?,
    val WarrantyMonth: Int?,
    val WarrantyStartDate: String?,
    val QRCodeContent:  String?,
    val Serial:  String?
) : EntityModel()
