package co.livil.workapi.serializers

import co.livil.workapi.model.Contact
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class ContactSerializer : BaseSerializer(typeClass = Contact::class.java){
    fun deserializeContact(body: String?): ObjectDocument<Contact> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeContacts(body: String?): ArrayDocument<Contact> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeContact(contact: Contact): String {
        val document: ObjectDocument<Contact> = ObjectDocument()
        document.set(contact)
        return serializeDocument(document)
    }

    fun serializeContacts(contacts: List<Contact>): String {
        val document: ArrayDocument<Contact> = ArrayDocument()
        document.addAll(contacts)
        return serializeDocument(document)
    }
}