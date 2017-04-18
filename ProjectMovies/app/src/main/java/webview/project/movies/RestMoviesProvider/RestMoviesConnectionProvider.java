package webview.project.movies.RestMoviesProvider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.MovieVideo;
import webview.project.movies.Entities.MoviesResult;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.Utils.AppConstants;

import java.util.List;

/**
 * Created by lfarias on 4/12/17.
 */
public interface RestMoviesConnectionProvider {

    //MOST POPULAR MOVVIES
    @GET("movie/popular")
    Call<MoviesResult> getMoviePopularList(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page_num
    );

    //NOW PLAYING MOVIES
    @GET("movie/now_playing")
    Call<MoviesResult> getMovieNowPlayingList(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page_num
    );

    //TOP RATED MOVIES
    @GET("movie/top_rated")
    Call<MoviesResult> getMovieTopRatedList(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page_num
    );

    //VIDEOS MOVIES
    @GET("movie/{movie_id}/videos")
    Call<MovieVideo> getMovieVideo(
            @Path("movie_id") Integer id,
            @Query("api_key") String api_key)
    ;

    //REVIEW MOVIES
    @GET("movie/{movie_id}/reviews")
    Call<MoviesResult> getMoviesReview();

}
