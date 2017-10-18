package com.vginert.brastlewark.android.exception;

import android.test.AndroidTestCase;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.data.exception.NetworkConnectionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorImageFactoryTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void tesGenericErrorMessage() {
        int expectedDrawable = R.drawable.general_error;
        int actualDrawable = ErrorImageFactory.create(new Exception());

        assertThat(actualDrawable, is(equalTo(expectedDrawable)));
    }

    public void testNetworkConnectionErrorMessage() {
        int expectedDrawable = R.drawable.internet_error;
        int actualDrawable = ErrorImageFactory.create(new NetworkConnectionException());

        assertThat(actualDrawable, is(equalTo(expectedDrawable)));
    }
}