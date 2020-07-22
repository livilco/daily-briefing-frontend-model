package co.livil.workapi.serializers

import co.livil.workapi.model.Email
import co.livil.workapi.serializers.EmailSerializer
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EmailSerializerTest {
    @Test
    fun test_serializeEmails() {
        val emails = listOf(Email(subject = "test"))
        val serialized = EmailSerializer().serializeEmails(emails)
        println(serialized)
    }
}