package com.exallium.todoapp.database

import java.util.UUID

data class Note(val id: UUID,
                val title: String,
                val body: String,
                val created: Long,
                val updated: Long) {

    fun with(id: UUID? = null,
             title: String? = null,
             body: String? = null,
             created: Long? = null,
             updated: Long? = null) = Note(
            id ?: this.id,
            title ?: this.title,
            body ?: this.body,
            created ?: this.created,
            updated ?: this.updated)
}
