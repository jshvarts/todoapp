package com.exallium.todoapp.allnotes

import com.exallium.todoapp.di.BaseComponent
import com.exallium.todoapp.di.PerScreen
import dagger.Component

@PerScreen
@Component(modules = arrayOf(AllNotesModule::class))
interface AllNotesComponent : BaseComponent<AllNotesController>