package com.exallium.todoapp.repository

import java.util.*

/**
 * Concrete Implementation for generation of IDs
 */
class IdFactoryImpl : IdFactory {
    override fun createId() = UUID.randomUUID().toString()
}