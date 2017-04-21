package webview.project.movies.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.Fragments.MovieDetailsFragment;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class MovieDetailActivity extends YouTubeBaseActivity{

    int movie_id;
    static YouTubePlayerFragment playerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle b = getIntent().getExtras();
        movie_id = b.getInt("id");

        //Initializing and adding fragments

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft;
        String player_tag = YouTubePlayerFragment.class.getSimpleName();
        String movie_tag = MovieDetailsFragment.class.getSimpleName();
        playerFragment = (YouTubePlayerFragment) fm.findFragmentByTag(player_tag);
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) fm.findFragmentByTag(movie_tag);
        ft = fm.beginTransaction();
        if (playerFragment == null) {
            playerFragment = new YouTubePlayerFragment();
            ft.add(R.id.content_youtube, playerFragment, player_tag);
        }
        if (movieDetailsFragment == null) {
            movieDetailsFragment = new MovieDetailsFragment();
            ft.add(R.id.content_description, movieDetailsFragment, movie_tag);
        }

        ft.commit();
    }

    public static class YouTubePlayerFragment extends Fragment implements YouTubePlayer.OnInitializedListener, VideoMovieAsynkConnection.Callback {

        List<VideoDetailObject> videoDataList;
        private YouTubePlayerView playerView;
        private String myVideoDataKey;
        int movie_id;
        static YouTubePlayer youTubePlayer;
        ProgressDialog progressDialog;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            final VideoMovieAsynkConnection videoMovieAsynkConnection = new VideoMovieAsynkConnection(this, getActivity(), movie_id);
            this.progressDialog = new ProgressDialog(getActivity(),R.style.AlertDialogCustom);
            progressDialog.setTitle(AppConstants.PROCESS_REQUEST);
            progressDialog.setMessage(AppConstants.LOADING);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            videoMovieAsynkConnection.execute(AppConstants.API_KEY);

        }

        public YouTubePlayerFragment() {
            super();
        }

        @Override
        public void onDestroy() {
            if (youTubePlayer != null) {
                youTubePlayer.release();
            }
            super.onDestroy();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle b = getActivity().getIntent().getExtras();
            movie_id = b.getInt("id");
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.youtube_fragment_layout, container, false);
            playerView = (YouTubePlayerView) v.findViewById(R.id.youtube_player_view);
            return v;
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            this.youTubePlayer = youTubePlayer;
            youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
            youTubePlayer.cueVideo(myVideoDataKey);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }

        @Override
        public void getVideoLinkCallback(Object video) {
            videoDataList = (List<VideoDetailObject>) video;
            for (VideoDetailObject myVideo : videoDataList) {
                myVideoDataKey = myVideo.getKey();
            }
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            playerView.initialize(AppConstants.YOUTUBE_KEY, this);
        }
    }
}
