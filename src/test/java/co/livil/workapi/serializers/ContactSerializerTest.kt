package co.livil.workapi.serializers

import co.livil.workapi.model.*
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ContactSerializerTest {
    private lateinit var contact: Contact
    private val jsonContact = """
        {
          "attributes": {
            "title": "Mr",
            "middle_name": "",
            "first_name": "Bill",
            "last_name": "Car",
            "email_addresses": [
              {
                "primary": false,
                "type": "personal",
                "address": "billcar@gmail.co.uk"
              }
            ],
            "phone_numbers": [
              {
                "primary": false,
                "type": "personal",
                "number": "0123456789"
              }
            ],
            "addresses": [
              {
                "primary": false,
                "type": "personal",
                "street": "10 King Road",
                "city": "Ipswich",
                "postal_code": "IP3 9AJ",
                "region": "Suffolk",
                "country": "United Kingdom"
              }
            ],
            "picture_url": "https://domain.com/picture.jpg",
            "updated_at": 1609996689,
            "created_at": 1609996689
          },
          "id": "OTg3NjU0MzIxYWJjZGVmOmFiY2RlZjEyMzQ1",
          "type": "contact"
        }
    """.trimIndent()

    @Before
    fun setup() {
        contact = Contact(
            title = "Mr",
            firstName = "Bill",
            lastName = "Car",
            emailAddresses = listOf(
                ContactEmailAddress(
                    type = "personal",
                    address = "billcar@gmail.co.uk"
                )
            ),
            phoneNumbers = listOf(
                ContactPhoneNumber(
                    type = "personal",
                    number = "0123456789"
                )
            ),
            addresses = listOf(
                ContactAddress(
                    type = "personal",
                    street = "10 King Road",
                    city = "Ipswich",
                    postalCode = "IP3 9AJ",
                    region = "Suffolk",
                    country = "United Kingdom"
                )
            ),
            pictureUrl = "https://domain.com/picture.jpg",
            updatedAt = 1609996689,
            createdAt = 1609996689
        )

        contact.setRemoteId("abcdef12345")
        contact.setIntegrationId("987654321abcdef")
    }

    @Test
    fun test_serializeSingleContact() {
        val expected = JSONObject("""{ "data": $jsonContact }""").toString(2)
        val serialized = JSONObject(ContactSerializer().serializeContact(contact)).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeSingleContact() {
        val input = JSONObject("""{ "data": $jsonContact }""").toString(2)
        val document = ContactSerializer().deserializeContact(input)
        val deserializedContact = document.get()
        assertEquals(contact, deserializedContact)
    }

    @Test
    fun test_serializeMultipleContacts() {
        val expected = JSONObject("""{ "data": [$jsonContact] }""").toString(2)
        val serialized = JSONObject(ContactSerializer().serializeContacts(listOf(contact))).toString(2)
        assertEquals(expected, serialized)
    }

    @Test
    fun test_deserializeMultipleContacts() {
        val input = JSONObject("""{ "data": [$jsonContact] }""").toString(2)
        val document = ContactSerializer().deserializeContacts(input)
        val firstDeserializedContact = document.get(0)
        assertEquals(contact, firstDeserializedContact)
    }
}