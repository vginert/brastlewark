package com.vginert.brastlewark.android.view;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interface representing a View.
 *
 * @author Vicente Giner Tendero
 */
public interface BaseView {
    /**
     * Get a {@link android.content.Context}.
     */
    @NonNull
    Context context();
}
