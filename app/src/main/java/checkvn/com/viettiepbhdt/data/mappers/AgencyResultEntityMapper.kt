package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.AgencyResultEntity
import checkvn.com.viettiepbhdt.domain.entities.AgencyResult

object AgencyResultEntityMapper : EntityMapper<AgencyResultEntity, AgencyResult> {
    override fun mapToDomains(entity: AgencyResultEntity): AgencyResult {
        return AgencyResult(
            Address = entity.Address ?: "",
            Agency_ID = entity.Agency_ID ?: 0,
            CreateBy = entity.CreateBy ?: "",
            CreateDate = entity.CreateDate ?: "",
            Description = entity.Description ?: "",
            District_ID = entity.District_ID ?: 0,
            Email = entity.Email ?: "",
            LastEditBy = entity.LastEditBy ?: "",
            LastEditDate = entity.LastEditDate ?: "",
            Location_ID = entity.Location_ID ?: 0,
            Name = entity.Name ?: "",
            Phone = entity.Phone ?: "",
            Phone1 = entity.Phone1 ?: "",
            Phone2 = entity.Phone2 ?: "",
            Phone3 = entity.Phone3 ?: "",
            ProductBrand_ID = entity.ProductBrand_ID ?: 0,
            Ward_ID = entity.Ward_ID ?: 0,
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Code = entity.Code ?: ""
        )
    }

    override fun mapToEntities(domain: AgencyResult): AgencyResultEntity {
        return AgencyResultEntity(
            Address = domain.Address,
            Agency_ID = domain.Agency_ID,
            CreateBy = domain.CreateBy,
            CreateDate = domain.CreateDate,
            Description = domain.Description,
            District_ID = domain.District_ID,
            Email = domain.Email,
            LastEditBy = domain.LastEditBy,
            LastEditDate = domain.LastEditDate,
            Location_ID = domain.Location_ID,
            Name = domain.Name,
            Phone = domain.Phone,
            Phone1 = domain.Phone1,
            Phone2 = domain.Phone2,
            Phone3 = domain.Phone3,
            ProductBrand_ID = domain.ProductBrand_ID,
            Ward_ID = domain.Ward_ID,
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Code = domain.Code
        )
    }
}