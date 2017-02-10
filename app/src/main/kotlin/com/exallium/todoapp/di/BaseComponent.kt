package com.exallium.todoapp.di

import com.exallium.todoapp.mvp.BaseViewImpl

/**
 * Base Component which injects an InjectionTarget
 */
interface BaseComponent<in B : BaseViewImpl<*, *, *, *>> {
    fun inject(controller: B)
}