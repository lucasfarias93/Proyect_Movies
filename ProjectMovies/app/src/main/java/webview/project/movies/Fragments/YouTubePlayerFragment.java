package webview.project.movies.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

/**
 * Created by lfarias on 4/19/17.
 */
public class YouTubePlayerFragment extends Fragment implements VideoMovieAsynkConnection.Callback{

    List<VideoDetailObject> videoDataList;
    private String myVideoDataKey;
    ProgressDialog progressDialog;
    private YouTubePlayerView playerView;
    int movie_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getActivity().getIntent().getExtras();
        movie_id = b.getInt("id");
        playerView = (YouTubePlayerView) getActivity().findViewById(R.id.youtube_player_view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final VideoMovieAsynkConnection videoMovieAsynkConnection = new VideoMovieAsynkConnection(this,getActivity(),movie_id);
        this.progressDialog = ProgressDialog.show(getActivity(), AppConstants.PROCESS_REQUEST, AppConstants.LOADING);
        videoMovieAsynkConnection.execute(AppConstants.API_KEY);
        return inflater.inflate(R.layout.youtube_fragment_layout, container, false);
    }


    @Override
    public String getVideoLinkCallback(Object video) {
        videoDataList = (List<VideoDetailObject>) video;
        for(VideoDetailObject myVideo : videoDataList){
            myVideoDataKey = myVideo.getKey();
        }
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        return myVideoDataKey;
    }
}
