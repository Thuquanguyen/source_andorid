package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListRepairCategoryResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val data: List<RepairCategoryResultEntity>?
) : EntityModel()