package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.VerifyCodeEntity
import checkvn.com.viettiepbhdt.domain.entities.VerifyCode

object VerifyCodeMapper : EntityMapper<VerifyCodeEntity, VerifyCode> {

    override fun mapToDomains(entity: VerifyCodeEntity): VerifyCode {
        return VerifyCode(
            ErrCode = entity.ErrCode ?: 0,
            ErrMessage = entity.ErrMessage ?: "",
            Transaction_ID = entity.Transaction_ID ?: ""
        )
    }

    override fun mapToEntities(domain: VerifyCode): VerifyCodeEntity {
        return VerifyCodeEntity(
            ErrCode = domain.ErrCode,
            ErrMessage = domain.ErrMessage,
            Transaction_ID = domain.Transaction_ID
        )
    }
}
