package checkvn.com.viettiepbhdt.domain.entities

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Fail(val error: Error) : Result<Nothing>()
}
