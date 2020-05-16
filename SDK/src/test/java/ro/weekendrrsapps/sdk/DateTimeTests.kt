package ro.weekendrrsapps.sdk

import org.junit.Assert
import org.junit.Test
import ro.weekendrrsapps.sdk.utils.DateTimeUtils
import java.util.*

class DateTimeTests {

    @Test
    fun datetime_test001_getDateFromTimestamp() {
        val time = 1589648809000L
        val expectedDate = "16-05-2020"
        val actualDate = DateTimeUtils.getDateFromTimeStamp(time)
        Assert.assertEquals(expectedDate, actualDate)
    }

    @Test
    fun datetime_test002_getDaysFromDate() {
        val expectedDays = 5
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_MONTH, -expectedDays)
        val actualDays = DateTimeUtils.getDaysFromDate(cal.timeInMillis)
        Assert.assertEquals(expectedDays, actualDays)
    }

}