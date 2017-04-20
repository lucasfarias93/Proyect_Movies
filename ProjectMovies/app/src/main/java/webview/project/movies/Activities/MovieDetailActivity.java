package webview.project.movies.Activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.io.File;
import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.Fragments.MovieDetailsFragment;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class MovieDetailActivity extends YouTubeBaseActivity implements VideoMovieAsynkConnection.Callback {

    List<VideoDetailObject> videoDataList;
    private String myVideoDataKey;
    ProgressDialog progressDialog;
    int movie_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle b = getIntent().getExtras();
        movie_id = b.getInt("id");

        //Initializing and adding YouTubePlayerFragment

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        String player_tag = YouTubePlayerFragment.class.getSimpleName();
        String movie_tag = MovieDetailsFragment.class.getSimpleName();
        YouTubePlayerFragment playerFragment = (YouTubePlayerFragment) fm.findFragmentByTag(player_tag);
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) fm.findFragmentByTag(movie_tag);
        ft = fm.beginTransaction();
        if (playerFragment == null) {
            playerFragment = YouTubePlayerFragment.newInstance();
            ft.add(R.id.content_youtube, playerFragment, player_tag);
        }
        if (movieDetailsFragment == null) {
            movieDetailsFragment = new MovieDetailsFragment();
            ft.add(R.id.content_description, movieDetailsFragment, movie_tag);
        }

        ft.commit();


        final VideoMovieAsynkConnection videoMovieAsynkConnection = new VideoMovieAsynkConnection(this, this, movie_id);
        this.progressDialog = ProgressDialog.show(this, AppConstants.PROCESS_REQUEST, AppConstants.LOADING);
        videoMovieAsynkConnection.execute(AppConstants.API_KEY);

        playerFragment.initialize(AppConstants.YOUTUBE_KEY, new YouTubePlayer.OnInitializedListener()

                {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer
                            youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(myVideoDataKey);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider
                                                                provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Toast.makeText(MovieDetailActivity.this, "Error while initializing YouTubePlayer.", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }

    @Override
    public String getVideoLinkCallback(Object video) {
        videoDataList = (List<VideoDetailObject>) video;
        for (VideoDetailObject myVideo : videoDataList) {
            myVideoDataKey = myVideo.getKey();
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        return myVideoDataKey;
    }

}
