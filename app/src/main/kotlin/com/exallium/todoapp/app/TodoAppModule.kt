package com.exallium.todoapp.app

import com.exallium.todoapp.database.DatabaseModule
import dagger.Module

/**
 * Todo Application Dagger Module
 */
@Module(includes = arrayOf(DatabaseModule::class))
class TodoAppModule