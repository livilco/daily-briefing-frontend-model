package co.livil.workapi.serializers

import co.livil.workapi.model.Event
import co.livil.workapi.serializers.EventSerializer
import moe.banana.jsonapi2.ArrayDocument
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EventSerializerTest {
    @Test
    fun test_serializeDocument() {
        val document = ArrayDocument<Event>()
        val events = listOf(Event(name = "Test Event"))
        document.addAll(events)
        val serialized = EventSerializer().serializeDocument(document)
        assertEquals("""{"data":[{"type":"event","id":"Og==","attributes":{"name":"Test Event","description":"","start_date_time":"","start_timezone":"","end_date_time":"","end_timezone":"","all_day":false,"recurrence":"","created_at":"","updated_at":"","remoteId":"","integrationId":""}}]}""", serialized)
    }
}