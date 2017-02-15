package com.exallium.todoapp.app

import android.app.Application
import com.exallium.todoapp.BuildConfig
import com.exallium.todoapp.database.DatabaseModule
import com.exallium.todoapp.screenbundle.ScreenBundleModule
import timber.log.Timber

/**
 * TodoApp Application
 */
class TodoApp : Application() {

    companion object {
        lateinit var component: TodoAppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerTodoAppComponent.builder()
                .databaseModule(DatabaseModule())
                .screenBundleModule(ScreenBundleModule())
                .todoAppModule(TodoAppModule(this))
                .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}