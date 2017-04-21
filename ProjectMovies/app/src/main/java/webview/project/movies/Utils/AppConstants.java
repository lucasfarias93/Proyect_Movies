package webview.project.movies.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import webview.project.movies.R;


public class AppConstants {

    public static final String API_KEY = "589e10387e0ca4ece633f5836fb0383f";
    public static final String API_URL = "https://api.themoviedb.org/3/";
    public static final String LANGUAJE_ES = "es";
    public static final Integer NOW_PLAYING_MOVIES = 0;
    public static final Integer POPULAR_MOVIES = 1;
    public static final Integer TOP_RATED_MOVIES = 2;
    public static final String PROCESS_REQUEST = "Processing request";
    public static final String LOADING = "Loading ...";
    public static final String CUSTOM_SETTINGS = "Custom your Settings Menu Option";
    public static final String BASE_POSTER_GRID_URL = "https://image.tmdb.org/t/p/w780";
    public static final String BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780";
    public static final String YOUTUBE_KEY = "AIzaSyDE18opuRo8CvyJqQf0AfiMBT_xwvTxaSE";

    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            return false;
        }
        return true;
    }

}
