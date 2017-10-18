package com.vginert.brastlewark.android.exception;

import android.content.Context;

import com.vginert.brastlewark.android.R;
import com.vginert.brastlewark.android.di.PerActivity;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;
import com.vginert.brastlewark.data.exception.NetworkConnectionException;

import javax.inject.Inject;

/**
 * Factory used to create error messages from an Exception as a condition.
 *
 * @author Vicente Giner Tendero
 */
@PerActivity
public class ErrorMessageFactory {

    private final Context context;

    @Inject
    public ErrorMessageFactory(Context context) {
        this.context = context;
    }

    /**
     * Creates a String representing an error message.
     *
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public String create(Exception exception) {
        String message = this.context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = this.context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof HeroNotFoundException) {
            message = this.context.getString(R.string.exception_message_hero_not_found);
        }

        return message;
    }
}
