package com.vginert.brastlewark.data.cache;

import android.content.Context;

import com.vginert.brastlewark.data.cache.utils.Serializer;
import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.exception.HeroNotFoundException;
import com.vginert.brastlewark.domain.executor.IThreadExecutor;

import java.io.File;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * {@link IHeroCache} implementation.
 *
 * @author Vicente Giner Tendero
 */
@Singleton
public class HeroCache implements IHeroCache {

    private static final String SETTINGS_FILE_NAME = "com.vginert.brastlewark.data.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "hero_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final IThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link HeroCache}.
     *
     * @param context     A
     * @param serializer  {@link Serializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    HeroCache(Context context, Serializer serializer,
              FileManager fileManager, IThreadExecutor executor) {
        if (context == null || serializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = serializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<HeroEntity> get(final int heroId) {
        return Observable.create(emitter -> {
            final File heroEntityFile = HeroCache.this.buildFile(heroId);
            final String fileContent = HeroCache.this.fileManager.readFileContent(heroEntityFile);
            final HeroEntity heroEntity =
                    HeroCache.this.serializer.deserialize(fileContent, HeroEntity.class);

            if (heroEntity != null) {
                emitter.onNext(heroEntity);
                emitter.onComplete();
            } else {
                emitter.onError(new HeroNotFoundException());
            }
        });
    }

    @Override
    public void put(HeroEntity heroEntity) {
        if (heroEntity != null) {
            final File heroEntityFile = this.buildFile(heroEntity.getId());
            if (!isCached(heroEntity.getId())) {
                final String jsonString = this.serializer.serialize(heroEntity, HeroEntity.class);
                this.executeAsynchronously(new CacheWriter(this.fileManager, heroEntityFile, jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public void put(List<HeroEntity> heroEntityList) {
        heroEntityList.forEach(this::put);
    }

    @Override
    public boolean isCached(int heroId) {
        final File heroEntityFile = this.buildFile(heroId);
        return this.fileManager.exists(heroEntityFile);
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @param heroId The id hero to build the file.
     * @return A valid file.
     */
    private File buildFile(int heroId) {
        final StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);
        fileNameBuilder.append(heroId);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        final long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override
        public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /**
     * {@link Runnable} class for evicting all the cached files
     */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override
        public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
