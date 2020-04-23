package ro.weekendrrsapps.sdk.utils

import java.util.*

object DateTimeUtils {

    const val DAY_IN_MILLIS = 3600 * 24 * 1000
    const val INVALID_TIMESTAMP = -1L
    const val HOURS_IN_A_DAY = 24

    fun getDateFromTimeStamp(timestamp: Long = System.currentTimeMillis()): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH)
        val dayStr = if (day < 10) "0$day" else day.toString()
        val monthStr = if (month + 1 < 10) "0${month + 1}" else month.toString()
        return "$dayStr-$monthStr-${cal.get(Calendar.YEAR)}"
    }

    fun getDaysFromDate(timestamp: Long): Int {
        val interval = System.currentTimeMillis() - timestamp
        if (interval < 0) return 0
        return (interval / DAY_IN_MILLIS).toInt()
    }

    fun convertDateToTimestamp(date: String): Long {
        val items = date.split("-")
        if (items.size == 3) {
            val day = items[0].toIntSafe()
            val month = items[1].toIntSafe()
            val year = items[2].toIntSafe()
            if (day > 0 && month > 0 && year > 0) {
                val cal = Calendar.getInstance()
                cal.set(year, month - 1 , day)
                return cal.timeInMillis
            }
        }
        return INVALID_TIMESTAMP
    }

    fun getCurrentHoursDifference(untilHour: Int): Int {
        val cal = Calendar.getInstance()
        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        return if (currentHour > untilHour) {
            HOURS_IN_A_DAY - (currentHour - untilHour)
        } else {
            untilHour - currentHour
        }
    }
}