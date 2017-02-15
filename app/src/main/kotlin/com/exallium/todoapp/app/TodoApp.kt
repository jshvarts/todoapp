package com.exallium.todoapp.app

import android.app.Application
import com.exallium.todoapp.BuildConfig
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
        component = DaggerTodoAppComponent.create()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}