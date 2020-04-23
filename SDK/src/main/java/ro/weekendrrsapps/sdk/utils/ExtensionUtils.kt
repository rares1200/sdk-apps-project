package ro.weekendrrsapps.sdk.utils

import timber.log.Timber
import java.lang.NumberFormatException

fun String?.toIntSafe(fallback: Int = 0) = try {
        this?.toInt() ?: fallback
    } catch (ex: NumberFormatException) {
        Timber.i("Error converting string to number:${ex.message}")
        fallback
    }