package com.vginert.brastlewark.data;

import android.content.Context;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = ApplicationStub.class, sdk = {19, 21, 22, 23, 24, 25, 26})
public abstract class ApplicationTestCase {

    @Rule
    public TestRule injectMocksRule = (base, description) -> {
        MockitoAnnotations.initMocks(ApplicationTestCase.this);
        return base;
    };

    public static Context context() {
        return RuntimeEnvironment.application;
    }

    public static File cacheDir() {
        return RuntimeEnvironment.application.getCacheDir();
    }
}
