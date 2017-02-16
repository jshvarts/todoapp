package com.exallium.todoapp.repository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module for Repository Dependencies
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideIdFactory(): IdFactory {
        return IdFactoryImpl()
    }

    @Provides
    @Singleton
    fun provideRepository(idFactory: IdFactory): Repository {
        return StubRepositoryImpl(idFactory)
    }
}