package com.exallium.todoapp.repository

import com.exallium.todoapp.entities.Note
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
    fun provideDataMapper(idFactory: IdFactory): DataMapper<Note> {
        return StubNoteMapperImpl(idFactory)
    }

    @Provides
    fun provideQueryMapper(dataMapper: DataMapper<Note>): QueryMapper<Note> {
        return dataMapper as StubNoteMapperImpl
    }

    @Provides
    @Singleton
    fun provideRepository(noteDataMapper: DataMapper<Note>,
                          noteQueryMapper: QueryMapper<Note>): Repository {
        return RepositoryImpl(noteDataMapper, noteQueryMapper)
    }
}