package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ProductStatusResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ProductStatusResult

object ProductStatusResultEntityMapper :
    EntityMapper<ProductStatusResultEntity, ProductStatusResult> {
    override fun mapToDomains(param: ProductStatusResultEntity): ProductStatusResult {
        return ProductStatusResult(
            WarrantyStatus_ID = param.WarrantyStatus_ID ?: 0,
            WarrantyStatusMessage = param.WarrantyStatusMessage ?: "",
            ErrCode = param.ErrCode ?: 0,
            ErrMessage = param.ErrMessage ?: ""
        )
    }

    override fun mapToEntities(param: ProductStatusResult): ProductStatusResultEntity {
        return ProductStatusResultEntity(
            WarrantyStatus_ID = param.WarrantyStatus_ID,
            WarrantyStatusMessage = param.WarrantyStatusMessage,
            ErrCode = param.ErrCode,
            ErrMessage = param.ErrMessage
        )
    }
}