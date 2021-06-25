package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListWarrantyRepairResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val data: List<WarrantyRepairResultEntity>?
) : EntityModel()