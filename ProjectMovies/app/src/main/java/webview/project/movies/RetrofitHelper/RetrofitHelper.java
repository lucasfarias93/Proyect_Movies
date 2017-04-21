package webview.project.movies.RetrofitHelper;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import webview.project.movies.Utils.AppConstants;


public class RetrofitHelper {

    private OkHttpClient.Builder httpClient;

    private Retrofit retrofit;

    public RetrofitHelper() {
        this.httpClient = new OkHttpClient.Builder();
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    //Override diferent createProvider methods in order to implement diferent type of Providers
    public <S> S createProvider(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
