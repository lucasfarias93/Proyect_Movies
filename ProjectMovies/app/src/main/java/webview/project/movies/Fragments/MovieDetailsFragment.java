package webview.project.movies.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

/**
 * Created by lfarias on 4/19/17.
 */
public class MovieDetailsFragment extends Fragment {

    private ImageView poster;
    private TextView title;
    private TextView overview;
    private TextView date;
    private TextView vote;
    int movie_id;
    private double movie_vote;

    FavoriteMoviesDatabase helper = new FavoriteMoviesDatabase(getActivity());

    public MovieDetailsFragment(){
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
        createView(v);
        return v;
    }

    public void actualizarFavoritos (){
        MovieData movie_favorite = new MovieData();
        movie_favorite.setTitle(getActivity().getIntent().getStringExtra("title"));
        movie_favorite.setBackdrop_path(getActivity().getIntent().getStringExtra("backdrop"));
        movie_favorite.setRelease_date(getActivity().getIntent().getStringExtra("date"));
        movie_favorite.setVote_average(movie_vote);
        movie_favorite.setOverview(getActivity().getIntent().getStringExtra("overview"));
        helper.onUpdate(movie_favorite,movie_id);
    }

    public void createView(View v){

        poster = (ImageView) v.findViewById(R.id.poster_view);
        title = (TextView) v.findViewById(R.id.title_movie);
        overview = (TextView) v.findViewById(R.id.overview);
        vote = (TextView) v.findViewById(R.id.vote);
        date = (TextView) v.findViewById(R.id.date);

        Bundle b = getActivity().getIntent().getExtras();
        movie_id = b.getInt("id");

        movie_vote = b.getDouble("vote");
        String vote_string = Double.toString(movie_id);
        title.setText(b.getString("title"));
        overview.setText(b.getString("overview"));
        date.setText("Release date: " + b.getString("date"));
        vote.setText("Vote Average: " + vote_string);

        String poster_path = b.getString("poster");

        Picasso.with(getActivity())
                .load(AppConstants.BASE_BACKDROP_URL + poster_path)
                .into(poster);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.float_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarFavoritos();
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
