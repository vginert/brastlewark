package com.vginert.brastlewark.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vginert.brastlewark.data.entity.HeroEntity;
import com.vginert.brastlewark.data.entity.mapper.HeroEntityJsonMapper;
import com.vginert.brastlewark.data.exception.NetworkConnectionException;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;

import io.reactivex.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network.
 *
 * @author Vicente Giner Tendero
 */
public class RestApiImpl implements RestApi {

    private final Context context;
    private final HeroEntityJsonMapper heroEntityJsonMapper;

    /**
     * Constructor of the class
     *
     * @param context              {@link android.content.Context}.
     * @param heroEntityJsonMapper {@link HeroEntityJsonMapper}.
     */
    public RestApiImpl(Context context, HeroEntityJsonMapper heroEntityJsonMapper) {
        if (context == null || heroEntityJsonMapper == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }
        this.context = context.getApplicationContext();
        this.heroEntityJsonMapper = heroEntityJsonMapper;
    }

    @Override
    public Observable<List<HeroEntity>> heroEntityList() {
        return Observable.create(subscriber -> {
            if (isThereInternetConnection()) {
                try {
                    String responseHeroEntities = getHeroEntitiesFromApi();
                    if (responseHeroEntities != null) {
                        subscriber.onNext(heroEntityJsonMapper.transformHeroEntityCollection(
                                responseHeroEntities));
                        subscriber.onComplete();
                    } else {
                        subscriber.onError(new NetworkConnectionException());
                    }
                } catch (Exception e) {
                    subscriber.onError(new NetworkConnectionException(e.getCause()));
                }
            } else {
                subscriber.onError(new NetworkConnectionException());
            }
        });
    }

    private String getHeroEntitiesFromApi() throws MalformedURLException {
        try {
            return new JSONObject(ApiConnection.createGET(RestApi.API_URL_GET_HERO_LIST).call()).getString("Brastlewark");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
