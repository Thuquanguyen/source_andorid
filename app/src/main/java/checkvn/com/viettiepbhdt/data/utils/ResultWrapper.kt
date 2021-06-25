package checkvn.com.viettiepbhdt.data.utils

import checkvn.com.viettiepbhdt.domain.entities.Error
import checkvn.com.viettiepbhdt.domain.entities.Result
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

inline fun <T> resultWrapper(call: () -> T): Result<T> {
    return try {
        val domainModel = call()
        Result.Success(domainModel)
    } catch (exception: Exception) {
        Result.Fail(errorHandle(exception))
    }
}

fun errorHandle(throwable: Throwable): Error {
    return when (throwable) {
        is IOException -> Error.Network
        is HttpException -> {
            when (throwable.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> Error.NotFound
                HttpURLConnection.HTTP_FORBIDDEN -> Error.AccessDenied
                HttpURLConnection.HTTP_UNAVAILABLE -> Error.ServiceUnavailable
                HttpURLConnection.HTTP_BAD_REQUEST -> Error.BadRequest
                else -> Error.Unknown
            }
        }
        else -> Error.Unknown
    }
}
