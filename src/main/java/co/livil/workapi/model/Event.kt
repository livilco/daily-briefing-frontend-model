package co.livil.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

/**
 * @see EventSerializerTest for sample JSON
 */
@JsonApi(type = "event")
data class Event(
    @field:Json(name = "name") var name: String = "",
    @field:Json(name = "description") var description: String = "",
    @field:Json(name = "location") var location: String = "",
    @field:Json(name = "start_date_time") var startDateTime: String = "",
    @field:Json(name = "start_timezone") var startTimezone: String = "",
    @field:Json(name = "end_date_time") var endDateTime: String = "",
    @field:Json(name = "end_timezone") var endTimezone: String = "",
    @field:Json(name = "all_day") var allDay: Boolean = false,
    @field:Json(name = "recurrence") var recurrence: String = "",
    @field:Json(name = "created_at") val createdAt: String = "",
    @field:Json(name = "updated_at") val updatedAt: String = "",
    @field:Json(name = "attendees") var attendees: List<Attendee>? = null
) : WorkApiResource()