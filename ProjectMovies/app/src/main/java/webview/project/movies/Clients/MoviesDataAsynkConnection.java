package webview.project.movies.Clients;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.RestMoviesProvider.RestMoviesConnectionProvider;
import webview.project.movies.RetrofitHelper.RetrofitHelper;

/**
 * Created by lfarias on 4/12/17.
 */

//AsyncTask used to connect with the API only in case the search filter is set with Top Rated or Most Popular movies
public class MoviesDataAsynkConnection extends AsyncTask {
    public interface Callback {
        void getMoviePopularDataListCallback(Object movies);

        void getMoviesTopRatedDataListCallback(Object movies);
    }

    private Callback callback;
    private Context context;
    private String filter_type;

    public MoviesDataAsynkConnection(Context context, Callback callback, String filter_Type) {
        this.callback = callback;
        this.context = context;
        this.filter_type = filter_Type;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        RetrofitHelper retrofit = new RetrofitHelper();
        RestMoviesConnectionProvider restMoviesConnectionProvider = retrofit.createProvider(RestMoviesConnectionProvider.class);
        if (filter_type == "Popular") {
            try {
                Call<List<MovieData>> request = restMoviesConnectionProvider.getMoviePopularList();
                Response<List<MovieData>> response = request.execute();
                response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Call<List<MovieData>> request = restMoviesConnectionProvider.getMovieTopRatedList();
                Response<List<MovieData>> response = request.execute();
                response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object movies) {
        super.onPostExecute(movies);
        if(filter_type == "Popular"){
            callback.getMoviePopularDataListCallback(movies);
        } else{
            callback.getMoviesTopRatedDataListCallback(movies);
        }
    }
}