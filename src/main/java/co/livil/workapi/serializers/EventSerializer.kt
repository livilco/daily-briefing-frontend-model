package co.livil.workapi.serializers

import co.livil.workapi.model.Event
import moe.banana.jsonapi2.*

class EventSerializer: BaseSerializer(typeClass = Event::class.java) {
    fun deserializeEvent(body: String): ObjectDocument<Event> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeEvents(body: String): ArrayDocument<Event> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}