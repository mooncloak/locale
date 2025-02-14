package com.mooncloak.kodetools.locale

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Represents an [ISO 3166-1 Alpha 2](https://en.wikipedia.org/wiki/ISO_3166-1) country code value or an
 * [ISO 3166-2](https://en.wikipedia.org/wiki/ISO_3166-2) region (ex: US State) code value.
 *
 * @property [value] The actual [String] ISO 3166-1 Alpha 2 code [String] value.
 *
 * @see [CountryCode] For a [LocationCode] that represents a [Country].
 * @see [RegionCode] For a [LocationCode] that represents a subdivision or [Region]
 */
@Serializable(with = LocationCodeSerializer::class)
public sealed interface LocationCode {

    /**
     * The actual ISO 3166-1 Alpha 2 or ISO 3166-2 location code value.
     */
    public val value: String

    /**
     * Converts this [LocationCode] into a [CountryCode]. A [CountryCode] is the first two characters in ISO 3166-2
     * ([RegionCode]), followed by a hyphen character ('-').
     *
     * > [!Note]
     * > A [CountryCode] is itself a [CountryCode], so this function will just return itself. Whereas, a [RegionCode]
     * > can be converted into a [CountryCode] by taking the first characters before the hyphen in its code value.
     */
    public fun toCountryCode(): CountryCode
}

/**
 * Retrieves a [LocationCode] instance wrapping the provided location code [value].
 *
 * > [!Note]
 * > This is not a validating function. This means if an invalid code value is provided to this function it will return
 * > an invalid [LocationCode].
 *
 * @param [value] The location code [String] value. This can be either a [CountryCode] or [RegionCode].
 *
 * @return The resulting [LocationCode].
 */
public operator fun LocationCode.Companion.invoke(value: String): LocationCode {
    val hyphenIndex = value.indexOf('-')

    return if (hyphenIndex == -1) {
        CountryCode(value = value)
    } else {
        RegionCode(value = value)
    }
}

/**
 * Serializes a [LocationCode] as a [String].
 */
internal object LocationCodeSerializer : KSerializer<LocationCode> {

    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun deserialize(decoder: Decoder): LocationCode {
        val value = decoder.decodeString()

        return LocationCode(value = value)
    }

    override fun serialize(encoder: Encoder, value: LocationCode) {
        encoder.encodeString(value.value)
    }
}
