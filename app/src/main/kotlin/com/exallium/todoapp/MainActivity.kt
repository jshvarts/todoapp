package com.exallium.todoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextSwitcher
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.*
import com.exallium.todoapp.allnotes.AllNotesViewImpl
import com.exallium.todoapp.app.TodoApp
import com.exallium.todoapp.screenbundle.BundleFactory
import com.exallium.todoapp.screenbundle.ScreenBundleHelper
import javax.inject.Inject

/**
 * Single Activity for Application
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var screenBundleHelper: ScreenBundleHelper

    @Inject
    lateinit var bundleFactory: BundleFactory

    @BindView(R.id.conductor_container)
    lateinit var container: ViewGroup

    @BindView(R.id.main_activity_toolbar_title)
    lateinit var toolbarTitle: TextSwitcher

    private lateinit var router: Router

    private val changeListener = object : ControllerChangeHandler.ControllerChangeListener {
        override fun onChangeStarted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
        }

        override fun onChangeCompleted(to: Controller?, from: Controller?, isPush: Boolean, container: ViewGroup, handler: ControllerChangeHandler) {
            val title = screenBundleHelper.getTitle(to?.args)
            toolbarTitle.setText(title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        TodoApp.component.inject(this)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        router.addChangeListener(changeListener)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(AllNotesViewImpl(bundleFactory.createBundle())))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}