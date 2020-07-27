package co.livil.workapi.model

import android.text.Html
import co.livil.workapi.R
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import moe.banana.jsonapi2.HasMany
import moe.banana.jsonapi2.JsonApi

/**
 * @see EmailSerializerTest for sample JSON
 */
@JsonApi(type = "email")
data class Email(
    @field:Json(name = "thread_id") var threadId: String? = "",
    @field:Json(name = "subject") var subject: String = "",
    @field:Json(name = "sender") var sender: Recipient = Recipient(),
    @field:Json(name = "to_recipients") var toRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "cc_recipients") var ccRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "bcc_recipients") var bccRecipients: MutableList<Recipient> = mutableListOf(),
    @field:Json(name = "flags") var flags: Flags = Flags(),
    @field:Json(name = "body") var body: EmailBody = EmailBody(),
    @field:Json(name = "labels") var labels: MutableList<String> = mutableListOf(),
    @field:Json(name = "received_at") var receivedAt: Long = 0,

    @field:Json(name = "mailboxes") var mailboxes: HasMany<Mailbox>? = HasMany(),
    @field:Json(name = "email_attachments") var emailAttachments: HasMany<EmailAttachment>? = HasMany()
) : WorkApiResource() {
    fun processBodyContent() {
        if (hasPlaintextContent()) {
            body.segments.add(subject)
            body.segments.add(sender.name)

            splitContent(body.plainText!!)?.let { body.segments.addAll(it) }
        } else if (hasHtmlContent()) {
            strippedHtmlContent()?.let {
                splitContent(it)?.let { it1 -> body.segments.addAll(it1) }
            }
        }
    }

    private fun hasPlaintextContent(): Boolean {
        val plainTextEmpty= body.plainText?.isNotEmpty()
        return plainTextEmpty ?: false
    }

    private fun hasHtmlContent(): Boolean {
        val htmlEmpty= body.html?.isNotEmpty()
        return htmlEmpty ?: false
    }

    private fun strippedHtmlContent(): List<String>? {
        return body.html?.map {
            return@map Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT).toString()
        }
    }

    private fun splitContent(content: List<String>): MutableList<String>? {
        val processed = mutableListOf<String>()
        content.forEach {
            it.split("\r\n\r\n", "\n\n").forEach { str -> processed.add(str) }
        }

        return processed.toMutableList()
    }
}

@JsonClass(generateAdapter = true)
data class Flags(
    @Json(name = "seen") var seen: Boolean = false,
    @Json(name = "flagged") var flagged: Boolean = false
)

@JsonClass(generateAdapter = true)
data class EmailBody(
    /**
     * `segments` is the list of readable strings derived from `plainText` and/or `html`
     *
     * TODO: Trigger auto generation of segments on (de)serialization
     */
    @Json(name = "segments") var segments: MutableList<String> = mutableListOf(),

    /**
     * `plainText` and `html` correspond to the blocks of text present in the original email
     *
     * Can be safely ignored
     */
    @Json(name = "plain_text") var plainText: MutableList<String>? = null,
    @Json(name = "html") var html: MutableList<String>? = null
)