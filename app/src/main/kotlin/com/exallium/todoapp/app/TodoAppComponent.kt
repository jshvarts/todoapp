package com.exallium.todoapp.app

import dagger.Component
import javax.inject.Singleton

/**
 * Application Component
 */
@Singleton
@Component(modules = arrayOf(TodoAppModule::class))
interface TodoAppComponent