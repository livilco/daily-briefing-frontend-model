package co.livil.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attendee (
    @Json(name = "profile_id") var profileId: String = "",
    @Json(name = "email_address") var emailAddress: String = "",
    @Json(name = "display_name") var displayName: String = "",
    @Json(name = "organizer") var organizer: Boolean = false,
    @Json(name = "self") var self: Boolean = false,
    @Json(name = "resource") var resource: Boolean = false,
    @Json(name = "optional") var optional: Boolean = false,
    @Json(name = "response_status") var responseStatus: String = "needsAction",
    @Json(name = "comment") var comment: String = "",
    @Json(name = "additional_guests") var additionalGuests: Int = 0
) {
    fun getLabel(): String {
        var output = if (displayName.isNotEmpty()) {
            "$displayName <$emailAddress>"
        } else {
            emailAddress
        }

        var flags = mutableListOf<String>()
        if (organizer) { flags.add("organizer") }

        return output + flags.joinToString(
            separator = ", ",
            prefix = if (flags.isNotEmpty()) " (" else "",
            postfix = if (flags.isNotEmpty()) ")" else ""
        )
    }
}