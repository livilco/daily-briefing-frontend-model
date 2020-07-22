package co.livil.workapi.model

import android.text.Html
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import moe.banana.jsonapi2.HasMany
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "email")
data class Email(
    @field:Json(name = "thread_id") var threadId: String = "",
    @field:Json(name = "subject") var subject: String = "",
    @field:Json(name = "sender") var sender: Recipient? = null,
    @field:Json(name = "to_recipients") var toRecipients: List<Recipient>? = null,
    @field:Json(name = "cc_recipients") var ccRecipients: List<Recipient>? = null,
    @field:Json(name = "bcc_recipients") var bccRecipients: List<Recipient>? = null,
    @field:Json(name = "flags") var flags: Flags? = null,
    @field:Json(name = "body") var body: EmailBody? = null,
    @field:Json(name = "labels") var labels: List<String>? = null,
    @field:Json(name = "received_at") var receivedAt: Int = 0,

    var mailboxes: HasMany<Mailbox>? = null,
    var emailAttachments: HasMany<EmailAttachment>? = null

) : WorkApiResource() {
    fun processBodyContent() {
        if (hasPlaintextContent()) {
            body!!.segments = splitContent(body!!.plainText!!)
        } else if (hasHtmlContent()) {
            strippedHtmlContent()?.let {
                body!!.segments = splitContent(it)
            }
        }
    }

    private fun hasPlaintextContent(): Boolean {
        val plainTextEmpty= body?.plainText?.isNotEmpty()
        return plainTextEmpty ?: false
    }

    private fun hasHtmlContent(): Boolean {
        val htmlEmpty= body?.html?.isNotEmpty()
        return htmlEmpty ?: false
    }

    private fun strippedHtmlContent(): List<String>? {
        return body?.html?.map {
            return@map Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT).toString()
        }
    }

    private fun splitContent(content: List<String>): List<String> {
        val processed = mutableListOf<String>()
        content.forEach {
            it.split("\r\n\r\n", "\n\n").forEach { str -> processed.add(str) }
        }

        return processed.toList()
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
     */
    @Json(name = "segments") var segments: List<String>? = null,

    /**
     * `plainText` and `html` correspond to the blocks of text present in the original email
     *
     * Can be safely ignored
     */
    @Json(name = "plain_text") var plainText: List<String>? = null,
    @Json(name = "html") var html: List<String>? = null
)