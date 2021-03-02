package co.livil.workapi.model

import co.livil.workapi.utils.DateHelper
import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasMany
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

@JsonApi(type = "busy_timeslot")
data class BusyTimeslot(
    @field:Json(name = "calendar_id") var calendarId: String = "",
    @field:Json(name = "start_time") var startTime: String = "",
    @field:Json(name = "end_time") var endTime: String = "",
    @field:Json(name = "events") var events: HasMany<Event>? = HasMany(),
) : Resource(), ITimeslot {
    @Transient private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    override fun getTimeRangeText(): String {
        val startTimeOutput = timeFormatter.format(DateHelper.fromIsoDateString(startTime))
        val endTimeOutput = timeFormatter.format(DateHelper.fromIsoDateString(endTime))
        return "$startTimeOutput - $endTimeOutput"
    }

    override fun getLabelText(): String {
        return if (events != null && events!!.size() > 0) {
            var eventsById = getEventsById()
            events!!.map { eventsById[it.id] }.joinToString(", ") { it?.name ?: "Unknown" }
        } else {
            "Unknown"
        }
    }

    override fun getSortValue(): String {
        return startTime
    }

    private fun getEventsById() : Map<String, Event> {
        val mapped = mutableMapOf<String, Event>()
        getIncludedEvents().forEach { mapped[it.id] = it }
        return mapped
    }

    fun getIncludedEvents() : List<Event> {
        if (events == null) return listOf()

        return events!!.get(document) as List<Event>
    }
}