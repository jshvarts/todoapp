package com.exallium.todoapp.app

import android.content.res.Resources
import com.exallium.todoapp.MainActivity
import com.exallium.todoapp.repository.IdFactory
import com.exallium.todoapp.repository.Repository
import com.exallium.todoapp.screenbundle.BundleFactory
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

    fun repository(): Repository

    fun screenBundleHelper(): ScreenBundleHelper

    fun bundleFactory(): BundleFactory

    fun idFactory(): IdFactory

    fun inject(mainActivity: MainActivity)
}