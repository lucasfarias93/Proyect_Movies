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

import com.squareup.picasso.Picasso;

import java.util.List;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.VideoDetailObject;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class MovieDetailActivity extends AppCompatActivity implements VideoMovieAsynkConnection.Callback {

    private TextView title_text;
    private TextView overview;
    private TextView date;
    private TextView vote;
    private ImageView imageView;
    private Button addFav;
    ProgressDialog progressDialog;
    private List<VideoDetailObject> videoDataList;
    private String myVideoDataKey;
    private VideoView trailer;
    private int position = 0;
    private double movie_vote;
    int movie_id ;
    FavoriteMoviesDatabase helper = new FavoriteMoviesDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        title_text = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.backdrop);
        overview = (TextView) findViewById(R.id.overview);
        date = (TextView)findViewById(R.id.date);
        vote = (TextView)findViewById(R.id.review);
        addFav = (Button) findViewById(R.id.addFav);
        Bundle b = this.getIntent().getExtras();
        movie_id = b.getInt("id");

         movie_vote = b.getDouble("vote");
         String vote_string = Double.toString(movie_id);

        final VideoMovieAsynkConnection videoMovieAsynkConnection = new VideoMovieAsynkConnection(this,this,movie_id);
        this.progressDialog = ProgressDialog.show(this,AppConstants.PROCESS_REQUEST, AppConstants.LOADING);
        videoMovieAsynkConnection.execute(AppConstants.API_KEY);


        title_text.setText(getIntent().getStringExtra("title"));
        overview.setText(getIntent().getStringExtra("overview"));
        vote.setText(vote_string);
        date.setText(getIntent().getStringExtra("date"));
        Picasso.with(this)
                .load(AppConstants.BASE_POSTER_URL + getIntent().getStringExtra("backdrop"))
                .into(imageView);

        addFav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarFavoritos(v);
            }
        });

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


    public void actualizarFavoritos (View view){
      MovieData movie_favorite = new MovieData();
        movie_favorite.setTitle(getIntent().getStringExtra("title"));
        movie_favorite.setBackdrop_path(getIntent().getStringExtra("backdrop"));
        movie_favorite.setRelease_date(getIntent().getStringExtra("date"));
        movie_favorite.setVote_average(movie_vote);
        movie_favorite.setOverview(getIntent().getStringExtra("overview"));
        helper.onUpdate(movie_favorite,movie_id);
    }
}
