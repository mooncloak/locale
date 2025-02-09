package com.mooncloak.kodetools.locale

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleProvider : Locale.Provider {

    override fun getDefault(): Locale {
        TODO("Not yet implemented")
    }

    override fun getAvailable(): List<Locale> {
        TODO("Not yet implemented")
    }

    actual val US: Locale
        get() = TODO("Not yet implemented")
    actual val ENGLISH: Locale
        get() = TODO("Not yet implemented")
    actual val EN_US: Locale
        get() = TODO("Not yet implemented")
}
