package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListLocationResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val data: List<LocationResultEntity>?
) : EntityModel()