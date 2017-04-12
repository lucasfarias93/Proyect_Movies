package webview.project.movies.RetrofitHelper;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.RestMoviesProvider.RestMoviesConnectionProvider;

/**
 * Created by lfarias on 4/12/17.
 */
public class RetrofitHelper {
    private String API_URL = "http://api.themoviedb.org/3/";
    private String API_KEY = "46959d86464d31094662c46fb1f59efc";

    private OkHttpClient.Builder httpClient;

    private Retrofit retrofit;


    public RetrofitHelper() {
        this.httpClient = new OkHttpClient.Builder();
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    //Override diferent createProvider methods in order to implement diferent type of Providers
    public <S> S createProvider(Class<S> serviceClass) {
        return createProvider(serviceClass);
    }

}
