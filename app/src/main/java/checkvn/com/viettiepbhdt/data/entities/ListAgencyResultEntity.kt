package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ListAgencyResultEntity(
    val ErrCode: Int?,
    val ErrMessage: String?,
    val data: List<AgencyResultEntity>?
) : EntityModel()