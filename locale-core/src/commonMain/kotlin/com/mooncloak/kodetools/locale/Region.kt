package com.mooncloak.kodetools.locale

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a region within a [Country].
 *
 * @property [code] The [RegionCode] of this region.
 *
 * @property [name] The name of this region.
 *
 * @property [type] The type of region within the [Country] (ex: State, City, etc.).
 *
 * @property [flag] A URI pointing to the flag image for this region.
 *
 * @property [emojiFlag] An emoji representation of this country's flag.
 */
@Serializable
public data class Region public constructor(
    @SerialName(value = "code") public override val code: RegionCode,
    @SerialName(value = "name") public override val name: String? = null,
    @SerialName(value = "type") public val type: String? = null,
    @SerialName(value = "flag") public val flag: String? = null,
    @SerialName(value = "emoji") public val emojiFlag: String? = null
) : Location
