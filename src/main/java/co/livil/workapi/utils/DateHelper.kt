package co.livil.workapi.utils

import org.ocpsoft.prettytime.PrettyTime
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

class DateHelper {
    companion object {
        fun startOfDayIso(): String {
            return startOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        fun endOfDayIso(): String {
            return endOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        fun oneWeekAgoIso(): String {
           return oneWeekAgo().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        fun endOfDay(): LocalDateTime {
            val finalMinute: LocalTime = LocalTime.of(23, 59, 59)
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return LocalDateTime.of(today, finalMinute)
        }

        fun startOfDay(): LocalDateTime {
            val midnight: LocalTime = LocalTime.MIDNIGHT
            val today: LocalDate = LocalDate.now(ZoneId.of(ZoneId.systemDefault().toString()))
            return LocalDateTime.of(today, midnight)
        }

        fun oneWeekAgo(): LocalDateTime {
            return startOfDay().minusDays(7)
        }

        fun fromLong(epochMs: Long): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMs), ZoneId.systemDefault())
        }

        fun fromIsoDateString(dateString: String): LocalDateTime {
            val date = RfcDateTimeParser.parseDateString(dateString)
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())
        }

        fun toIsoDateString(localDateTime: LocalDateTime): String {
            return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        fun prettyDate(datetime: LocalDateTime, reference: Date? = null): String {
            val out = Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
            return PrettyTime(reference).format(out)
        }

        fun prettyDate(datetimeLong: Long, reference: Date? = null): String {
            val datetime = fromLong(datetimeLong)
            return prettyDate(datetime, reference)
        }
    }
}