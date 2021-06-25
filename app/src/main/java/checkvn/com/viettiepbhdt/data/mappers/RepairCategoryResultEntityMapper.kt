package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.RepairCategoryResultEntity
import checkvn.com.viettiepbhdt.domain.entities.RepairCategoryResult

object RepairCategoryResultEntityMapper :
    EntityMapper<RepairCategoryResultEntity, RepairCategoryResult> {
    override fun mapToDomains(param: RepairCategoryResultEntity): RepairCategoryResult {
        return RepairCategoryResult(
            Name = param.Name ?: "",
            QRCodeWarrantyRepairCategory_ID = param.QRCodeWarrantyRepairCategory_ID ?: 0,
            Sort = param.Sort ?: 0
        )
    }

    override fun mapToEntities(param: RepairCategoryResult): RepairCategoryResultEntity {
        return RepairCategoryResultEntity(
            Name = param.Name,
            QRCodeWarrantyRepairCategory_ID = param.QRCodeWarrantyRepairCategory_ID,
            Sort = param.Sort
        )
    }
}