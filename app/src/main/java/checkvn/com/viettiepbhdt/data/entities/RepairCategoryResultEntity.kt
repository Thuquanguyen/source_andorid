package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepairCategoryResultEntity(
    val Name: String?,
    val QRCodeWarrantyRepairCategory_ID: Int?,
    val Sort: Int?
) : EntityModel()