package com.exallium.todoapp.screenbundle

import android.os.Bundle

/**
 * Interface for generating {@link android.os.Bundle} instances in order to avoid
 * having to use Robolectric to test various classes that createBundle a Bundle.
 */
interface BundleFactory {
    fun createBundle(): Bundle
}
