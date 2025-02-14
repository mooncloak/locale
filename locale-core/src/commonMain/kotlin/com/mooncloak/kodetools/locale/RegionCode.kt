package com.mooncloak.kodetools.locale

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline

/**
 * Represents an [ISO 3166-2](https://en.wikipedia.org/wiki/ISO_3166-2) subdivision, or region, (ex: US State) code
 * value.
 *
 * @property [value] The actual [String] ISO 3166-2 code [String] value.
 *
 * @see [ISO 3166-2 A](https://en.wikipedia.org/wiki/ISO_3166-2)
 */
@JvmInline
@Serializable(with = RegionCodeSerializer::class)
public value class RegionCode internal constructor(
    public override val value: String
) : LocationCode {

    override fun toCountryCode(): CountryCode {
        val index = this.value.indexOf('-')

        val value = if (index == -1) {
            this.value
        } else {
            this.value.substring(startIndex = 0, endIndex = index)
        }

        return CountryCode(value = value)
    }
}

/**
 * Constructs an instance of [RegionCode] with the provided [value].
 *
 * @param [value] The ISO 3166-2 A [String] value.
 *
 * @throws [IllegalArgumentException] if the provided [value] is not a valid ISO 3155-2 A code value.
 *
 * @return [RegionCode]
 */
@Throws(IllegalArgumentException::class)
public operator fun RegionCode.Companion.invoke(value: String): RegionCode {
    val code = LocationCode(value)

    if (code !is RegionCode) {
        throw IllegalArgumentException("Provided location code '$value' was not a valid region code.")
    }

    return code
}

/**
 * Serializes a [RegionCode] as a [String].
 */
internal object RegionCodeSerializer : KSerializer<RegionCode> {

    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): RegionCode {
        val value = decoder.decodeString()

        return RegionCode(value = value)
    }

    override fun serialize(encoder: Encoder, value: RegionCode) {
        encoder.encodeString(value.value)
    }
}
