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
 * @param [value] The location code [String] value. This can be either a [CountryCode] or [RegionCode].
 *
 * @throws [IllegalArgumentException] if the provided [value] is an invalid location code [String].
 *
 * @return The resulting [LocationCode].
 */
@Throws(IllegalArgumentException::class)
public operator fun LocationCode.Companion.invoke(value: String): LocationCode {
    require(value.isNotBlank()) {
        "Invalid location code value. Value was blank."
    }

    require(value.matches("^[A-Za-z0-9-]+$".toRegex())) {
        "Invalid location code value '${value}'. Only alphanumeric and hyphen characters are supported."
    }

    val separatorIndex = value.indexOf('-')

    return if (separatorIndex == -1) {
        require(value.length == 2) {
            "Invalid country code length '${value.length}'. Country codes must be in ISO 3166-1 Alpha-2 format. Meaning that there must only be two characters."
        }
        require(value.all { char -> char.isLetter() }) {
            "Invalid country code '${value}'. Country codes must be in ISO 3166-1 Alpha-2 format. Meaning that the character values must all be letters."
        }

        CountryCode(value = value.uppercase())
    } else {
        require(separatorIndex == 2) {
            "Invalid region code '${value}'. Region codes must be in ISO 3166-2 format. Meaning that it must start with a two character country code."
        }
        require(value.substring(0 until separatorIndex).all { char -> char.isLetter() }) {
            "Invalid country code '${value}'. Country codes must be in ISO 3166-1 Alpha-2 format. Meaning that the character values must all be letters."
        }

        RegionCode(value = value.uppercase())
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
