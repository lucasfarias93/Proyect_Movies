package webview.project.movies.RestMoviesProvider;

import retrofit2.Call;
import retrofit2.http.GET;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.MovieVideo;

import java.util.List;

/**
 * Created by lfarias on 4/12/17.
 */
public interface RestMoviesConnectionProvider {

    //MOST POPULAR MOVVIES
    @GET("/movie/popular")
    Call<List<MovieData>> getMoviePopularList();

    //TOP RATED MOVIES
    @GET("/movie/top_rated")
    Call<List<MovieData>> getMovieTopRatedList();

    //VIDEOS MOVIES
    @GET("/movie/{movie_id}/videos")
    Call<List<MovieVideo>> getMovieVideo();

}
