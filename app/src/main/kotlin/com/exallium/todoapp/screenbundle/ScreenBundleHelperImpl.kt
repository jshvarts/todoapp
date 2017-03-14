package com.exallium.todoapp.screenbundle

import android.content.res.Resources
import android.os.Bundle
import com.exallium.todoapp.R

/**
 * Implementation for ScreenBundleHelper
 */
class ScreenBundleHelperImpl(private val resources: Resources) : ScreenBundleHelper {
    companion object {
        private val TITLE = "screenBundleHelper.title"
        private val NOTE_ID = "screenBundleHelper.noteId"
    }

    private val appName: String
        get() = resources.getString(R.string.app_name)

    override fun setTitle(bundle: Bundle, screenTitleRes: Int) {
        bundle.putString(TITLE, resources.getString(screenTitleRes))
    }

    override fun setTitle(bundle: Bundle, screenTitle: String) {
        bundle.putString(TITLE, screenTitle)
    }

    override fun getTitle(bundle: Bundle?): String
            = bundle?.getString(TITLE, appName)?:appName

    override fun setNoteId(bundle: Bundle, noteId: String?) {
        bundle.putString(NOTE_ID, noteId)
    }

    override fun getNoteId(bundle: Bundle): String?
            = bundle.getString(NOTE_ID, null)
}