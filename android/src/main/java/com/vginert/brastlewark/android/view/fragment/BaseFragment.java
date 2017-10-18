package com.vginert.brastlewark.android.view.fragment;

import android.support.v4.app.Fragment;

import com.vginert.brastlewark.android.di.HasComponent;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 *
 * @author Vicente Giner Tendero
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
