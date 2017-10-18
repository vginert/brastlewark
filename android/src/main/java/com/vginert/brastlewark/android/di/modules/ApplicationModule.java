package com.vginert.brastlewark.android.di.modules;

import android.content.Context;

import com.vginert.brastlewark.android.AndroidApplication;
import com.vginert.brastlewark.android.UIThread;
import com.vginert.brastlewark.data.cache.HeroCache;
import com.vginert.brastlewark.data.cache.IHeroCache;
import com.vginert.brastlewark.data.executor.JobExecutor;
import com.vginert.brastlewark.data.repository.HeroRepository;
import com.vginert.brastlewark.domain.executor.IPostExecutionThread;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;
import com.vginert.brastlewark.domain.repository.IHeroRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * @author Vicente Giner Tendero
 */
@Module
public class ApplicationModule {
  private final AndroidApplication application;

  public ApplicationModule(AndroidApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides
  @Singleton
  IThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
    return jobExecutor;
  }

  @Provides
  @Singleton
  IPostExecutionThread providePostExecutionThread(UIThread uiThread) {
    return uiThread;
  }

  @Provides
  @Singleton
  IHeroCache provideHeroCache(HeroCache heroCache) {
    return heroCache;
  }

  @Provides
  @Singleton
  IHeroRepository provideHeroRepository(HeroRepository heroRepository) {
    return heroRepository;
  }
}
