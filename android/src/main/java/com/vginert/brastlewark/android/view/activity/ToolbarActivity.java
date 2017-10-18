package com.vginert.brastlewark.android.view.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * Interface that represent a Toolbar Activity
 *
 * @author Vicente Giner Tendero
 */

public interface ToolbarActivity {
    void setToolbar(Toolbar toolbar);
    ActionBar getToolbar();
}
