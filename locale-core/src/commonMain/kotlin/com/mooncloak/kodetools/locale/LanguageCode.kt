package com.mooncloak.kodetools.locale

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Represents an IETF BCP 47 language code.
 *
 * @property [value] The actual [String] IETF BCP 47 language code value.
 *
 * @see [IETF BCP 47 language tags](https://en.wikipedia.org/wiki/IETF_language_tag)
 */
@JvmInline
@Serializable
public value class LanguageCode public constructor(
    @Suppress("MemberVisibilityCanBePrivate") public val value: String
)
