package co.livil.workapi.model

import co.livil.workapi.utils.DateHelper
import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@JsonApi(type = "free_timeslot")
data class FreeTimeslot(
    @field:Json(name = "start_time") var startTime: String = "",
    @field:Json(name = "end_time") var endTime: String = ""
): Resource(), ITimeslot {
    @Transient private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun getTimeRangeText(): String {
        if (startTime == DateHelper.startOfDayIso() && endTime == DateHelper.endOfDayIso()) {
            return "All day"
        }

        val startTimeOutput = timeFormatter.format(DateHelper.fromIsoDateString(startTime))
        val endTimeOutput = timeFormatter.format(DateHelper.fromIsoDateString(endTime))
        return "$startTimeOutput - $endTimeOutput"
    }

    override fun getLabelText(): String {
        return "[Free]"
    }

    override fun getSortValue(): String {
        return startTime
    }

    companion object {
        fun build(busyTimeslots: List<BusyTimeslot>, rangeStart: ZonedDateTime, rangeEnd: ZonedDateTime): List<FreeTimeslot> {
            val freeTimeslots = mutableListOf<FreeTimeslot>()
            var freeStartTime = DateHelper.toIsoDateString(rangeStart)

            busyTimeslots.forEach {
                if (freeStartTime != it.startTime) {
                    freeTimeslots.add(
                        FreeTimeslot(
                            startTime = freeStartTime,
                            endTime = it.startTime
                        )
                    )
                }

                freeStartTime = it.endTime
            }

            val busyEndTime = busyTimeslots.last().endTime
            val freeEndTime = DateHelper.toIsoDateString(rangeEnd)
            if (busyEndTime != freeEndTime) {
                freeTimeslots.add(
                    FreeTimeslot(
                        startTime = busyEndTime,
                        endTime = freeEndTime
                    )
                )
            }

            return freeTimeslots
        }
    }
}
