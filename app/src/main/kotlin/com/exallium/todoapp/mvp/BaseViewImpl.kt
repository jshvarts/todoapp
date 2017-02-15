package com.exallium.todoapp.mvp

import android.app.Activity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Controller
import com.exallium.todoapp.di.BaseComponent
import rx.Observable
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * Base Conductor Controller, which is visible to Presenters as a sub-interface of BaseView
 */
abstract class BaseViewImpl<
        out V : BaseView,
        P : BasePresenter<V>,
        B : BaseViewImpl<V, P, B, C>,
        out C : BaseComponent<B>>(bundle: Bundle) : Controller(bundle), BaseView {

    private lateinit var _presenter: P

    private val lifecycleSubject: PublishSubject<BaseView.LifecycleEvent> = PublishSubject.create<BaseView.LifecycleEvent>()

    @Suppress("UNCHECKED_CAST")
    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflater.inflate(getLayoutId(), container, false)
        ButterKnife.bind(this, view)
        getComponent().inject(this as B)
        setUp()
        lifecycleSubject.onNext(BaseView.LifecycleEvent.CREATE_VIEW)
        return view
    }

    override final fun onActivityStarted(activity: Activity) {
        lifecycleSubject.onNext(BaseView.LifecycleEvent.ACTIVITY_START)
    }

    override final fun onActivityResumed(activity: Activity) {
        lifecycleSubject.onNext(BaseView.LifecycleEvent.ACTIVITY_RESUME)
    }

    override final fun onActivityPaused(activity: Activity) {
        lifecycleSubject.onNext(BaseView.LifecycleEvent.ACTIVITY_PAUSE)
    }

    override final fun onActivityStopped(activity: Activity) {
        lifecycleSubject.onNext(BaseView.LifecycleEvent.ACTIVITY_STOP)
    }

    override final fun onDestroyView(view: View) {
        tearDown()
        lifecycleSubject.onNext(BaseView.LifecycleEvent.DESTROY_VIEW)
    }

    override final fun lifecycleEvents(): Observable<BaseView.LifecycleEvent> = lifecycleSubject

    override fun displaySnackbar(@StringRes stringResourceId: Int, @BaseTransientBottomBar.Duration length: Int) {
        view?.let { Snackbar.make(it, stringResourceId, length) }
    }

    override fun displaySnackbar(string: String, @BaseTransientBottomBar.Duration length: Int) {
        view?.let { Snackbar.make(it, string, length) }
    }

    @Inject
    fun setPresenter(presenter: P) {
        _presenter = presenter
    }

    /**
     * Any edge-case setup code for a particular view goes here.
     * Try to avoid using this.
     */
    protected open fun setUp() {
    }

    /**
     * Any edge-case teardown code for a particular view goes here.
     * Try to avoid using this.
     */
    protected open fun tearDown() {
    }

    abstract fun getComponent(): C

    @IdRes
    abstract protected fun getLayoutId(): Int
}