package com.exallium.todoapp.database

/**
 * Interface for generating unique ids
 */
interface IdFactory {
    fun createId(): String
}