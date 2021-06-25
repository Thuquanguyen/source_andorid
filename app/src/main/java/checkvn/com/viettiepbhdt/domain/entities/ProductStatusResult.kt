package checkvn.com.viettiepbhdt.domain.entities

class ProductStatusResult (
    val WarrantyStatus_ID: Int,
    val WarrantyStatusMessage: String,
    val ErrCode: Int,
    val ErrMessage: String
) : DomainModel()