package com.exallium.todoapp.screenbundle

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for providing a ScreenBundle factory
 */
@Module
class ScreenBundleModule {
    @Provides
    @Singleton
    fun provideScreenBundleHelper(resources: Resources): ScreenBundleHelper
        = ScreenBundleHelperImpl(resources)
}