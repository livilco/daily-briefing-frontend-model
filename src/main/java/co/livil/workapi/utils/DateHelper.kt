package co.livil.workapi.utils

import android.util.Log
import co.livil.workapi.utils.RfcDateTimeParser
import org.ocpsoft.prettytime.PrettyTime
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class DateHelper {
    companion object {
        fun nowIso(): String {
            return now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun startOfDayIso(): String {
            return startOfDayZoned().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun endOfDayIso(): String {
            return endOfDayZoned().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun oneWeekAgoIso(): String {
           return oneWeekAgo().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun now(): LocalDateTime {
            return LocalDateTime.now(ZoneId.of(ZoneId.systemDefault().toString()))
        }

        fun endOfDay(): LocalDateTime {
            val finalMinute: LocalTime = LocalTime.of(23, 59, 59)
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return LocalDateTime.of(today, finalMinute)
        }

        fun endOfDayZoned(): ZonedDateTime {
            val finalMinute: LocalTime = LocalTime.of(23, 59, 59)
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return ZonedDateTime.of(
                today,
                finalMinute,
                ZoneId.systemDefault()
            )
        }

        fun startOfDay(): LocalDateTime {
            val midnight: LocalTime = LocalTime.MIDNIGHT
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return LocalDateTime.of(today, midnight)
        }

        fun startOfDayZoned(): ZonedDateTime {
            val midnight: LocalTime = LocalTime.MIDNIGHT
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return ZonedDateTime.of(
                today,
                midnight,
                ZoneId.systemDefault()
            )
        }


        fun oneWeekAgo(): LocalDateTime {
            return startOfDay().minusDays(7)
        }

        fun fromLong(epochMs: Long): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMs), ZoneId.systemDefault())
        }

        fun fromIsoDateString(dateString: String): LocalDateTime {
            val date = RfcDateTimeParser.parseDateString(dateString)
            try {
                return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
            } catch (exception: Exception) {
                Log.e(TAG, "Could not parse date string: $dateString")
                throw exception
            }
        }

        fun toIsoDateString(localDateTime: LocalDateTime): String {
            val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
            return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun toIsoDateString(zonedDateTime: ZonedDateTime): String {
            return zonedDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        }

        fun prettyDate(datetime: LocalDateTime, reference: Date? = null): String {
            val out = Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
            return PrettyTime(reference).format(out)
        }

        fun prettyDate(datetimeLong: Long, reference: Date? = null): String {
            val datetime = fromLong(datetimeLong)
            return prettyDate(datetime, reference)
        }

        fun formatFriendlyDateTime(datetimeStr: String, pattern: String) : String {
            return try {
                val datetime = DateHelper.fromIsoDateString(datetimeStr)
                val formatter = DateTimeFormatter.ofPattern(pattern)
                datetime.format(formatter)
            } catch (e: NullPointerException) {
                Log.e("DateHepler#formatDateTime", datetimeStr.toString())
                datetimeStr
            }
        }

        const val TAG = "DateHelper"
    }
}