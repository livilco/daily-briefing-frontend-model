package co.livil.workapi.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDateTime

@RunWith(RobolectricTestRunner::class)
class EventTest {
    @Test
    fun test_occurrences() {
        val event = Event(
            startDateTime = "2020-07-01T10:00:00Z",
            endDateTime = "2020-07-01T12:00:00Z",
            recurrence = "RRULE:FREQ=WEEKLY;WKST=SU;BYDAY=WE,TH,FR"
        )

        val occurrences = event.occurrences(
            from = LocalDateTime.of(2020, 7, 1, 0, 0),
            until = LocalDateTime.of(2020, 7, 8, 23, 59)
        )

        assertEquals(4, occurrences.size)

        val firstOccurrence = occurrences[0]
        assertEquals("2020-07-01T00:00:00", firstOccurrence.startDateTime)
        assertEquals("2020-07-01T02:00:00", firstOccurrence.endDateTime)

        val secondOccurrence = occurrences[1]
        assertEquals("2020-07-02T00:00:00", secondOccurrence.startDateTime)
        assertEquals("2020-07-02T02:00:00", secondOccurrence.endDateTime)

        val thirdOccurrence = occurrences[2]
        assertEquals("2020-07-03T00:00:00", thirdOccurrence.startDateTime)
        assertEquals("2020-07-03T02:00:00", thirdOccurrence.endDateTime)

        val fourthOccurrence = occurrences[3]
        assertEquals("2020-07-08T00:00:00", fourthOccurrence.startDateTime)
        assertEquals("2020-07-08T02:00:00", fourthOccurrence.endDateTime)
    }

    @Test
    fun test_getRRULE() {
        val event = Event(recurrence = "RRULE:FREQ=WEEKLY;WKST=SU;BYDAY=WE,TH,FR")

        val rrule = event.getRRULE()
        assertNotNull(rrule)
        assertEquals("FREQ=WEEKLY;WKST=SU;BYDAY=WE,TH,FR", rrule.toString())
    }
}