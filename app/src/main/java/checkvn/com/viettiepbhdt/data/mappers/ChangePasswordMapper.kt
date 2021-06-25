package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.ChangePasswordEntity
import checkvn.com.viettiepbhdt.domain.entities.ChangePassword

object ChangePasswordMapper : EntityMapper<ChangePasswordEntity, ChangePassword> {

    override fun mapToDomains(entity: ChangePasswordEntity): ChangePassword {
        return ChangePassword(ErrMessage = entity.ErrMessage ?: "", ErrCode = entity.ErrCode ?: 0)
    }

    override fun mapToEntities(domain: ChangePassword): ChangePasswordEntity {
        return ChangePasswordEntity(
            ErrCode = domain.ErrCode, ErrMessage = domain.ErrMessage
        )
    }
}
