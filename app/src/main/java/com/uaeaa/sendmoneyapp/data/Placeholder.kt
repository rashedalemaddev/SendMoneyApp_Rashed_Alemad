package com.uaeaa.sendmoneyapp.data

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
import kotlinx.serialization.json.jsonPrimitive

@kotlinx.serialization.Serializable(with = PlaceholderSerializer::class)
sealed class Placeholder {
    @kotlinx.serialization.Serializable
    data class Text(val value: String) : Placeholder()

    @Serializable
    data class Localized(val map: Map<String, String>) : Placeholder()
}

object PlaceholderSerializer : KSerializer<Placeholder> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("Placeholder")

    override fun serialize(encoder: Encoder, value: Placeholder) {
        when (value) {
            is Placeholder.Text -> encoder.encodeString(value.value)
            is Placeholder.Localized -> encoder.encodeSerializableValue(
                MapSerializer(String.serializer(), String.serializer()), value.map
            )
        }
    }

    override fun deserialize(decoder: Decoder): Placeholder {
        val input = decoder as? JsonDecoder
            ?: throw SerializationException("This class can be loaded only by JSON")
        val element = input.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> Placeholder.Text(element.content)
            is JsonObject -> {
                val map = element.mapValues { it.value.jsonPrimitive.content }
                Placeholder.Localized(map)
            }
            else -> throw SerializationException("Unexpected JSON for placeholder: $element")
        }
    }
}
