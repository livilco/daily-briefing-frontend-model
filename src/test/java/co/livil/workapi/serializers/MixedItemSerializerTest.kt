package co.livil.workapi.serializers

import co.livil.workapi.model.Email
import co.livil.workapi.model.Event
import org.json.JSONObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MixedItemSerializerTest {
    private lateinit var email: Email
    private lateinit var event: Event

    private val emailJson: String = """
        {
          "relationships": {
            "mailboxes": {
              "data": []
            },
            "email_attachments": {
              "data": []
            }
          },
          "attributes": {
            "cc_recipients": [],
            "thread_id": "",
            "sender": {
              "address": "",
              "name": ""
            },
            "subject": "",
            "received_at": 0,
            "bcc_recipients": [],
            "flags": {
              "flagged": false,
              "urgent": false,
              "seen": false
            },
            "body": {
              "segments": []
            },
            "to_recipients": [],
            "labels": []
          },
          "id": "YWJjZGVmMTIzNDU2Nzg5OmFiY2RlZjEyMzQ1",
          "type": "email"
        }
    """.trimIndent()

    private val eventJson: String = """
        {
          "attributes": {
            "recurrence": "",
            "end_date_time": "",
            "end_timezone": "",
            "updated_at": "",
            "start_timezone": "",
            "name": "",
            "all_day": false,
            "description": "",
            "created_at": "",
            "location": "",
            "start_date_time": ""
          },
          "id": "OmZlZGNiYTU0MzIx",
          "type": "event"
        }
    """.trimIndent()
    @Before
    fun setup() {
        email = Email()
        email.setRemoteId("abcdef12345")
        email.setIntegrationId("987654321abcdef")

        event = Event()
        event.setRemoteId("fedcba54321")
        email.setIntegrationId("abcdef123456789")
    }

    @Test
    fun test_serializeMultipleMixedItems() {
        val expected = JSONObject("""{ "data": [$emailJson, $eventJson] }""").toString(2)
        val serialized = JSONObject(MixedItemSerializer().serializeItems(listOf(email, event))).toString(2)
        Assert.assertEquals(expected, serialized)
    }
}