package com.exallium.todoapp.app

import android.app.Application

/**
 * TodoApp Application
 */
class TodoApp : Application() {

    companion object {
        lateinit var component: TodoAppComponent
            private set
    }

    override fun onCreate() {
        component = DaggerTodoAppComponent.create()
    }

}