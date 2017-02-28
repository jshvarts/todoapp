package com.exallium.todoapp.app

import android.content.res.Resources
import com.exallium.todoapp.repository.RepositoryModule
import com.exallium.todoapp.screenbundle.BundleFactoryModule
import com.exallium.todoapp.screenbundle.ScreenBundleModule
import dagger.Module
import dagger.Provides

/**
 * Todo Application Dagger Module
 */
@Module(includes = arrayOf(RepositoryModule::class, ScreenBundleModule::class, BundleFactoryModule::class))
class TodoAppModule(private val todoApp: TodoApp) {
    @Provides
    fun provideResources(): Resources = todoApp.resources
}
