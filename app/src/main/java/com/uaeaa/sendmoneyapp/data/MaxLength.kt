import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = MaxLengthSerializer::class)
sealed class MaxLength {
    data class IntLength(val value: Int) : MaxLength()
    data class StringLength(val value: String) : MaxLength()
}

object MaxLengthSerializer : KSerializer<MaxLength> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MaxLength", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: MaxLength) {
        when (value) {
            is MaxLength.IntLength -> encoder.encodeInt(value.value)
            is MaxLength.StringLength -> encoder.encodeString(value.value)
            else -> {}
        }
    }

    override fun deserialize(decoder: Decoder): MaxLength {
        return try {
            MaxLength.IntLength(decoder.decodeInt())
        } catch (e: Exception) {
            MaxLength.StringLength(decoder.decodeString())
        }
    }
}
