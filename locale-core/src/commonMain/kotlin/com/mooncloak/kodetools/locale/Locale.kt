package com.mooncloak.kodetools.locale

/**
 * A [Locale] represents a specific geographical, political, or cultural region. It is often used in application
 * development to provide a more localized user experience. For example, formatting a number to be displayed in an
 * application is considered to be a locale-sensitive operation, since different countries and regions format numbers
 * differently (Some countries may use commas as separators while others may use periods or spaces), as such, a
 * [Locale] could be used to determine how to format the numbers.
 *
 * The [Locale] interface implements IETF BCP-47 which is composed of RFC-4647 ("Matching of Language Tags") and
 * RFC-5646 ("Tags for Identifying Languages") with support for the LDML
 * (UTS#35, "Unicode Locale Data Markup Language") BCP-47-compatible extensions for locale data exchange.
 *
 * To obtain an instance of the [Locale] interface, use the constructor functions, such as the [Locale.Companion.parse]
 * function.
 *
 * Example usage:
 *
 * ```kotlin
 * Locale.getDefault().language
 * ```
 *
 * > [!Note]
 * > This project was inspired by the [locale](https://github.com/chRyNaN/locale) open source project, licensed under
 * > Apache 2.0. Which in turn, that project is heavily inspired by the open source JDK version of Locale.
 *
 * @see [Tags for Identifying Languages: RFC-5646](https://www.rfc-editor.org/rfc/rfc5646)
 * @see [Matching of language Tags: RFC-4647](https://www.rfc-editor.org/rfc/rfc4647.html)
 * @see [ISO-639-2](https://www.loc.gov/standards/iso639-2/)
 * @see [Wikipedia ISO-639](https://en.wikipedia.org/wiki/ISO_639)
 * @see [Language Subtag Registry](https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry)
 * @see [ISO-3166](https://www.iso.org/iso-3166-country-codes.html)
 * @see [Wikipedia ISO-3166](https://en.wikipedia.org/wiki/ISO_3166)
 * @see [Wikipedia ISO-15924](https://en.wikipedia.org/wiki/ISO_15924)
 * @see [iso-15924:2004](https://www.iso.org/standard/29546.html)
 */
@ExperimentalLocaleApi
public interface Locale {

    /**
     * Returns the language code of this Locale or `null` if it is not available.
     *
     * This property is an ISO-639 alpha-2 or alpha-3 language code, or registered language subtag up to 8 alpha
     * letters (for support for future enhancements). When a language has both an alpha-2 code and an alpha-3 code,
     * the alpha-2 code **must** be used.
     *
     * In the specification, the language field is case-insensitive, but [Locale] implementations should always convert
     * the [language] property to lower-case. Well-formed [language] values are defined by the following form:
     * `[a-zA-Z]{2,8}`
     *
     * **Note:** The term "BCP-47" refers to both the specification (RFC-5646) and the subtag registry.
     *
     * **Note:** this is not the full BCP-47 language production, since implementations may exclude support for the
     * `extlang` values.
     *
     * Example values:
     * ```
     * "en" - English
     * "ja" - Japanese
     * "kok" - Konkani
     * ```
     *
     * @see [ISO-639-2](https://www.loc.gov/standards/iso639-2/)
     * @see [Wikipedia ISO-639](https://en.wikipedia.org/wiki/ISO_639)
     * @see [Matching of language Tags: RFC-4647](https://www.rfc-editor.org/rfc/rfc4647.html)
     * @see [Tags for Identifying Languages: RFC-5646](https://www.rfc-editor.org/rfc/rfc5646)
     * @see [Language Subtag Registry](https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry)
     */
    public val language: String?

