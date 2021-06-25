package checkvn.com.viettiepbhdt.utils.extensions


fun String.isValidPassword(): Boolean {
    /*val regex =
        """^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[~`%^*,./?';:@#${'$'}%!\-_?&])(?=\S+${'$'}).{6,}""".toRegex()
    return regex.matches(this)*/
    return this.length >= 6
}

fun String.isValidPhoneNumber(): Boolean {
    val regex = """^(\+?[84])?[-.\s]?\(?[0-9]\d{2,3}\)?[-.\s]?\d{3}[-.\s]?\d{4}""".toRegex()
    return regex.matches(this)
}

fun getNotBlankValue(value: String?): String {
    return if (value == null || value.isBlank())
        "Chưa cập nhật"
    else value
}

fun getInfoValue(value: String?): String {
    return if (value == null || value.isBlank())
        "N/A"
    else value
}