package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.CreatePasswordEntity
import checkvn.com.viettiepbhdt.domain.entities.CreatePassword

object CreatePasswordMapper : EntityMapper<CreatePasswordEntity, CreatePassword> {

    override fun mapToDomains(entity: CreatePasswordEntity): CreatePassword {
        return CreatePassword(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Transaction_ID = entity.Transaction_ID ?: ""
        )
    }

    override fun mapToEntities(domain: CreatePassword): CreatePasswordEntity {
        return CreatePasswordEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Transaction_ID = domain.Transaction_ID
        )
    }
}
