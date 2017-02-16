package com.exallium.todoapp.entities

/**
 * The Note entity
 */
data class Note(val id: String,
                val title: String,
                val body: String,
                val created: Long,
                val updated: Long) {

    fun with(id: String? = null,
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
