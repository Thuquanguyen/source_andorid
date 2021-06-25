package checkvn.com.viettiepbhdt.domain.entities

sealed class LoginResultType {
    object OnCancel : LoginResultType()
    object OnError : LoginResultType()
    object OnSuccess : LoginResultType()
}
