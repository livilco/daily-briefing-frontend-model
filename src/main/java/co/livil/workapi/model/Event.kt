package co.livil.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

/**
 * `@JsonApi` and `@field:Json` annotation allows (de)serialization with specific JSONAPI format
 *    for BACKEND communication. Can safely be ignored when working on frontend only
 *
 *  Sample JSON:
 *  {
 *    "data": {
 *      "type": "event",
 *      "id": "some-event-id-goes-here",
 *      "attributes": {
 *        "name": "Weekly Stand-up",
 *        "description": "All team members required to attend",
 *        "start_date_time": "2020-02-01T11:20:00Z",
 *        "start_timezone": "UTC",
 *        "end_date_time": "2020-02-01T11:40:00Z",
 *        "end_timezone": "UTC",
 *        "all_day": false,
 *        "recurrence": "RRULE:FREQ=WEEKLY",
 *        "created_at": "2020-01-01T14:30:00Z",
 *        "updated_at": "2020-01-01T14:30:00Z",
 *        "attendees": [
 *          { "display_name": "Davey Jones", "email_address": "davey@thelock.er", ... }
 *        ]
 *      }
 *  }
 */

@JsonApi(type = "event")
data class Event(
    @field:Json(name = "name") var name: String = "",
    @field:Json(name = "description") var description: String = "",
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