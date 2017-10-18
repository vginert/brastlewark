package com.vginert.brastlewark.android.exception;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;
import com.vginert.brastlewark.data.exception.NetworkConnectionException;

import javax.inject.Inject;

/**
 * Factory used to create error images from an Exception as a condition.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class ErrorImageFactory {

    @Inject
    public ErrorImageFactory() {

    }

    /**
     * Creates a drawable id representing an error.
     *
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return the id of the drawable error image.
     */
    public int create(Exception exception) {
        int id = R.drawable.general_error;

        if (exception instanceof NetworkConnectionException) {
            id = R.drawable.internet_error;
        } else if (exception instanceof HeroNotFoundException) {
            id = R.drawable.sorry;
        }

        return id;
    }
}
