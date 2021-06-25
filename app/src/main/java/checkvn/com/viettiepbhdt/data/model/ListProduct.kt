package checkvn.com.viettiepbhdt.data.model

import checkvn.com.viettiepbhdt.domain.entities.ProductResult

data class ListProduct(
    val title: String,
    val products: List<ProductResult>
)
