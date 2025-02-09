package com.mooncloak.kodetools.locale.storage

import kotlin.reflect.KClass

public interface StorageProvider : AutoCloseable {

    public override fun close()

    public fun <Repository : LocationRepository> get(kClass: KClass<Repository>): Repository

    public companion object
}

public inline fun <reified Repository : LocationRepository> StorageProvider.get(): Repository =
    this.get(kClass = Repository::class)
