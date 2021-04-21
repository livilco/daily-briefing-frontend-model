package co.livil.workapi.serializers

import co.livil.workapi.model.Email
import co.livil.workapi.model.Event
import co.livil.workapi.utils.DateHelper
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument
import java.lang.NullPointerException

/**
 * @see EventSerializerTest for sample JSON
 */
class EventSerializer: BaseSerializer(typeClass = Event::class.java) {
    fun deserializeEvent(body: String): ObjectDocument<Event> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeEvents(body: String): ArrayDocument<Event> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeEvent(event: Event): String {
        event.sanitizeAllDayDateTimes()

        val document: ObjectDocument<Event> = ObjectDocument()
        document.set(event)
        return serializeDocument(document)
    }

    fun serializeEvents(events: List<Event>): String {
        events.forEach {
            it.sanitizeAllDayDateTimes()
        }

        val document: ArrayDocument<Event> = ArrayDocument()
        document.addAll(events)
        return serializeDocument(document)
    }
}