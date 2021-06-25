package checkvn.com.viettiepbhdt.domain.entities

sealed class Error {
    object BadRequest : Error()
    object AccessDenied : Error()
    object ServiceUnavailable : Error()
    object Unknown : Error()
    object Network : Error()
    object NotFound : Error()
}
