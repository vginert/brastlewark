package com.vginert.brastlewark.android.view;

/**
 * Interface representing a View that will use to load data.
 *
 * @author Vicente Giner Tendero
 */
public interface LoadDataView extends BaseView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show a retry view in case of an error when retrieving data.
     * @param message A string representing an error.
     */
    void showRetry(String message);

    /**
     * Hide a retry view shown if there was an error when retrieving data.
     */
    void hideRetry();
}
