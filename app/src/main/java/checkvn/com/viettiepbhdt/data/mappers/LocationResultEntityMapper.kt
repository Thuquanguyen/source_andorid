package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.LocationResultEntity
import checkvn.com.viettiepbhdt.domain.entities.LocationResult

object LocationResultEntityMapper : EntityMapper<LocationResultEntity, LocationResult> {
    override fun mapToDomains(entity: LocationResultEntity): LocationResult {
        return LocationResult(
            Code = entity.Code ?: "",
            Ward_ID = entity.Ward_ID ?: 0,
            Name = entity.Name ?: "",
            Location_ID = entity.Location_ID ?: 0,
            District_ID = entity.District_ID ?: 0,
            Description = entity.Description ?: "",
            Sort = entity.Sort ?: 0
        )
    }

    override fun mapToEntities(domain: LocationResult): LocationResultEntity {
        return LocationResultEntity(
            Code = domain.Code,
            Ward_ID = domain.Ward_ID,
            Name = domain.Name,
            Location_ID = domain.Location_ID,
            District_ID = domain.District_ID,
            Description = domain.Description,
            Sort = domain.Sort
        )
    }
}