package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListProductResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListProductResult

object ListProductResultEntityMapper : EntityMapper<ListProductResultEntity, ListProductResult> {
    override fun mapToDomains(entity: ListProductResultEntity): ListProductResult {
        return ListProductResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Count = entity.Count ?: 0,
            data = entity.data?.map { ProductResultEntityMapper.mapToDomains(it) } ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListProductResult): ListProductResultEntity {
        return ListProductResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Count = domain.Count,
            data = domain.data.map { ProductResultEntityMapper.mapToEntities(it) }
        )
    }
}
