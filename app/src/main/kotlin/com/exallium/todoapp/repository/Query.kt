package com.exallium.todoapp.repository

/**
 * Data Access Query Object
 */
sealed class Query<T> {
    class SingleObjectByIdQuery<T>(val id: String) : Query<T>()
    class AllObjectsQuery<T>(val limit: Int = -1): Query<T>()
}