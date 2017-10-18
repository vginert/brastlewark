package com.vginert.brastlewark.android.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * A scoping annotation to permit objects whose lifetime should
 * conform to the life of the activity to be memorized in the
 * correct component.
 *
 * @author Vicente Giner Tendero
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
