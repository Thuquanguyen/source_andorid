package checkvn.com.viettiepbhdt.utils

import com.pixplicity.easyprefs.library.Prefs

fun isUsed() = Prefs.getBoolean(Constants.USED, false)
fun setUsed() = Prefs.putBoolean(Constants.USED, true)

fun setMethod(method: String) = Prefs.putString(Constants.METHOD, method)
fun getMethod(): String = Prefs.getString(Constants.METHOD, "")

fun setEmail(email: String?) = Prefs.putString(Constants.EMAIL, email)
fun getEmail(): String = Prefs.getString(Constants.EMAIL, "")

fun setPhone(email: String?) = Prefs.putString(Constants.USERNAME, email)
fun getPhone(): String = Prefs.getString(Constants.USERNAME, "")

fun setBirth(email: String?) = Prefs.putString(Constants.BIRTH, email)
fun getBirth(): String = Prefs.getString(Constants.BIRTH, "")

fun setGender(email: String?) = Prefs.putString(Constants.GENDER, email)
fun getGender(): String = Prefs.getString(Constants.GENDER, "")

fun setAvatarUri(uri: String?) = Prefs.putString(Constants.AvatarUrl, uri)
fun getAvatarUri(): String = Prefs.getString(Constants.AvatarUrl, "")

fun setFullName(name: String?) = Prefs.putString(Constants.FULLNAME, name)
fun getFullName(): String = Prefs.getString(Constants.FULLNAME, "")

fun setToken(token: String) = Prefs.putString(Constants.TOKEN, token)
fun getToken(): String = Prefs.getString(Constants.TOKEN, "")
fun removeToken() = Prefs.remove(Constants.TOKEN)

fun getUserId(): String = Prefs.getString(Constants.USER_ID, "")
fun getUserName(): String = Prefs.getString(Constants.USERNAME, "")

fun getScanMode(): Int = Prefs.getInt(Constants.SCAN_MODE, 0)

fun setRoleName(roleName: String?) = Prefs.putString(Constants.ROLENAME, roleName)
fun getRoleName(): String = Prefs.getString(Constants.ROLENAME, "")

fun setRoleType(roleType: Int) = Prefs.putInt(Constants.ROLE_TYPE, roleType)
fun getRoleType(): Int = Prefs.getInt(Constants.ROLE_TYPE, 1)

fun setAgencyId(agencyId: String?) = Prefs.putString(Constants.AGENCY_ID, agencyId)
fun getAgencyId(): String = Prefs.getString(Constants.AGENCY_ID, "")

fun setAgencyName(agencyName: String?) = Prefs.putString(Constants.AGENCY_NAME, agencyName)
fun getAgencyName(): String = Prefs.getString(Constants.AGENCY_NAME, "")

fun setAgencyAddress(agencyName: String?) = Prefs.putString(Constants.AGENCY_ADDRESS, agencyName)
fun getAgencyAddress(): String = Prefs.getString(Constants.AGENCY_ADDRESS, "")

fun setShowActiveResult(isShow: Boolean) = Prefs.putBoolean(Constants.IS_SHOW_ACTIVE_RESULT, isShow)
fun isShowActiveResult(): Boolean = Prefs.getBoolean(Constants.IS_SHOW_ACTIVE_RESULT, true)

fun setShowConfirmActive(isShow: Boolean) = Prefs.putBoolean(Constants.IS_SHOW_CONFIRM_ACTIVE, isShow)
fun isShowConfirmActive(): Boolean = Prefs.getBoolean(Constants.IS_SHOW_CONFIRM_ACTIVE, true)

fun setLastLocation(location: String?) = Prefs.putString(Constants.LAST_LOCATION, location)
fun getLastLocation(): String = Prefs.getString(Constants.LAST_LOCATION, "")

fun <T> savePrefs(key: String, data: T) {
    when (data) {
        is String -> {
            Prefs.putString(key, data)
        }
        is Int -> {
            Prefs.putInt(key, data)
        }
        is Long -> {
            Prefs.putLong(key, data)
        }
        is Boolean -> {
            Prefs.putBoolean(key, data)
        }
        is Double -> {
            Prefs.putDouble(key, data)
        }
    }
}
