package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ListAgencyResultEntity
import checkvn.com.viettiepbhdt.domain.entities.ListAgencyResult

object ListAgencyResultEntityMapper : EntityMapper<ListAgencyResultEntity, ListAgencyResult> {
    override fun mapToDomains(entity: ListAgencyResultEntity): ListAgencyResult {
        return ListAgencyResult(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            data = entity.data?.map { AgencyResultEntityMapper.mapToDomains(it) } ?: emptyList()
        )
    }

    override fun mapToEntities(domain: ListAgencyResult): ListAgencyResultEntity {
        return ListAgencyResultEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            data = domain.data?.map { AgencyResultEntityMapper.mapToEntities(it) }
        )
    }
}