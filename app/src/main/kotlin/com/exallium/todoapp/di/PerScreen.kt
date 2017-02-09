package com.exallium.todoapp.di

import javax.inject.Scope

/**
 * Custom Scope for each Screen
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerScreen