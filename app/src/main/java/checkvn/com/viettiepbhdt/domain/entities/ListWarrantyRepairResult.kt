package checkvn.com.viettiepbhdt.domain.entities

class ListWarrantyRepairResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val data: List<WarrantyRepairResult>
) : DomainModel()