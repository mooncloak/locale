package com.mooncloak.kodetools.locale.storage.sqlite

import com.mooncloak.kodetools.locale.storage.LocationRepository
import com.mooncloak.kodetools.locale.storage.StorageProvider
import kotlin.reflect.KClass

public class SqliteStorageProvider : StorageProvider {

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun <Repository : LocationRepository> get(kClass: KClass<Repository>): Repository {
        TODO("Not yet implemented")
    }
}
