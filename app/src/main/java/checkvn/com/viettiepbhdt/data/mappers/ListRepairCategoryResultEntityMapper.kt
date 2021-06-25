package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListRepairCategoryResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListRepairCategoryResult

object ListRepairCategoryResultEntityMapper :
    EntityMapper<ListRepairCategoryResultEntity, ListRepairCategoryResult> {
    override fun mapToDomains(entity: ListRepairCategoryResultEntity): ListRepairCategoryResult {
        return ListRepairCategoryResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            data = entity.data?.map { RepairCategoryResultEntityMapper.mapToDomains(it) } ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListRepairCategoryResult): ListRepairCategoryResultEntity {
        return ListRepairCategoryResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            data = domain.data.map { RepairCategoryResultEntityMapper.mapToEntities(it) }
        )
    }
}
