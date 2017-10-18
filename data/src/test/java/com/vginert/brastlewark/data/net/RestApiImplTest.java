package com.vginert.brastlewark.data.net;

import android.content.Context;

import com.vginert.brastlewark.data.ApplicationTestCase;
import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.entity.mapper.HeroEntityJsonMapper;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import io.reactivex.observers.TestObserver;

public class RestApiImplTest extends ApplicationTestCase {

    private RestApiImpl restApi;

    @Mock
    HeroEntityJsonMapper heroEntityJsonMapper;
    @Mock
    Context context;

    @Test
    public void constructorHappyCase() {
        this.restApi = new RestApiImpl(context(), this.heroEntityJsonMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullContext() {
        this.restApi = new RestApiImpl(null, this.heroEntityJsonMapper);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorNullMapper() {
        this.restApi = new RestApiImpl(context(), null);
    }

    @Test
    public void heroEntityList() {
        TestObserver<List<HeroEntity>> testObserver = new TestObserver<>();
        this.restApi = new RestApiImpl(context(), this.heroEntityJsonMapper);
        this.restApi.heroEntityList().subscribe(testObserver);
    }

    @Test
    public void heroEntityListNoConnection() {

        TestObserver<List<HeroEntity>> testObserver = new TestObserver<>();
        this.restApi = new RestApiImpl(this.context, this.heroEntityJsonMapper);
        this.restApi.heroEntityList().subscribe(testObserver);
    }
}