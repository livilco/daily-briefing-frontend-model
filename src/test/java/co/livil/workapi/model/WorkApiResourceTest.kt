package co.livil.workapi.model

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.*

@RunWith(RobolectricTestRunner::class)
class WorkApiResourceTest {
    @Test
    fun test_idSetters() {
        val expectedId = "aW50ZWdyYXRpb24taWQ6cmVtb3RlLWlk"
        val integrationId = "integration-id"
        val remoteId = "remote-id"
        val email = Email()

        email.setIntegrationId(integrationId)
        email.setRemoteId(remoteId)

        assertEquals(expectedId, email.id)

        val decodedId = String(Base64.getUrlDecoder().decode(email.id)).split(":")
        assertEquals(integrationId, decodedId[0])
        assertEquals(remoteId, decodedId[1])
    }

    @Test
    fun test_idGetters() {
        val expectedId = "aW50ZWdyYXRpb24taWQ6cmVtb3RlLWlk"
        val integrationId = "integration-id"
        val remoteId = "remote-id"

        val email = Email()
        email.id = expectedId

        assertEquals(integrationId, email.getIntegrationId())
        assertEquals(remoteId, email.getRemoteId())
    }
}