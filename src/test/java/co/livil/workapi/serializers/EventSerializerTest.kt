package co.livil.workapi.serializers

import co.livil.workapi.model.Attendee
import co.livil.workapi.model.Event
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EventSerializerTest {
    private val event: Event = Event(
        name = "Test Event Name",
        description = "Test Event Description",
        location = "London",
        startDateTime = "2020-07-27T10:00:00Z",
        startTimezone = "UTC",
        endDateTime = "2020-07-27T11:00:00Z",
        endTimezone = "UTC",
        allDay = true,
        recurrence = "RRULE:FREQ=WEEKLY;",
        createdAt = "2020-07-27T05:00:00Z",
        updatedAt = "2020-07-27T05:00:00Z",
        attendees = listOf(
            Attendee(
                displayName = "Test Attendee",
                emailAddress = "test@livil.co"
            )
        )
    )
    private val jsonEvent = """
        {
          "attributes": {
            "recurrence": "RRULE:FREQ=WEEKLY;",
            "end_date_time": "2020-07-27T11:00:00Z",
            "end_timezone": "UTC",
            "updated_at": "2020-07-27T05:00:00Z",
            "start_timezone": "UTC",
            "attendees": [
              {
                "additionalGuests": 0,
                "email_address": "test@livil.co",
                "response_status": "",
                "resource": false,
                "profile_id": "",
                "organizer": true,
                "self": true,
                "optional": false,
                "comment": "",
                "display_name": "Test Attendee"
              }
            ],
            "name": "Test Event Name",
            "all_day": true,
            "description": "Test Event Description",
            "created_at": "2020-07-27T05:00:00Z",
            "location": "London",
            "start_date_time": "2020-07-27T10:00:00Z"
          },
          "id": "Og==",
          "type": "event"
        }""".trimIndent()

    // TODO("handle attachments")

    @Test
    fun test_serializeSingleEvent() {
        val expected = JSONObject("""{ "data": $jsonEvent }""").toString(2)
        val serialized = JSONObject(EventSerializer().serializeEvent(event)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeSingleEvent() {
        val input = JSONObject("""{ "data": $jsonEvent }""").toString(2)
        val document = EventSerializer().deserializeEvent(input)
        val deserializedEvent = document.get()
        assertEquals(event, deserializedEvent)
    }

    @Test
    fun test_serializeMultipleEvents() {
        val expected = JSONObject("""{ "data": [$jsonEvent] }""").toString(2)
        val serialized = JSONObject(EventSerializer().serializeEvents(listOf(event))).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeMultipleEmails() {
        val input = JSONObject("""{ "data": [$jsonEvent] }""").toString(2)
        val document = EventSerializer().deserializeEvents(input)
        val firstDeserializedEmail = document.get(0)
        assertEquals(event, firstDeserializedEmail)
    }
}