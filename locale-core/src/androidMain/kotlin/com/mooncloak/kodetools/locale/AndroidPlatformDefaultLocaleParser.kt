package com.mooncloak.kodetools.locale

import com.mooncloak.kodetools.locale.Locale.Companion.VARIANT_SEPARATOR

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleParser : Locale.Parser {

    actual override fun parse(languageTag: String): Locale =
        JvmLocale(java.util.Locale.forLanguageTag(languageTag))

    actual override fun fromParts(language: String?, region: String?, script: String?, variants: List<String>): Locale =
        JvmLocale(
            java.util.Locale.Builder()
                .setLanguage(language)
                .setRegion(region)
                .setScript(script)
                .setVariant(variants.joinToString(separator = VARIANT_SEPARATOR))
                .build()
        )
}
