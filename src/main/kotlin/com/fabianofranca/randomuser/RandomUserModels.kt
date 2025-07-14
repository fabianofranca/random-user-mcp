package com.fabianofranca.randomuser

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class RandomUserResponse(
    val results: List<UserResult>,
    val info: Info
)

@Serializable
data class UserResult(
    val gender: String,
    val name: UserName,
    val location: UserLocation,
    val email: String,
    val login: UserLogin,
    val dob: UserDob,
    val registered: UserRegistered,
    val phone: String,
    val cell: String,
    val id: UserId,
    val picture: UserPicture,
    val nat: String
)

@Serializable
data class UserName(
    val title: String,
    val first: String,
    val last: String
)

object PostcodeSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Postcode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String?) {
        encoder.encodeString(value ?: "")
    }

    override fun deserialize(decoder: Decoder): String? {
        if (decoder !is JsonDecoder) return decoder.decodeString()

        val element = decoder.decodeJsonElement()
        if (element !is JsonPrimitive) return null

        return when {
            element.jsonPrimitive.isString -> element.jsonPrimitive.content
            else -> element.toString()
        }
    }
}

@Serializable
data class UserLocation(
    val street: UserStreet,
    val city: String,
    val state: String,
    val country: String,
    @Serializable(with = PostcodeSerializer::class)
    val postcode: String?,
    val coordinates: UserCoordinates,
    val timezone: UserTimezone
)

@Serializable
data class UserStreet(
    val number: Int,
    val name: String
)

@Serializable
data class UserCoordinates(
    val latitude: String,
    val longitude: String
)

@Serializable
data class UserTimezone(
    val offset: String,
    val description: String
)

@Serializable
data class UserLogin(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

@Serializable
data class UserDob(
    val date: String,
    val age: Int
)

@Serializable
data class UserRegistered(
    val date: String,
    val age: Int
)

@Serializable
data class UserId(
    val name: String,
    val value: String?
)

@Serializable
data class UserPicture(
    val large: String,
    val medium: String,
    val thumbnail: String
)

@Serializable
data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)
