package webview.project.movies.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import webview.project.movies.Activities.ReviewsActivity;
import webview.project.movies.Database.DBaseBitmapUtility;
import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.PersistentMovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

import static webview.project.movies.Database.DBaseBitmapUtility.imageDownload;

/**
 * Created by lfarias on 4/19/17.
 */
public class MovieDetailsFragment extends Fragment {

    private ImageView backdrop;
    private TextView title;
    private TextView overview;
    private TextView date;
    private TextView vote;
    private TextView reviews;
    int movie_id;
    private double movie_vote;
    String vote_string;
    Context context;
    private FloatingActionButton fab;
    FavoriteMoviesDatabase helper;
    MovieData movieData;
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
        PersistentMovieData movie_favorite = new PersistentMovieData();
        String movie_title = (getActivity().getIntent().getStringExtra("title"));

        Picasso.with(context)
                .load(AppConstants.BASE_BACKDROP_URL + b.getString("poster"))
                .into((DBaseBitmapUtility.imageDownload(context, "poster_dir", movie_title + "/poster.jpeg")));

        Picasso.with(context)
                .load(AppConstants.BASE_BACKDROP_URL + b.getString("backdrop"))
                .into((DBaseBitmapUtility.imageDownload(context, "backdrop_dir", movie_title + "/backdrop.jpeg")));


        movie_favorite.setId(movie_id);
        movie_favorite.setTitle(getActivity().getIntent().getStringExtra("title"));
        movie_favorite.setRelease_date(getActivity().getIntent().getStringExtra("date"));
        movie_favorite.setVote_average(movie_vote);
        movie_favorite.setOverview(getActivity().getIntent().getStringExtra("overview"));
        helper.insertMovieData(movie_favorite);
    }

    public MovieData getMovieData(int MovieID) {
        movieData = helper.getMovieData(MovieID);
        return movieData;
    }

    public void createView(View v) {
        backdrop = (ImageView) v.findViewById(R.id.backdrop_view);
        title = (TextView) v.findViewById(R.id.title_movie);
        overview = (TextView) v.findViewById(R.id.overview);
        vote = (TextView) v.findViewById(R.id.vote);
        date = (TextView) v.findViewById(R.id.date);
        reviews = (TextView) v.findViewById(R.id.link_reviews);

        Typeface customFont2 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/AngularRounded.ttf");
        Typeface customFont3 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Heroes.ttf");

        title.setTypeface(customFont3);
        date.setTypeface(customFont2);

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
            /*getMovieData(movie_id);
            title.setText(movieData.getTitle());
            overview.setText(movieData.getOverview());
            date.setText(movieData.getRelease_date());
            Picasso.with(getActivity())
                    .load(movieData.getBackdrop_path())
                    .into(backdrop);*/
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        helper = new FavoriteMoviesDatabase(context);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarFavoritos();
            }
        });
    }
}
