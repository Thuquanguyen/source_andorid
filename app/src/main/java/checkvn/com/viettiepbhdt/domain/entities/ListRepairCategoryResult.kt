package checkvn.com.viettiepbhdt.domain.entities

data class ListRepairCategoryResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val data: List<RepairCategoryResult>
) : DomainModel()