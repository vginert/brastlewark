package com.vginert.brastlewark.android.di;

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 *
 * @author Vicente Giner Tendero
 */
public interface HasComponent<C> {
  C getComponent();
}
