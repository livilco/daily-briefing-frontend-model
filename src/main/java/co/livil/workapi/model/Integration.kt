package co.livil.workapi.model

import co.livil.workapi.utils.DateHelper
import com.squareup.moshi.Json
import moe.banana.jsonapi2.JsonApi
import moe.banana.jsonapi2.Resource
import java.time.ZoneOffset


@JsonApi(type = "integration")
data class InternalIntegration (
    @field:Json(name = "provider") var provider: String = "",
    @field:Json(name = "provider_id") var providerId: String? = null,
    @field:Json(name = "media_type") var mediaType: String = ""
) : Resource()

@JsonApi(type = "integration")
data class Integration (
    @field:Json(name = "provider") var provider: String = "",
    @field:Json(name = "provider_id") var providerId: String? = null,
    @field:Json(name = "media_type") var mediaType: String = "",
    @field:Json(name = "auth_expires_at") var authExpiresAt: Int? = null,
    @field:Json(name = "default") var default: Boolean = false,
    @field:Json(name = "label") var label: String = "",
    @field:Json(name = "context") var context: String = ""
) : Resource() {
    val isExpired: Boolean
        get() {
            if (authExpiresAt == null || authExpiresAt == 0) return false

            return authExpiresAt!! < System.currentTimeMillis() / 1000
        }

    fun toInternal(): InternalIntegration {
        return InternalIntegration(
            provider = provider,
            providerId = providerId,
            mediaType = mediaType
        )
    }
}
