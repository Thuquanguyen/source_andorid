package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.LoginResultEntity
import checkvn.com.viettiepbhdt.domain.entities.LoginResult

object LoginResultMapper : EntityMapper<LoginResultEntity, LoginResult> {

    override fun mapToDomains(entity: LoginResultEntity): LoginResult {
        return LoginResult(
            AvatarUrl = entity.AvatarUrl ?: "",
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            FullName = entity.FullName ?: "",
            ProductBrandName = entity.ProductBrandName ?: "",
            ProductBrand_ID = entity.ProductBrand_ID ?: 0,
            RoleType = entity.RoleType ?: 0,
            RoleName = entity.RoleName ?: "",
            UserName = entity.UserName ?: "",
            User_ID = entity.User_ID ?: ""
        )
    }

    override fun mapToEntities(domain: LoginResult): LoginResultEntity {
        return LoginResultEntity(
            AvatarUrl = domain.AvatarUrl,
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            FullName = domain.FullName,
            ProductBrandName = domain.ProductBrandName,
            ProductBrand_ID = domain.ProductBrand_ID,
            RoleType = domain.RoleType,
            RoleName = domain.RoleName,
            UserName = domain.UserName,
            User_ID = domain.User_ID
        )
    }
}
