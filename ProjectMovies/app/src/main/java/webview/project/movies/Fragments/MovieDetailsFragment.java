package webview.project.movies.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.ArrayList;

import webview.project.movies.Activities.ReviewsActivity;
import webview.project.movies.Clients.FavoriteImagesAsynkConnection;
import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.PersistentMovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;


public class MovieDetailsFragment extends Fragment implements FavoriteImagesAsynkConnection.Callback{

    private ImageView backdrop;
    private TextView title;
    private TextView overview;
    private TextView date;
    private TextView vote;
    private Button reviews;
    String poster_path;
    String final_backdrop_path;
    int movie_id;
    PersistentMovieData movie_favorite;
    private double movie_vote;
    String vote_string;
    Context context;
    private FloatingActionButton fab;
    FavoriteMoviesDatabase helper;
    Bundle b;

    public MovieDetailsFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment_layout, container, false);
        b = getActivity().getIntent().getExtras();
        createView(v);
        return v;
    }

    public void actualizarFavoritos() {
    String movie_title = getActivity().getIntent().getStringExtra("title");
        FavoriteImagesAsynkConnection favoriteImagesAsynkConnection = new FavoriteImagesAsynkConnection(this, context);
        favoriteImagesAsynkConnection.execute((AppConstants.BASE_POSTER_GRID_URL + b.getString("poster")),(AppConstants.BASE_BACKDROP_URL + b.getString("backdrop")), movie_title);

    }

    public void createView(View v) {
        backdrop = (ImageView) v.findViewById(R.id.backdrop_view);
        title = (TextView) v.findViewById(R.id.title_movie);
        overview = (TextView) v.findViewById(R.id.overview);
        vote = (TextView) v.findViewById(R.id.vote);
        date = (TextView) v.findViewById(R.id.date);
        reviews = (Button) v.findViewById(R.id.link_reviews);

        Typeface customFont2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AngularRounded.ttf");
        Typeface customFont3 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Heroes.ttf");

        title.setTypeface(customFont3);
        date.setTypeface(customFont3);
        vote.setTypeface(customFont3);

        movie_id = b.getInt("id");

        movie_vote = b.getDouble("vote");
        vote_string = Double.toString(movie_vote);
        if (AppConstants.isNetworkConnected(getActivity())) {

            title.setText(b.getString("title"));
            overview.setText(b.getString("overview"));
            date.setText("Release date: " + b.getString("date"));
            vote.setText("Vote Average: " + vote_string);

            String backdrop_path = b.getString("backdrop");

            Picasso.with(getActivity())
                    .load(AppConstants.BASE_BACKDROP_URL + backdrop_path)
                    .into(backdrop);

            reviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), ReviewsActivity.class);
                    i.putExtra("movie_id", movie_id);
                    startActivity(i);
                }
            });
            fab = (FloatingActionButton) v.findViewById(R.id.float_button);

        } else {

            title.setText(b.getString("title"));
            overview.setText(b.getString("overview"));
            date.setText("Release date: " + b.getString("date"));
            vote.setText("Vote Average: " + vote_string);

            String backdrop_path = b.getString("backdrop");

            File imgFile = new File(backdrop_path);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                backdrop.setImageBitmap(myBitmap);
            }
            fab = (FloatingActionButton) v.findViewById(R.id.float_button);
            fab.setVisibility(View.GONE);
            reviews.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movie_favorite = new PersistentMovieData();
        context = getActivity();
        helper = new FavoriteMoviesDatabase(context);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarFavoritos();
            }
        });
    }

    @Override
    public String getImagePath(ArrayList<String> imagePath) {
        poster_path = imagePath.get(0);
        final_backdrop_path = imagePath.get(1);
        movie_favorite.setId(movie_id);
        movie_favorite.setTitle(getActivity().getIntent().getStringExtra("title"));
        movie_favorite.setRelease_date(getActivity().getIntent().getStringExtra("date"));
        movie_favorite.setVote_average(movie_vote);
        movie_favorite.setPoster_path(poster_path);
        movie_favorite.setBackdrop_path(final_backdrop_path);
        movie_favorite.setOverview(getActivity().getIntent().getStringExtra("overview"));
        helper.insertMovieData(movie_favorite);

        Toast.makeText(getActivity(),"Pelicula Guardada en Favoritos!!",Toast.LENGTH_LONG).show();
        return null;
    }
}
