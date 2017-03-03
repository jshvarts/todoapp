package com.exallium.todoapp.editnote

import com.exallium.todoapp.app.TodoAppComponent
import com.exallium.todoapp.di.BaseComponent
import com.exallium.todoapp.di.PerScreen
import dagger.Component

@PerScreen
@Component(modules = arrayOf(EditNoteModule::class), dependencies = arrayOf(TodoAppComponent::class))
interface EditNoteComponent : BaseComponent<EditNoteViewImpl>