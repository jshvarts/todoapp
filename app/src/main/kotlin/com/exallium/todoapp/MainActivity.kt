package com.exallium.todoapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextSwitcher
import butterknife.BindView
import butterknife.ButterKnife
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.exallium.todoapp.allnotes.AllNotesViewImpl

/**
 * Single Activity for Application
 */
class MainActivity : AppCompatActivity() {

    @BindView(R.id.conductor_container)
    lateinit var container: ViewGroup

    @BindView(R.id.main_activity_toolbar_title)
    lateinit var toolbarTitle: TextSwitcher

    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ButterKnife.bind(this)
        router = Conductor.attachRouter(this, container, savedInstanceState)
        toolbarTitle.setText(getString(R.string.app_name))
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