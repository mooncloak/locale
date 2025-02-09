package com.mooncloak.kodetools.locale

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline

/**
 * Represents an [ISO 3166-1 Alpha 2](https://en.wikipedia.org/wiki/ISO_3166-1) country code value.
 *
 * @property [value] The actual [String] ISO 3166-1 Alpha 2 code [String] value.
 *
 * @see [ISO 3166-1 Alpha 2](https://en.wikipedia.org/wiki/ISO_3166-1)
 */
@JvmInline
@Serializable(with = CountryCodeSerializer::class)
public value class CountryCode public constructor(
    public override val value: String
) : LocationCode {

    override fun toCountryCode(): CountryCode = this
}

/**
 * Serializes a [CountryCode] as a [String].
 */
internal object CountryCodeSerializer : KSerializer<CountryCode> {

    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): CountryCode {
        val value = decoder.decodeString()

        return CountryCode(value = value)
    }

    override fun serialize(encoder: Encoder, value: CountryCode) {
        encoder.encodeString(value.value)
    }
}
