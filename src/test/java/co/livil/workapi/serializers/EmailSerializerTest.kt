package co.livil.workapi.serializers

import co.livil.workapi.model.*
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EmailSerializerTest {
    private lateinit var email: Email
    private val jsonEmail = """
        {
                  "relationships": {
                    "mailboxes": {
                      "data": [
                        {
                          "id": "YWJjMTIzOmluYm94",
                          "type": "mailbox"
                        }
                      ]
                    },
                    "email_attachments": {
                      "data": []
                    }
                  },
                  "attributes": {
                    "cc_recipients": [
                      {
                        "address": "ccRecipient1@livil.co",
                        "name": "CC Recipient 1"
                      },
                      {
                        "address": "ccRecipient2@livil.co",
                        "name": "CC Recipient 2"
                      }
                    ],
                    "thread_id": "abc123",
                    "sender": {
                      "address": "sender@livil.co",
                      "name": "Sender Name"
                    },
                    "subject": "Test Subject",
                    "received_at": 1595826402,
                    "bcc_recipients": [
                      {
                        "address": "bccRecipient1@livil.co",
                        "name": "BCC Recipient 1"
                      },
                      {
                        "address": "bccRecipient2@livil.co",
                        "name": "BCC Recipient 2"
                      }
                    ],
                    "flags": {
                      "flagged": true,
                      "seen": true,
                      "urgent": false 
                    },
                    "body": {
                      "segments": [
                        "Body part 1",
                        "Body part 2"
                      ],
                      "plain_text": [
                        "Body part 1",
                        "Body part 2"
                      ],
                      "html": [
                        "<blink>Body part 1<\/blink>",
                        "<blink>Body part 2<\/blink>"
                      ]
                    },
                    "to_recipients": [
                      {
                        "address": "recipient1@livil.co",
                        "name": "Recipient 1"
                      },
                      {
                        "address": "recipient2@livil.co",
                        "name": "Recipient 2"
                      }
                    ],
                    "labels": [
                      "INBOX",
                      "IMPORTANT"
                    ]
                  },
                  "id": "OTg3NjU0MzIxYWJjZGVmOmFiY2RlZjEyMzQ1",
                  "type": "email"
                }
    """.trimIndent()

    @Before
    fun setup() {
        email = Email(
            threadId = "abc123",
            subject = "Test Subject",
            sender = Recipient(
                name = "Sender Name",
                address = "sender@livil.co"
            ),
            toRecipients = mutableListOf(
                Recipient(
                    name = "Recipient 1",
                    address = "recipient1@livil.co"
                ),
                Recipient(
                    name = "Recipient 2",
                    address = "recipient2@livil.co"
                )
            ),
            ccRecipients = mutableListOf(
                Recipient(
                    name = "CC Recipient 1",
                    address = "ccRecipient1@livil.co"
                ),
                Recipient(
                    name = "CC Recipient 2",
                    address = "ccRecipient2@livil.co"
                )
            ),
            bccRecipients = mutableListOf(
                Recipient(
                    name = "BCC Recipient 1",
                    address = "bccRecipient1@livil.co"
                ),
                Recipient(
                    name = "BCC Recipient 2",
                    address = "bccRecipient2@livil.co"
                )
            ),
            flags = Flags(
                seen = true,
                flagged = true
            ),
            body = EmailBody(
                plainText = mutableListOf(
                    "Body part 1",
                    "Body part 2"
                ),
                html = mutableListOf(
                    "<blink>Body part 1</blink>",
                    "<blink>Body part 2</blink>"
                )
            ),
            labels = mutableListOf(
                "INBOX",
                "IMPORTANT"
            ),
            receivedAt = 1595826402
        )

        email.processBodyContent()
        email.setRemoteId("abcdef12345")
        email.setIntegrationId("987654321abcdef")

        val mailbox = Mailbox(name = "Inbox", mailboxType = "inbox")
        mailbox.setRemoteId("inbox")
        mailbox.setIntegrationId("abc123")
        email.mailboxes?.add(mailbox)
    }

    // TODO("handle attachments")

    @Test
    fun test_serializeSingleEmail() {
        val expected = JSONObject("""{ "data": $jsonEmail }""").toString(2)
        val serialized = JSONObject(EmailSerializer().serializeEmail(email)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeSingleEmail() {
        val input = JSONObject("""{ "data": $jsonEmail }""").toString(2)
        val document = EmailSerializer().deserializeEmail(input)
        val deserializedEmail = document.get()
        assertEquals(email, deserializedEmail)
    }

    @Test
    fun test_serializeMultipleEmails() {
        val expected = JSONObject("""{ "data": [$jsonEmail] }""").toString(2)
        val serialized = JSONObject(EmailSerializer().serializeEmails(listOf(email))).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeMultipleEmails() {
        val input = JSONObject("""{ "data": [$jsonEmail] }""").toString(2)
        val document = EmailSerializer().deserializeEmails(input)
        val firstDeserializedEmail = document.get(0)
        assertEquals(email, firstDeserializedEmail)
    }
}