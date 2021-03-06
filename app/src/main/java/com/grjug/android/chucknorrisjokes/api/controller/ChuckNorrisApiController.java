package com.grjug.android.chucknorrisjokes.api.controller;

import com.grjug.android.chucknorrisjokes.api.dao.ChuckNorrisApiDao;
import com.grjug.android.chucknorrisjokes.model.JokeResponse;
import com.grjug.android.chucknorrisjokes.model.UIJoke;

import retrofit.RestAdapter;
import retrofit.http.GET;
import rx.Observable;
import rx.functions.Func1;
import timber.log.Timber;

/**
 * Created by foxefj on 3/18/14.
 */
public class ChuckNorrisApiController {
    private final ChuckNorrisService chuckNorrisService;
    private ChuckNorrisApiDao apiDao;

    public ChuckNorrisApiController(ChuckNorrisApiDao apiDao) {
        this.apiDao = apiDao;
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://api.icndb.com")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            chuckNorrisService = restAdapter.create(ChuckNorrisService.class);

    }

    public Observable<UIJoke> fetchRandomJoke() {
                Timber.i("Retrieving joke...");
        return chuckNorrisService.fetchRandomJoke().map(new Func1<JokeResponse, UIJoke>() {
            @Override
            public UIJoke call(JokeResponse jokeResponse) {
                Timber.i("Found joke with id: %d", jokeResponse.getJoke().getId());
                return new UIJoke(jokeResponse);
            }
        });
    }

    public interface ChuckNorrisService {
        @GET("/jokes/random")
        public Observable<JokeResponse> fetchRandomJoke();
    }


}
