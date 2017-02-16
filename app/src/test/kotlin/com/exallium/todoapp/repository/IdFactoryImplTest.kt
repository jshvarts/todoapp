package com.exallium.todoapp.repository

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Unit testing for {@link IdFactoryImpl}
 */
class IdFactoryImplTest {

    lateinit var testSubject: IdFactoryImpl

    @Before
    fun setUp() {
        testSubject = IdFactoryImpl()
    }

    @Test
    fun createId_when10Requested_allAreUnique() {
        // GIVEN
        val expected = 10

        // WHEN
        val result = (1..expected).map { testSubject.createId() }.distinct().size

        // THEN
        assertEquals(expected, result)
    }
}
