package com.exallium.todoapp.screenbundle

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for providing a Bundle factory.
 */
@Module
class BundleFactoryModule {
    @Provides
    @Singleton
    fun provideBundleFactory(): BundleFactory = BundleFactoryImpl()
}
