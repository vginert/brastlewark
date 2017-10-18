package com.vginert.brastlewark.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.vginert.brastlewark.data.cache.IHeroCache;
import com.vginert.brastlewark.data.entity.mapper.HeroEntityJsonMapper;
import com.vginert.brastlewark.data.filter.HeroFilter;
import com.vginert.brastlewark.data.net.RestApi;
import com.vginert.brastlewark.data.net.RestApiImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link IHeroDataStore}.
 *
 * @author Vicente Giner Tendero
 */
@Singleton
public class HeroDataStoreFactory {

    private final Context context;
    private final IHeroCache heroCache;

    @Inject
    HeroDataStoreFactory(@NonNull Context context, @NonNull IHeroCache heroCache) {
        this.context = context.getApplicationContext();
        this.heroCache = heroCache;
    }

    /**
     * Create {@link IHeroDataStore} from a hero id.
     */
    public IHeroDataStore create(int heroId) {
        IHeroDataStore heroDataStore;

        if (this.heroCache.isCached(heroId)) {
            heroDataStore = new DiskHeroDataStore(this.heroCache);
        } else {
            heroDataStore = createCloudDataStore();
        }

        return heroDataStore;
    }

    /**
     * Create {@link IHeroDataStore} to retrieve data from the Cloud.
     */
    public IHeroDataStore createCloudDataStore() {
        final HeroEntityJsonMapper heroEntityJsonMapper = new HeroEntityJsonMapper();
        final RestApi restApi = new RestApiImpl(this.context, heroEntityJsonMapper);
        final HeroFilter heroFilter = new HeroFilter();

        return new CloudHeroDataStore(restApi, this.heroCache, heroFilter);
    }
}
