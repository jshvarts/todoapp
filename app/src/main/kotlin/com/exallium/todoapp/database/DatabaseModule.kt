package com.exallium.todoapp.database

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module for Database Dependencies
 */
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideIdFactory(): IdFactory {
        return IdFactoryImpl()
    }

    @Provides
    @Singleton
    fun provideDatabase(idFactory: IdFactory): Database {
        return StubDatabaseImpl(idFactory)
    }
}