package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WarrantyRepairResultEntity(
    val Content: String?,
    val CreateBy: String?,
    val CreateByFullName: String?,
    val CreateByPhone: String?,
    val CreateDate: String?,
    val ProductBrandName: String?,
    val ProductBrand_ID: Int?,
    val ProductName: String?,
    val Product_ID: Int?,
    val QRCodeContent: String?,
    val QRCodeWarrantyRepairCategoryName: String?,
    val QRCodeWarrantyRepairCategory_ID: Int?,
    val QRCodeWarrantyRepair_ID: Int?,
    val SerialNumber: String?
) : EntityModel()