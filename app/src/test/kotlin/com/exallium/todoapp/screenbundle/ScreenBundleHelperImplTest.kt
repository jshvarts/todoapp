package com.exallium.todoapp.screenbundle

import android.content.res.Resources
import android.os.Bundle
import com.exallium.todoapp.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Unit testing for ScreenBundleHelperImpl
 */
class ScreenBundleHelperImplTest {

    companion object {
        private val TITLE = "screenBundleHelper.title"

        private val APP_NAME = "TodoApp"
        private val TEST_TITLE_RES_ID = 1
        private val TEST_TITLE_STRING = "test title"
    }

    @InjectMocks
    private lateinit var testSubject: ScreenBundleHelperImpl

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var bundle: Bundle

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(resources.getString(R.string.app_name)).thenReturn(APP_NAME)
        `when`(resources.getString(TEST_TITLE_RES_ID)).thenReturn(TEST_TITLE_STRING)
    }

    @Test
    fun setTitle_getsTitleFromResourcesAndWritesToBundle() {
        // WHEN
        testSubject.setTitle(bundle, TEST_TITLE_RES_ID)

        // THEN
        verify(resources).getString(TEST_TITLE_RES_ID)
        verify(bundle).putString(TITLE, TEST_TITLE_STRING)
    }

    @Test
    fun setTitle_doesNotAccessResourcesAndWritesToBundle() {
        // WHEN
        testSubject.setTitle(bundle, TEST_TITLE_STRING)

        // THEN
        verifyZeroInteractions(resources)
        verify(bundle).putString(TITLE, TEST_TITLE_STRING)
    }

    @Test
    fun getTitle_whenBundleDoesNotContainTitle_returnsAppName() {
        // GIVEN
        `when`(bundle.getString(TITLE, APP_NAME)).thenReturn(APP_NAME)

        // WHEN
        val result = testSubject.getTitle(bundle)

        // THEN
        verify(bundle).getString(TITLE, APP_NAME)
        assertThat(result, `is`(APP_NAME))
    }

    @Test
    fun getTitle_whenBundleIsNull_returnsAppName() {
        // WHEN
        val result = testSubject.getTitle(null)

        // THEN
        assertThat(result, `is`(APP_NAME))
    }
}