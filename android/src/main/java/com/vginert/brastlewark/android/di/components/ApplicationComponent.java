package com.vginert.brastlewark.android.di.components;

import android.content.Context;

import com.vginert.brastlewark.android.di.modules.ApplicationModule;
import com.vginert.brastlewark.android.view.activity.BaseActivity;
import com.vginert.brastlewark.domain.executor.IPostExecutionThread;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 *
 * @author Vicente Giner Tendero
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  IThreadExecutor threadExecutor();
  IPostExecutionThread postExecutionThread();
  IHeroRepository heroRepository();
}
