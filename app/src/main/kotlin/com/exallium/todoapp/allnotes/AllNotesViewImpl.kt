package com.exallium.todoapp.allnotes

import android.os.Bundle
import com.exallium.todoapp.mvp.BaseViewImpl

/**
 * Conductor Controller displaying all Notes
 */
class AllNotesViewImpl(bundle: Bundle) : BaseViewImpl<AllNotesView, AllNotesPresenter, AllNotesViewImpl, AllNotesComponent>(bundle), AllNotesView {
    override fun getComponent(): AllNotesComponent = DaggerAllNotesComponent.builder()
            .allNotesModule(AllNotesModule(this))
            .build()

    override fun getLayoutId(): Int = -1

    override fun setAdapter(adapter: AllNotesAdapter) {
        throw UnsupportedOperationException("not implemented")
    }
}