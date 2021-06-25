package checkvn.com.viettiepbhdt.domain.usecases.base

import checkvn.com.viettiepbhdt.domain.entities.Result

interface BaseUseCase<IN, OUT> {

    suspend fun execute(param: IN): Result<OUT>
}
