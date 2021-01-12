package co.livil.workapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import moe.banana.jsonapi2.JsonApi

@JsonApi(type = "contact")
data class Contact (
    @field:Json(name = "title") var title: String = "",
    @field:Json(name = "first_name") var firstName: String = "",
    @field:Json(name = "middle_name") var middleName: String = "",
    @field:Json(name = "last_name") var lastName: String = "",
    @field:Json(name = "email_addresses") var emailAddresses: List<ContactEmailAddress> = emptyList(),
    @field:Json(name = "phone_numbers") var phoneNumbers: List<ContactPhoneNumber> = emptyList(),
    @field:Json(name = "addresses") var addresses: List<ContactAddress> = emptyList(),
    @field:Json(name = "picture_url") var pictureUrl: String = "",
    @field:Json(name = "created_at") var createdAt: Int = 0,
    @field:Json(name = "updated_at") var updatedAt: Int = 0
) : WorkApiResource()


@JsonClass(generateAdapter = true)
data class ContactEmailAddress(
    @Json(name = "primary") var primary: Boolean = false,
    @Json(name = "type") var type: String = "",
    @Json(name = "address") var address: String = ""
)

data class ContactPhoneNumber(
    @Json(name = "primary") var primary: Boolean = false,
    @Json(name = "type") var type: String = "",
    @Json(name = "number") var number: String = ""
)
data class ContactAddress(
    @Json(name = "primary") var primary: Boolean = false,
    @Json(name = "type") var type: String = "",
    @Json(name = "street") var street: String = "",
    @Json(name = "city") var city: String = "",
    @Json(name = "postal_code") var postalCode: String = "",
    @Json(name = "region") var region: String = "",
    @Json(name = "country") var country: String = ""
)
