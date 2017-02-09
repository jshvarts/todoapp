package com.exallium.todoapp.database

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit testing for IdFactoryImpl
 */
class IdFactoryImplTest {

    val testSubject = IdFactoryImpl()

    @Test
    fun createId_when1000Requested_allAreUnique() {
        // GIVEN
        val expected = 1000

        // WHEN
        val result = (1..expected).map { testSubject.createId() }.distinct().size

        // THEN
        assertEquals(expected, result)
    }
}
