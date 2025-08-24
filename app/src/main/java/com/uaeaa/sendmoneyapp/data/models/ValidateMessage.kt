package com.uaeaa.sendmoneyapp.data.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = ValidationMessageSerializer::class)
sealed class ValidationMessage {
    data class Text(val value: String) : ValidationMessage()
    @Serializable
    data class Localized(val map: Map<String, String>) : ValidationMessage()
}

object ValidationMessageSerializer : KSerializer<ValidationMessage> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ValidationMessage")

    override fun serialize(encoder: Encoder, value: ValidationMessage) {
        when (value) {
            is ValidationMessage.Text -> encoder.encodeString(value.value)
            is ValidationMessage.Localized -> encoder.encodeSerializableValue(
                MapSerializer(String.serializer(), String.serializer()), value.map
            )
        }
    }

    override fun deserialize(decoder: Decoder): ValidationMessage {
        val input = decoder as? JsonDecoder ?: throw SerializationException("Only JSON supported")
        val element = input.decodeJsonElement()

        return when (element) {
            is JsonPrimitive -> ValidationMessage.Text(element.content)
            is JsonObject -> {
                val map = element.mapNotNull { (key, value) ->
                    val content = value.jsonPrimitive.contentOrNull
                    if (content != null) key to content else null
                }.toMap()
                ValidationMessage.Localized(map)
            }
            else -> throw SerializationException("Unexpected JSON for validation_error_message: $element")
        }
    }

}
