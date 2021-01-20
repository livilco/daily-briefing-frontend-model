package co.livil.workapi.serializers

import co.livil.workapi.model.BusyTimeslot
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class BusyTimeslotSerializer : BaseSerializer(typeClass = BusyTimeslot::class.java) {
    fun serializeBusyTimeslot(busyTimeslot: BusyTimeslot): String {
        val document: ObjectDocument<BusyTimeslot> = ObjectDocument()
        document.set(busyTimeslot)
        return adapter().toJson(document)
    }

    fun deserializeBusyTimeslot(body: String): ObjectDocument<BusyTimeslot> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun serializeBusyTimeslots(busyTimeslots: List<BusyTimeslot>): String {
        val document: ArrayDocument<BusyTimeslot> = ArrayDocument()
        document.addAll(busyTimeslots)
        return serializeDocument(document)
    }

    fun deserializeBusyTimeslots(body: String): ArrayDocument<BusyTimeslot> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }
}