    /**
     * Returns the country/region code for this locale, which should either be an uppercase ISO-3166 2-letter code, a
     * UN M.49 3-digit code, or `null` if it is not available.
     *
     * This property is an ISO-3166 alpha-2 country code or UN M.49 numeric-3 area code. In the specification, the
     * country (region) field is case-insensitive, but [Locale] implementations should always convert
     * the [region] property to upper-case. Well-formed [region] values are defined by the following form:
     * `[a-zA-Z]{2} | [0-9]{3}`
     *
     * Example values:
     * ```
     * "US" - United States
     * "FR" - France
     * "029" - Caribbean
     * ```
     *
     * @see [ISO-3166](https://www.iso.org/iso-3166-country-codes.html)
     * @see [Wikipedia ISO-3166](https://en.wikipedia.org/wiki/ISO_3166)
     */
    public val region: String?

    /**
     * Returns the script for this locale, which should either be an ISO-15924 4-letter script code or `null` if it is
     * not available.
     *
     * This property is an ISO-15924 alpha-4 script code. In the specification, the script field is case-insensitive,
     * but [Locale] implementations should always convert the [script] property to title-case (the first letter is
     * upper case and the rest of the letters are lower case). Well-formed script values are defined by the following
     * form: `[a-zA-Z]{4}`
     *
     * Example values:
     * ```
     * "Latn" - Latin
     * "Cyrl" - Cyrillic
     * ```
     *
     * @see [Wikipedia ISO-15924](https://en.wikipedia.org/wiki/ISO_15924)
     * @see [iso-15924:2004](https://www.iso.org/standard/29546.html)
     * @see [ISO-639-2](https://www.loc.gov/standards/iso639-2/)
     * @see [Language Subtag Registry](https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry)
     */
    public val script: String?

    /**
     * Returns the variant codes for this locale.
     *
     * This property can contain any arbitrary [String] value used to indicate a variation of a [Locale]. In a locale
     * [String], if there are two or more variant values each indicating its own semantics, these values should be
     * ordered by importance, with the most important positioned first, and separated by an underscore ('_'). These
     * values are separated and returned as a [List] of [String]s from this [variants] property, where the [List.first]
     * value would be considered the most important value. If there are no variants, then an empty [List] will be
     * returned. The [variants] values are case-sensitive.
     *
     * **Note:** The term "BCP-47" refers to both the specification (RFC-5646) and the subtag registry.
     *
     * **Note:** IETF BCP-47 places syntactic restrictions on variant sub-tags. Also, BCP-47 sub-tags are strictly used
     * to indicate additional variations that define a language or its dialects that are not covered by any
     * combinations of [language], [script], and [region] sub-tags. However, the variant field in Locale has
     * historically been used for any kind of variation, not just language variations. For example, some supported
     * variants available in Java SE Runtime Environments indicate alternative cultural behaviors such as calendar type
     * or number script. In BCP 47 this kind of information, which does not identify the language, is supported by
     * extension sub-tags or private use sub-tags.
     *
     * Well-formed variant values are defined by the following form:
     * `SUBTAG (('_'|'-') SUBTAG)* where SUBTAG = [0-9][0-9a-zA-Z]{3} | [0-9a-zA-Z]{5,8}`
     * (Note: BCP-47 only uses hyphen ('-') as a delimiter, this is more lenient).
     *
     * Example values:
     * ```
     * "polyton" - Polytonic Greek
     * "POSIX"
     * ```
     *
     * @see [ISO-639-2](https://www.loc.gov/standards/iso639-2/)
     * @see [Wikipedia ISO-639](https://en.wikipedia.org/wiki/ISO_639)
     * @see [Matching of language Tags: RFC-4647](https://www.rfc-editor.org/rfc/rfc4647.html)
     * @see [Tags for Identifying Languages: RFC-5646](https://www.rfc-editor.org/rfc/rfc5646)
     * @see [Language Subtag Registry](https://www.iana.org/assignments/language-subtag-registry/language-subtag-registry)
     */
    public val variants: List<String>

    /**
     * Returns a well-formed IETF BCP-47 language tag representing this locale.
     */
    public val languageTag: LanguageCode

    /**
     * Retrieves a displayable [String] of this [Locale]'s [language] value formatted for the provided [inLocale].
     */
    public fun getDisplayLanguage(inLocale: Locale = Locale.getDefault()): String?

