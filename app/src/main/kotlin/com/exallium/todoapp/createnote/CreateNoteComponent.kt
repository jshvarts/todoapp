package com.exallium.todoapp.createnote

import com.exallium.todoapp.app.TodoAppComponent
import com.exallium.todoapp.di.BaseComponent
import com.exallium.todoapp.di.PerScreen
import dagger.Component

@PerScreen
@Component(modules = arrayOf(CreateNoteModule::class), dependencies = arrayOf(TodoAppComponent::class))
interface CreateNoteComponent : BaseComponent<CreateNoteViewImpl>
