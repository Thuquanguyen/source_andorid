package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListWarrantyRepairResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListWarrantyRepairResult

object ListWarrantyRepairResultEntityMapper :
    EntityMapper<ListWarrantyRepairResultEntity, ListWarrantyRepairResult> {
    override fun mapToDomains(entity: ListWarrantyRepairResultEntity): ListWarrantyRepairResult {
        return ListWarrantyRepairResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            data = entity.data?.map { WarrantyRepairResultEntityMapper.mapToDomains(it) }
                ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListWarrantyRepairResult): ListWarrantyRepairResultEntity {
        return ListWarrantyRepairResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            data = domain.data.map { WarrantyRepairResultEntityMapper.mapToEntities(it) }
        )
    }
}
