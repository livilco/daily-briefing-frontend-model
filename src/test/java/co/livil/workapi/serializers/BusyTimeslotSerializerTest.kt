package co.livil.workapi.serializers

import co.livil.workapi.model.BusyTimeslot
import co.livil.workapi.model.Event
import moe.banana.jsonapi2.HasMany
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BusyTimeslotSerializerTest {
    private val busyTimeslot = BusyTimeslot(
        startTime = "2021-05-01T12:00:00Z",
        endTime = "2021-05-01T14:00:00Z"
    )
    private val busyTimeslotWithEvent: BusyTimeslot
        get() {
            val event = Event()
            event.setRemoteId("test-event")
            event.setIntegrationId("test-integration")

            val timeslot = BusyTimeslot(
                startTime = "2021-05-01T12:00:00Z",
                endTime = "2021-05-01T14:00:00Z",
            )
            timeslot.events = HasMany<Event>(event)

            return timeslot
        }
    private val jsonBusyTimeslot = """
        {
          "attributes": {
            "start_time": "2021-05-01T12:00:00Z",
            "calendar_id": "",
            "end_time": "2021-05-01T14:00:00Z"
          },
          "relationships": {
            "events": {
              "data": []
            }
          },
          "type": "busy_timeslot"
        }""".trimIndent()

    private val jsonBusyTimeslotWithEvent = """
        {
          "attributes": {
            "start_time": "2021-05-01T12:00:00Z",
            "calendar_id": "",
            "end_time": "2021-05-01T14:00:00Z"
          },
          "relationships": {
            "events": {
              "data": [
                { "id": "dGVzdC1pbnRlZ3JhdGlvbjp0ZXN0LWV2ZW50", "type": "event" }
              ]
            }
          },
          "type": "busy_timeslot"
        }""".trimIndent()

    @Test
    fun test_serializeSingleBusyTimeslot() {
        val expected = JSONObject("""{ "data": $jsonBusyTimeslot }""").toString(2)
        val serialized = JSONObject(BusyTimeslotSerializer().serializeBusyTimeslot(busyTimeslot)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_serializeSingleBusyTimeslotWithEvent() {
        val expected = JSONObject("""{ "data": $jsonBusyTimeslotWithEvent }""").toString(2)
        val serialized = JSONObject(BusyTimeslotSerializer().serializeBusyTimeslot(busyTimeslotWithEvent)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeSingleBusyTimeslot() {
        val input = JSONObject("""{ "data": $jsonBusyTimeslot }""").toString(2)
        val document = BusyTimeslotSerializer().deserializeBusyTimeslot(input)
        val deserializedBusyTimeslot = document.get()
        assertEquals(busyTimeslot, deserializedBusyTimeslot)
    }

    @Test
    fun test_deserializeSingleBusyTimeslotWithEvent() {
        val input = JSONObject("""{ "data": $jsonBusyTimeslotWithEvent }""").toString(2)
        val document = BusyTimeslotSerializer().deserializeBusyTimeslot(input)
        val deserializedBusyTimeslot = document.get()
        assertEquals(busyTimeslotWithEvent, deserializedBusyTimeslot)
    }

    @Test
    fun test_serializeMultipleBusyTimeslots() {
        val expected = JSONObject("""{ "data": [$jsonBusyTimeslot] }""").toString(2)
        val serialized = JSONObject(BusyTimeslotSerializer().serializeBusyTimeslots(listOf(busyTimeslot))).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeMultipleBusyTimeslots() {
        val input = JSONObject("""{ "data": [$jsonBusyTimeslot] }""").toString(2)
        val document = BusyTimeslotSerializer().deserializeBusyTimeslots(input)
        val firstDeserializedBusyTimeslot = document.get(0)
        assertEquals(busyTimeslot, firstDeserializedBusyTimeslot)
    }
}