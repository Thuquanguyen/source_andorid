package checkvn.com.viettiepbhdt.domain.entities

class ListAgencyResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val data: List<AgencyResult>
) : DomainModel()