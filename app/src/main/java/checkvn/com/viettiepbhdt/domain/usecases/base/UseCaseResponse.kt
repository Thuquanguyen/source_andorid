package checkvn.com.viettiepbhdt.domain.usecases.base

import checkvn.com.viettiepbhdt.domain.entities.Error

interface UseCaseResponse<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: Error)
}

