package com.exallium.todoapp.di

import com.exallium.todoapp.mvp.BaseController

/**
 * Base Component which injects an InjectionTarget
 */
interface BaseComponent<in B : BaseController<*, *, *, *>> {
    fun inject(controller: B)
}