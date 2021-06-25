package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListLocationResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListLocationResult

object ListLocationResultEntityMapper : EntityMapper<ListLocationResultEntity, ListLocationResult> {
    override fun mapToDomains(entity: ListLocationResultEntity): ListLocationResult {
        return ListLocationResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            data = entity.data?.map { LocationResultEntityMapper.mapToDomains(it) } ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListLocationResult): ListLocationResultEntity {
        return ListLocationResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            data = domain.data?.map { LocationResultEntityMapper.mapToEntities(it) }
        )
    }
}