package com.fabianofranca.randomuser.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

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