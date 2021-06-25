package checkvn.com.viettiepbhdt.domain.entities

class ListProductResult(
    val ErrCode: Int,
    val ErrMessage: String,
    val Count: Int,
    val data: List<ProductResult>
) : DomainModel()
