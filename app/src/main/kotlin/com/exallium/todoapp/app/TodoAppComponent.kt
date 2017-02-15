package com.exallium.todoapp.app

import android.content.res.Resources
import com.exallium.todoapp.MainActivity
import com.exallium.todoapp.database.Database
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import dagger.Component
import javax.inject.Singleton

/**
 * Application Component
 */
@Singleton
@Component(modules = arrayOf(TodoAppModule::class))
interface TodoAppComponent {
    fun resources(): Resources

    fun database(): Database

    fun screenBundleHelper(): ScreenBundleHelper

    fun inject(mainActivity: MainActivity)
}