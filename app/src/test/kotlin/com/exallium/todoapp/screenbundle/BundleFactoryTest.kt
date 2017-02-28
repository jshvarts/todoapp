package com.exallium.todoapp.screenbundle

import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Unit testing for {@link BundleFactory}
 */
class BundleFactoryTest {
    lateinit var testSubject: BundleFactoryImpl

    @Before
    fun setUp() {
        testSubject = BundleFactoryImpl()
    }

    @Test
    fun createBundle_returnsBundle() {
        // WHEN
        val result = testSubject.createBundle()

        // THEN
        assertNotNull(result)
    }
}