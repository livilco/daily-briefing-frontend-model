package co.livil.workapi.serializers

import co.livil.workapi.model.*
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IntegrationSerializerTest {

    private lateinit var integration: Integration
    private val jsonIntegration = """
        {
                  "attributes": {
                    "default": false,
                    "provider": "gmail",
                    "media_type": "email",
                    "context": "",
                    "label": ""
                  },
                  "id": "123456-abcdef",
                  "type": "integration"
                }
    """.trimIndent()

    private val incomingIntegration = Integration(
        authExpiresAt = 123456789,
        mediaType = "email",
        provider = "gmail"
    )

    private val incomingJsonIntegration = """
        {
                  "attributes": {
                    "default": false,
                    "provider": "gmail",
                    "media_type": "email",
                    "context": "",
                    "label": "",
                    "auth_expires_at": 123456789
                  },
                  "id": "123456-abcdef",
                  "type": "integration"
                }
    """.trimIndent()

    @Before
    fun setup() {
        integration = Integration(
            provider = "gmail",
            mediaType = "email"
        )

        integration.id = "123456-abcdef"
    }

    @Test
    fun test_serializeSingleIntegration() {
        val expected = JSONObject("""{ "data": $jsonIntegration }""").toString(2)
        val serialized = JSONObject(IntegrationSerializer().serializeIntegration(integration)).toString(2)
        assertEquals(expected, serialized)
    }

     @Test
     fun test_deserializeSingleIntegration() {
         val input = JSONObject("""{ "data": $incomingJsonIntegration }""").toString(2)
         val document = IntegrationSerializer().deserializeIntegration(input)
         val deserializedIntegration = document.get()
         assertEquals(incomingIntegration, deserializedIntegration)
     }
//
//     @Test
//     fun test_serializeMultipleMailboxes() {
//         val expected = JSONObject("""{ "data": [$jsonMailbox] }""").toString(2)
//         val serialized = JSONObject(MailboxSerializer().serializeMailboxes(listOf(mailbox))).toString(2)
//         assertEquals(expected, serialized)
//     }
//
//     @Test
//     fun test_deserializeMultipleMailboxes() {
//         val input = JSONObject("""{ "data": [$jsonMailbox] }""").toString(2)
//         val document = MailboxSerializer().deserializeMailboxes(input)
//         val firstDeserializedMailbox = document.get(0)
//         assertEquals(mailbox, firstDeserializedMailbox)
//     }
}