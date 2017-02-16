package com.exallium.todoapp.repository

/**
 * Interface for Accessing Data
 */
interface QueryMapper<T> {
    fun query(query: Query<T>): QueryResponse<T>
}