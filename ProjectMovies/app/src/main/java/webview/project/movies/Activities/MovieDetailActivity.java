package webview.project.movies.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class MovieDetailActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

    }
}
