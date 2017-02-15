package com.exallium.todoapp

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.exallium.todoapp.allnotes.AllNotesViewImpl

/**
 * Single Activity for Application
 */
class MainActivity : Activity() {

    @BindView(R.id.conductor_container)
    lateinit var container: ViewGroup

    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(AllNotesViewImpl(Bundle())))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }
}