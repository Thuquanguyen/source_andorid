package checkvn.com.viettiepbhdt.utils

import checkvn.com.viettiepbhdt.R

object Constants {

    const val USED = "USED"
    const val TOKEN = "TOKEN"
    const val METHOD = "METHOD"
    const val FULLNAME = "FULLNAME"
    const val EMAIL = "EMAIL"
    const val AvatarUrl = "AvatarUrl"
    const val ACTION_NAME = "ACTION_NAME"
    const val USER_ID = "USER_ID"
    const val USERNAME = "USERNAME"
    const val ROLENAME = "ROLENAME"
    const val ROLE_TYPE = "ROLE_TYPE"
    const val BIRTH = "BIRTH"
    const val GENDER = "GENDER"
    const val PHONE = "PHONE"
    const val AGENCY = "AGENCY"
    const val AGENCY_ID = "AGENCY_ID"
    const val AGENCY_NAME = "AGENCY_NAME"
    const val PROVINCE_ID = "PROVINCE_ID"
    const val DISTRICT_ID = "DISTRICT_ID"
    const val WARD_ID = "WARD_ID"
    const val AGENCY_ADDRESS = "AGENCY_ADDRESS"
    const val IS_SHOW_CONFIRM_ACTIVE = "IS_SHOW_CONFIRM_ACTIVE"
    const val IS_SHOW_ACTIVE_RESULT = "IS_SHOW_ACTIVE_RESULT"
    const val NOTIFICATION = "NOTIFICATION"
    const val SOUND = "SOUND"
    const val SCAN_MODE = "SCAN_MODE"
    const val LAST_LOCATION = "LAST_LOCATION"
    const val VIETTIEP_URL = "http://khoaviettiep.com.vn"
    const val ABOUT_US_URL = "https://check.net.vn/viettiep/aboutus"
    const val POLICY_URL = "https://check.net.vn/viettiep/policy"
    const val FAQ_URL = "https://check.net.vn/viettiep/faq"
    const val FACEBOOK_LINK = "https://www.facebook.com/khoaviettiepniemtincuamoinha"
    const val YOUTUBE_LINK =
        "https://www.youtube.com/channel/UCY7yFs6Dw7caF8SOnyg6unQ?zdlink=Uo9XRcHoRsba8ZeYOszjBcTlRsTiPIvXRcHoRsbaBdblTNHrOcKYB29fRtCYEdiYSsDePMrbNtLoR28w8dfXR6yjDJGqC30tDZOqEbmlN2yYB29XS71fP28w8ZKqD30mDpOsD29zVG"
    const val DELAY = 1000
    const val VERIFY_CODE_WARRANTY_STAFF = "VTKCS"
    const val CHANNEL_ID = "KVT123"
    const val CHANNEL_NAME = "KhoaVietTiep"
    const val GPS_REQUEST = 789

    enum class OverviewItemProperties(
        val icon: Int,
        val textColor: Int,
        val backgroundColor: Int
    ) {
        IN_WARRANTY(R.drawable.ic_verify, R.color.colorTextGreen, R.color.colorBackgroundGreen),
        WARRANTY_EXPIRE(
            R.drawable.ic_warning,
            R.color.colorTextYellow,
            R.color.colorBackgroundYellow
        ),
        EXTENDED_WARRANTY(
            R.drawable.ic_extension,
            R.color.colorTextOrange,
            R.color.colorBackgroundOrange
        ),
        EXPIRED_WARRANTY(R.drawable.ic_saved, R.color.colorTextRed, R.color.colorBackgroundRed)
    }

    enum class SCREENNAME(val action: String) {
        PROFILE("PROFILE"),
        PERSONAL("PERSONAL"),
        PASSWORD("PASSWORD"),
        STORAGE("STORAGE"),
        SCAN_MODE("SCAN_MODE"),
        HISTORY("HISTORY"),
        BRAND("BRAND"),
        ACTIVATE("ACTIVATE"),
        NOTIFICATION("NOTIFICATION"),
        CHANGEPASSWORD("CHANGEPASSWORD")
    }

    enum class OrderBy(val title: String, val value: String) {
        WARRANTY_START_DATE_DESC("Thời điểm kích hoạt (mới nhất)", "WarrantyStartDate DESC"),
        WARRANTY_START_DATE_ASC("Thời điểm kích hoạt (cũ nhất)", "WarrantyStartDate ASC"),
        WARRANTY_END_DATE_ASC("Hiệu lực (tăng dần)", "WarrantyEndDate ASC"),
        WARRANTY_END_DATE_DESC("Hiệu lực (giảm dần)", "WarrantyEndDate DESC");
    }

    object UserRole {
        const val CONSUMERS = 1
        const val AGENCY = 2
        const val WARRANTY_STAFF = 3
    }

    object LocationType {
        const val PROVINCE = "PROVINCE"
        const val DISTRICT = "DISTRICT"
        const val WARD = "WARD"
    }

    object ActiveType {
        const val QR_CODE = "QR_CODE"
        const val SERIAL = "SERIAL"
    }

    object ProductStatus {
        const val NOT_ACTIVATED = 0
        const val ACTIVATED = 1
    }
}
