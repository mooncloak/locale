package com.mooncloak.kodetools.locale

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleParser : Locale.Parser {

    actual override fun parse(languageTag: String): Locale {
        TODO("Not yet implemented")
    }

    actual override fun fromParts(language: String?, region: String?, script: String?, variants: List<String>): Locale {
        TODO("Not yet implemented")
    }
}
