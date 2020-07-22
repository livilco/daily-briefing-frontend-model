package co.livil.workapi.serializers

import co.livil.workapi.model.Email
import co.livil.workapi.model.EmailAttachment
import co.livil.workapi.model.Mailbox
import moe.banana.jsonapi2.*

class EmailSerializer: BaseSerializer(typeClass = Email::class.java) {
    fun deserializeEmail(body: String): ObjectDocument<Email> {
        return adapter().fromJson(body)!!.asObjectDocument()
    }

    fun deserializeEmails(body: String): ArrayDocument<Email> {
        return adapter().fromJson(body)!!.asArrayDocument()
    }

    fun serializeEmails(emails: List<Email>): String {
        val document: ArrayDocument<Email> = ArrayDocument()
        document.addAll(emails)
        return adapter().toJson(document)
    }

    override fun adapterFactory(): ResourceAdapterFactory.Builder {
        return super
            .adapterFactory()
            .add(Mailbox::class.java)
            .add(EmailAttachment::class.java)
    }
}