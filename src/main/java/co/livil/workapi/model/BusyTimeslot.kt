package co.livil.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.HasMany
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource

@JsonApi(type = "busy_timeslot")
data class BusyTimeslot(
    @field:Json(name = "calendar_id") var calendarId: String = "",
    @field:Json(name = "start_time") var startTime: String = "",
    @field:Json(name = "end_time") var endTime: String = "",
    @field:Json(name = "events") var events: HasMany<Event>? = HasMany(),
) : Resource()