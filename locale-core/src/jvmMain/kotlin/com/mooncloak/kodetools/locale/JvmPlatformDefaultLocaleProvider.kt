package com.mooncloak.kodetools.locale

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleProvider : Locale.Provider {

    actual override fun getDefault(): Locale =
        JvmLocale(java.util.Locale.getDefault())

    actual override fun getAvailable(): List<Locale> =
        java.util.Locale.getAvailableLocales().map { JvmLocale(it) }

    actual val US: Locale
        get() = JvmLocale(jvmLocale = java.util.Locale.US)

    actual val ENGLISH: Locale
        get() = JvmLocale(jvmLocale = java.util.Locale.ENGLISH)

    actual val EN_US: Locale
        get() = JvmLocale(jvmLocale = java.util.Locale.forLanguageTag("en_US"))
}
