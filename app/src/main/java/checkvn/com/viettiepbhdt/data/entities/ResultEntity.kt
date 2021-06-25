package checkvn.com.viettiepbhdt.data.entities


sealed class ResultEntity<out T> {
    data class Success<T>(val data: T) : ResultEntity<T>()
    data class Fail(val error: Error) : ResultEntity<Nothing>()
}
