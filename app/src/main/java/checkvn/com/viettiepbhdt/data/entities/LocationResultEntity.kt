package checkvn.com.viettiepbhdt.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationResultEntity(
    val Code: String?,
    val Description: String?,
    val District_ID: Int?,
    val Location_ID: Int?,
    val Name: String?,
    val Sort: Int?,
    val Ward_ID: Int?
) : EntityModel()