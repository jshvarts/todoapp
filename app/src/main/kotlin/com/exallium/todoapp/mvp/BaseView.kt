package com.exallium.todoapp.mvp

import rx.Observable

/**
 * Base View for MVP Pattern
 */
interface BaseView {

    enum class LifecycleEvent {
        CREATE_VIEW,
        ACTIVITY_START,
        ACTIVITY_RESUME,
        ACTIVITY_PAUSE,
        ACTIVITY_STOP,
        DESTROY_VIEW
    }

    fun lifecycleEvents(): Observable<LifecycleEvent>
}