    /**
     * Retrieves a displayable [String] of this [Locale]'s [region] value formatted for the provided [inLocale].
     */
    public fun getDisplayRegion(inLocale: Locale = Locale.getDefault()): String?

    /**
     * Retrieves a displayable [String] of this [Locale]'s [script] value formatted for the provided [inLocale].
     */
    public fun getDisplayScript(inLocale: Locale = Locale.getDefault()): String?

    /**
     * Retrieves a displayable [String] of this [Locale]'s [variants] value formatted for the provided [inLocale].
     */
    public fun getDisplayVariant(inLocale: Locale = Locale.getDefault()): String?

    /**
     * Retrieves a displayable [String] of this [Locale]'s name ([languageTag]) formatted for the provided [inLocale].
     */
    public fun getDisplayName(inLocale: Locale = Locale.getDefault()): String?

    @ExperimentalLocaleApi
    public interface Parser {

        /**
         * Converts the provided [languageTag] into a [Locale] or throws an exception if it could not be converted into
         * a [Locale].
         *
         * **Note:** Some platform implementations may be more lenient that others and will try to create a [Locale]
         * removing any unsupported or unmatched values. This logic is up to the implementing class.
         */
        public fun parse(languageTag: String): Locale

        /**
         * Attempts to parse the provided [languageTag] into a [Locale] using the [parse] function, or `null` if it
         * cannot be converted into a [Locale].
         */
        public fun parseOrNull(languageTag: String): Locale? =
            try {
                parse(languageTag = languageTag)
            } catch (_: Exception) {
                null
            }

        /**
         * Retrieves a [Locale] from the provided parts or throws an exception if a [Locale] can't be created.
         */
        public fun fromParts(
            language: String? = null,
            region: String? = null,
            script: String? = null,
            variants: List<String> = emptyList()
        ): Locale

        /**
         * Retrieves a [Locale] from the provided parts or `null` if a [Locale] can't be created.
         */
        public fun fromPartsOrNull(
            language: String? = null,
            region: String? = null,
            script: String? = null,
            variants: List<String> = emptyList()
        ): Locale? =
            try {
                fromParts(
                    language = language,
                    region = region,
                    script = script,
                    variants = variants
                )
            } catch (_: Exception) {
                null
            }

        public companion object
    }

    @ExperimentalLocaleApi
    public interface Provider {

        /**
         * Retrieves the current default [Locale] for this device and platform. It is dependent on the implementation
         * on how this value is retrieved and may differ between implementations and platforms.
         */
        public fun getDefault(): Locale

        /**
         * Retrieves a [List] of the currently available [Locale]s.
         */
        public fun getAvailable(): List<Locale>

        public companion object
    }

    @ExperimentalLocaleApi
    public companion object : Parser by PlatformDefaultLocaleParser,
        Provider by PlatformDefaultLocaleProvider {

        /**
         * The separator for concatenating the [Locale.variants] to a [String]. This is equal to an underscore ('_')
         * character.
         */
        public const val VARIANT_SEPARATOR: String = "_"
    }
}

/**
 * Retrieves the [Locale.language] component.
 */
@ExperimentalLocaleApi
public operator fun Locale.component1(): String? = language

/**
 * Retrieves the [Locale.region] component.
 */
@ExperimentalLocaleApi
public operator fun Locale.component2(): String? = region

/**
 * Retrieves the [Locale.script] component.
 */
@ExperimentalLocaleApi
public operator fun Locale.component3(): String? = script

/**
 * Retrieves the [Locale.variants] component.
 */
@ExperimentalLocaleApi
public operator fun Locale.component4(): List<String> = variants

@ExperimentalLocaleApi
internal expect object PlatformDefaultLocaleParser : Locale.Parser

@ExperimentalLocaleApi
internal expect object PlatformDefaultLocaleProvider : Locale.Provider {

    val US: Locale
    val ENGLISH: Locale
    val EN_US: Locale
}
