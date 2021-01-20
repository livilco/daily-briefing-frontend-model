package co.livil.workapi.serializers

import co.livil.workapi.model.Mailbox
import moe.banana.jsonapi2.ArrayDocument
import moe.banana.jsonapi2.ObjectDocument

class MailboxSerializer: BaseSerializer(typeClass = Mailbox::class.java) {
    fun deserializeMailbox(body: String): ObjectDocument<Mailbox> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeMailboxes(body: String): ArrayDocument<Mailbox> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeMailbox(mailbox: Mailbox): String {
        val document: ObjectDocument<Mailbox> = ObjectDocument()
        document.set(mailbox)
        return serializeDocument(document)
    }

    fun serializeMailboxes(mailboxes: List<Mailbox>): String {
        val document: ArrayDocument<Mailbox> = ArrayDocument()
        document.addAll(mailboxes)
        return serializeDocument(document)
    }
}