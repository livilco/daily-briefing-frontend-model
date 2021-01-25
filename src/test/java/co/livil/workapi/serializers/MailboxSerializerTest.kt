package co.livil.workapi.serializers

import co.livil.workapi.model.*
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MailboxSerializerTest {
    private lateinit var mailbox: Mailbox
    private val jsonMailbox = """
        {
                  "attributes": {
                    "name": "Inbox",
                    "mailbox_type": "inbox",
                    "unread_count": 0,
                    "total_count": 0
                  },
                  "id": "YWJjMTIzOmluYm94",
                  "type": "mailbox"
                }
    """.trimIndent()

    @Before
    fun setup() {
        mailbox = Mailbox(
            name = "Inbox",
            mailboxType = "inbox"
        )

        mailbox.setRemoteId("inbox")
        mailbox.setIntegrationId("abc123")
    }

    @Test
    fun test_serializeSingleMailbox() {
        val expected = JSONObject("""{ "data": $jsonMailbox }""").toString(2)
        val serialized = JSONObject(MailboxSerializer().serializeMailbox(mailbox)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeSingleMailbox() {
        val input = JSONObject("""{ "data": $jsonMailbox }""").toString(2)
        val document = MailboxSerializer().deserializeMailbox(input)
        val deserializedMailbox = document.get()
        assertEquals(mailbox, deserializedMailbox)
    }

    @Test
    fun test_serializeMultipleMailboxes() {
        val expected = JSONObject("""{ "data": [$jsonMailbox] }""").toString(2)
        val serialized = JSONObject(MailboxSerializer().serializeMailboxes(listOf(mailbox))).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeMultipleMailboxes() {
        val input = JSONObject("""{ "data": [$jsonMailbox] }""").toString(2)
        val document = MailboxSerializer().deserializeMailboxes(input)
        val firstDeserializedMailbox = document.get(0)
        assertEquals(mailbox, firstDeserializedMailbox)
    }
}