package com.exallium.todoapp.mvp

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Abstract Presenter with common functionality.
 * The Presenter is considered "alive" when it is created, but "dead" as soon as
 * the underlying view is destroyed.
 */
abstract class BasePresenter<out V : BaseView>(private val view: V) {

    // All subscriptions should be written to this unless they're to be explicitly handled otherwise
    protected val compositeSubscription = CompositeSubscription()

    init {
        compositeSubscription.add(view.lifecycleEvents().subscribe({
            routeLifecycleEvent(it)
        }))
    }

    /**
     * Called when underlying view is created
     */
    open fun onViewCreated() {}

    /**
     * Called when activity is started
     */
    open fun onActivityStarted() {}

    /**
     * Called when activity is resumed
     */
    open fun onActivityResumed() {}

    /**
     * Called when activity is paused
     */
    open fun onActivityPaused() {}

    /**
     * Called when activity stops
     */
    open fun onActivityStopped() {}

    /**
     * Called when underlying view is destroyed
     */
    open fun onViewDestroyed() {}

    private fun routeLifecycleEvent(event: BaseView.LifecycleEvent) {
        when (event) {
            BaseView.LifecycleEvent.CREATE_VIEW -> onViewCreated()
            BaseView.LifecycleEvent.ACTIVITY_START -> onActivityStarted()
            BaseView.LifecycleEvent.ACTIVITY_RESUME -> onActivityResumed()
            BaseView.LifecycleEvent.ACTIVITY_PAUSE -> onActivityPaused()
            BaseView.LifecycleEvent.ACTIVITY_STOP -> onActivityStopped()
            BaseView.LifecycleEvent.DESTROY_VIEW -> {
                onViewDestroyed()
                compositeSubscription.clear()
            }
        }
    }

    protected fun Subscription.addToComposite() { compositeSubscription.add(this) }

    protected fun getArgs() = view.getArgs()
}
