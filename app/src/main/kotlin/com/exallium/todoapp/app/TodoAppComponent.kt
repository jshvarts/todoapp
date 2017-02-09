package com.exallium.todoapp.app

import com.exallium.todoapp.database.Database
import dagger.Component
import javax.inject.Singleton

/**
 * Application Component
 */
@Singleton
@Component(modules = arrayOf(TodoAppModule::class))
interface TodoAppComponent {
    fun database(): Database
}