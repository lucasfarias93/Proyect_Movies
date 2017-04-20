package webview.project.movies.Clients;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import webview.project.movies.Entities.ReviewDetails;
import webview.project.movies.Entities.ReviewResult;
import webview.project.movies.RestMoviesProvider.RestMoviesConnectionProvider;
import webview.project.movies.RetrofitHelper.RetrofitHelper;

/**
 * Created by LUCAS on 19/04/2017.
 */
public class ReviewsMovieAsynkConnection extends AsyncTask<String,Void,List<ReviewDetails>> {

    List<ReviewDetails> reviewDetailsList;
    private Callback callback;
    private Context context;
    private Integer movie_id;


    public interface Callback {
        void getReviewsCallback(Object reviews);
    }

    public ReviewsMovieAsynkConnection(Callback callback, Context context, Integer movie_id) {
        this.callback = callback;
        this.context = context;
        this.movie_id = movie_id;
    }

    @Override
    protected List<ReviewDetails> doInBackground(String[] params) {

        String api_key = params[0];

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        RestMoviesConnectionProvider restMoviesConnectionProvider = retrofitHelper.createProvider(RestMoviesConnectionProvider.class);

        try {
            Call<ReviewResult> request = restMoviesConnectionProvider.getMoviesReview(movie_id, api_key);
            Response<ReviewResult> response = request.execute();
            getReviewsResult(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reviewDetailsList;
    }

    @Override
    protected void onPostExecute(List<ReviewDetails> reviewDetailsList) {
        super.onPostExecute(reviewDetailsList);
        callback.getReviewsCallback(reviewDetailsList);
    }

    public List<ReviewDetails> getReviewsResult(Response<ReviewResult> response){
        ReviewResult reviewResult = response.body();
        reviewDetailsList = reviewResult.getResults();
        return reviewDetailsList;
    }
}
