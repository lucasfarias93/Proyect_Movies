package webview.project.movies.Clients;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import webview.project.movies.Entities.MovieVideo;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.RestMoviesProvider.RestMoviesConnectionProvider;
import webview.project.movies.RetrofitHelper.RetrofitHelper;

public class VideoMovieAsynkConnection extends AsyncTask<String,Void,List<VideoDetailObject>> {

    List<VideoDetailObject> videoDataList;
    private Callback callback;
    private Context context;
    private Integer movie_id;


    public interface Callback {
        void getVideoLinkCallback(Object video);
    }

    public VideoMovieAsynkConnection(Callback callback, Context context, Integer movie_id) {
        this.callback = callback;
        this.context = context;
        this.movie_id = movie_id;
    }

    @Override
    protected List<VideoDetailObject> doInBackground(String[] params) {

        String api_key = params[0];

        RetrofitHelper retrofitHelper = new RetrofitHelper();
        RestMoviesConnectionProvider restMoviesConnectionProvider = retrofitHelper.createProvider(RestMoviesConnectionProvider.class);

        try {
            Call<MovieVideo> request = restMoviesConnectionProvider.getMovieVideo(movie_id, api_key);
            Response<MovieVideo> response = request.execute();
            getVideoResult(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoDataList;
    }

    @Override
    protected void onPostExecute(List<VideoDetailObject> videoDataList) {
        super.onPostExecute(videoDataList);
        callback.getVideoLinkCallback(videoDataList);
    }

    public List<VideoDetailObject> getVideoResult(Response<MovieVideo> response){
        MovieVideo movieVideo = response.body();
        videoDataList = movieVideo.getVideoDetails();
        return videoDataList;
    }
}