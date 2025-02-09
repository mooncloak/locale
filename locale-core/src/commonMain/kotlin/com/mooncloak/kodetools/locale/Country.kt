package com.mooncloak.kodetools.locale

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a country within the world.
 *
 * @property [code] The [CountryCode] for this country.
 *
 * @property [name] The name of this country.
 *
 * @property [regionType] The default [Region.type] for this country.
 *
 * @property [flag] A URI pointing to the flag image for this country.
 *
 * @property [emojiFlag] An emoji representation of this country's flag.
 */
@Serializable
public data class Country public constructor(
    @SerialName(value = "code") public val code: CountryCode,
    @SerialName(value = "name") public val name: String? = null,
    @SerialName(value = "region_type") public val regionType: String? = null,
    @SerialName(value = "flag") public val flag: String? = null,
    @SerialName(value = "emoji") public val emojiFlag: String? = null
)
