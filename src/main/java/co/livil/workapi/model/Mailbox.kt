package co.livil.workapi.model

import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "mailbox")
data class Mailbox (
    @field:Json(name = "name") var name: String = "",

    // Note: not using an enum due to serialization headaches
    @field:Json(name = "mailbox_type") var mailboxType: String = INBOX,

    @field:Json(name = "total_count") var totalCount: Int = 0,
    @field:Json(name = "unread_count") var unreadCount: Int = 0
) : WorkApiResource() {
    companion object {
        // Possible values for mailboxType
        const val INBOX = "inbox"
        const val SENT = "sent"
        const val OUTBOX = "outbox"
        const val DRAFTS = "drafts"
        const val OTHER = "other"
    }
}
