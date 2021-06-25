package checkvn.com.viettiepbhdt.data.mappers

import checkvn.com.viettiepbhdt.data.entities.CommonResultEntity
import checkvn.com.viettiepbhdt.domain.entities.CommonResult

object CommonResultEntityMapper : EntityMapper<CommonResultEntity, CommonResult> {
    override fun mapToDomains(param: CommonResultEntity): CommonResult {
        return CommonResult(
            ErrCode = param.ErrCode ?: 0,
            ErrMessage = param.ErrMessage ?: ""
        )
    }

    override fun mapToEntities(param: CommonResult): CommonResultEntity {
        return CommonResultEntity(
            ErrCode = param.ErrCode,
            ErrMessage = param.ErrMessage
        )
    }
}