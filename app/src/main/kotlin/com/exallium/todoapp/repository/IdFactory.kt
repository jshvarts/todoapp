package com.exallium.todoapp.repository

/**
 * Interface for generating unique ids
 */
interface IdFactory {
    fun createId(): String
}