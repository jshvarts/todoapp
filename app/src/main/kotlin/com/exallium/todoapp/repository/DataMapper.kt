package com.exallium.todoapp.repository

import rx.Single

/**
 *  DataMapper Interface for Persisting and Removing Data
 *
 *  We do not utilize in/out variance here in order to keep Dagger happy
 */
interface DataMapper<T> {
    fun save(entity: T)
    fun remove(id: String)
}