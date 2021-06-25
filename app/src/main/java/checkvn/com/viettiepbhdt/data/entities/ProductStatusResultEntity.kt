package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ProductStatusResultEntity(
    val WarrantyStatus_ID: Int?,
    val WarrantyStatusMessage: String?,
    val ErrCode: Int?,
    val ErrMessage: String?
) : EntityModel()