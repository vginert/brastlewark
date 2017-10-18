package com.vginert.brastlewark.android.exception;

import android.test.AndroidTestCase;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;
import com.vginert.brastlewark.data.exception.NetworkConnectionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ErrorMessageFactoryTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void tesGenericErrorMessage() {
        String expectedMessage = getContext().getString(R.string.exception_message_generic);
        String actualMessage = ErrorMessageFactory.create(getContext(),
                new Exception());

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    public void testNetworkConnectionErrorMessage() {
        String expectedMessage = getContext().getString(R.string.exception_message_no_connection);
        String actualMessage = ErrorMessageFactory.create(getContext(),
                new NetworkConnectionException());

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    public void testUserNotFoundErrorMessage() {
        String expectedMessage = getContext().getString(R.string.exception_message_hero_not_found);
        String actualMessage = ErrorMessageFactory.create(getContext(), new HeroNotFoundException());

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }
}