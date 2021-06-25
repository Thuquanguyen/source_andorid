package checkvn.com.viettiepbhdt.utils

import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

class DateUtils {
    companion object {
        const val DATE_FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd"
        const val DATE_FORMAT_DAY_MONTH_YEAR = "dd-MM-yyyy"
        const val DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SSSSSS =
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
        const val DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd'T'HH:mm:ss"
    }
}

@Throws(ParseException::class)
fun getDateFromString(
    dateString: String,
    dateFormat: String
): Date {
    return SimpleDateFormat(dateFormat, Locale.getDefault()).parse(dateString) ?: Date()
}

@Throws(ParseException::class)
fun formatDate(
    date: String,
    initDateFormat: String,
    endDateFormat: String
): String {
    return try {
        val initDate = SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date) ?: Date()
        val formatter = SimpleDateFormat(endDateFormat, Locale.getDefault())
        formatter.format(initDate)
    } catch (e: Exception) {
        ""
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun getTotalDay(startDate: String, endDate: String): Int {
    val warrantyEndDate = getDateFromString(endDate, DateUtils.DATE_FORMAT_YEAR_MONTH_DAY)
    val warrantyStartDate = getDateFromString(startDate, DateUtils.DATE_FORMAT_YEAR_MONTH_DAY)
    val totalDayTime = warrantyEndDate.time - warrantyStartDate.time
    return TimeUnit.DAYS.convert(abs(totalDayTime), TimeUnit.MILLISECONDS).toInt()
}
