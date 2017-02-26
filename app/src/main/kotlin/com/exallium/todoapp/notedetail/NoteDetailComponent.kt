package com.exallium.todoapp.notedetail

import com.exallium.todoapp.app.TodoAppComponent
import com.exallium.todoapp.di.BaseComponent
import com.exallium.todoapp.di.PerScreen
import dagger.Component

@PerScreen
@Component(modules = arrayOf(NoteDetailModule::class), dependencies = arrayOf(TodoAppComponent::class))
interface NoteDetailComponent : BaseComponent<NoteDetailViewImpl>