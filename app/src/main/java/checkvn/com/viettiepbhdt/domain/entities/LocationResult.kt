package checkvn.com.viettiepbhdt.domain.entities

data class LocationResult(
    val Code: String,
    val Description: String,
    val District_ID: Int,
    val Location_ID: Int,
    val Name: String,
    val Sort: Int,
    val Ward_ID: Int
) : DomainModel()