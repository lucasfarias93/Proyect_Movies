package webview.project.movies.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class MovieDetailActivity extends AppCompatActivity implements VideoMovieAsynkConnection.Callback {

    private TextView title_text;
    private TextView overview;
    private ImageView imageView;
    ProgressDialog progressDialog;
    private List<VideoDetailObject> videoDataList;
    private String myVideoDataKey;
    private VideoView trailer;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title_text = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.backdrop);
        overview = (TextView) findViewById(R.id.overview);

        Bundle b = this.getIntent().getExtras();
        int movie_id = b.getInt("id");

        final VideoMovieAsynkConnection videoMovieAsynkConnection = new VideoMovieAsynkConnection(this,this,movie_id);
        this.progressDialog = ProgressDialog.show(this,AppConstants.PROCESS_REQUEST, AppConstants.LOADING);
        videoMovieAsynkConnection.execute(AppConstants.API_KEY);

        setupVideoView();

        title_text.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));

        Picasso.with(this)
                .load(AppConstants.BASE_POSTER_URL + getIntent().getStringExtra("backdrop"))
                .into(imageView);

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

    public void setupVideoView(){
        String uriPath = AppConstants.BASE_VIDEO_URL + myVideoDataKey;
        Uri uri = Uri.parse(uriPath);
        trailer = (VideoView) findViewById(R.id.trailer);
        trailer.setOnPreparedListener(createdOnPreparedListener());
        MediaController mediaController = new MediaController(this);
        trailer.setMediaController(mediaController);
        try{
            trailer.setVideoURI(uri);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Position", trailer.getCurrentPosition());
        trailer.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        trailer.seekTo(position);
    }

    private MediaPlayer.OnPreparedListener createdOnPreparedListener() {
        return new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        };
    }
}
