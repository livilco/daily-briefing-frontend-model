package co.livil.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipient (
    @Json(name = "name") var name: String = "",
    @Json(name = "address") var address: String = ""
) {
    companion object {
        fun fromString(str: String): Recipient {
            return if (str.matches(".* <.*@.*>".toRegex())) {
                val segments = str.split("<", ">").filter { it.isNotEmpty() }
                Recipient(name = segments[0].trim(), address = segments[1].trim())
            } else {
                Recipient(address = str.trim())
            }
        }
    }
}