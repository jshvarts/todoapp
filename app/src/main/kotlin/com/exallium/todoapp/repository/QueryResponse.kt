package com.exallium.todoapp.repository

/**
 * Response to a Query
 */
sealed class QueryResponse<T> {
    class SingleObjectByIdQueryResponse<T>(val item: T) : QueryResponse<T>()
    class AllObjectsQueryResponse<T>(val items: Set<T>): QueryResponse<T>()
}