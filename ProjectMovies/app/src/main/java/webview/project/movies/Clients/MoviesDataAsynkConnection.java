package webview.project.movies.Clients;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.MoviesResult;
import webview.project.movies.RestMoviesProvider.RestMoviesConnectionProvider;
import webview.project.movies.RetrofitHelper.RetrofitHelper;

/**
 * Created by lfarias on 4/12/17.
 */

//AsyncTask used to connect with the API only in case the search filter is set with Top Rated or Most Popular movies
public class MoviesDataAsynkConnection extends AsyncTask<String,Void,List<MovieData>>{

    List<MovieData> movieDataList;

    public interface Callback {
        void getMovieDataListCallback(Object movies);
    }

    private Callback callback;
    private Context context;
    private Integer filter_type;

    public MoviesDataAsynkConnection(Callback callback, Context context, Integer filter_Type) {
        this.callback = callback;
        this.context = context;
        this.filter_type = filter_Type;
    }

    @Override
    protected List<MovieData> doInBackground(String[] params) {

        String api_key = params[0];
        String language = params[1];
        String page_num = (params[2]);

        int page_number = Integer.parseInt(page_num);

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        RestMoviesConnectionProvider restMoviesConnectionProvider = retrofitHelper.createProvider(RestMoviesConnectionProvider.class);
        switch(filter_type){
            case 0:
                try {
                    Call<MoviesResult> request = restMoviesConnectionProvider.getMovieNowPlayingList(api_key, language, page_number);
                    Response<MoviesResult> response = request.execute();
                    getMoviesResult(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case 1:
                try {
                Call<MoviesResult> request = restMoviesConnectionProvider.getMoviePopularList(api_key, language, page_number);
                Response<MoviesResult> response = request.execute();
                getMoviesResult(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
                break;

            case 2:
                try {
                    Call<MoviesResult> request = restMoviesConnectionProvider.getMovieTopRatedList(api_key, language, page_number);
                    Response<MoviesResult> response = request.execute();
                    getMoviesResult(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

        return movieDataList;
    }

    @Override
    protected void onPostExecute(List<MovieData> movieDataList) {
        super.onPostExecute(movieDataList);
        callback.getMovieDataListCallback(movieDataList);
    }

    public List<MovieData> getMoviesResult(Response<MoviesResult> response){
        MoviesResult moviesResult = response.body();
        movieDataList = moviesResult.getResults();
        return movieDataList;

    }
}