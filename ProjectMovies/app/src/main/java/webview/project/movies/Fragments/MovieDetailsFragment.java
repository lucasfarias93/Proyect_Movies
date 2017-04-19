package webview.project.movies.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import webview.project.movies.Clients.VideoMovieAsynkConnection;
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
    private Button addFav;
    int movie_id;
    private double movie_vote;

    FavoriteMoviesDatabase helper = new FavoriteMoviesDatabase(getActivity());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        poster = (ImageView) getActivity().findViewById(R.id.poster_view);
        title = (TextView) getActivity().findViewById(R.id.title_movie);
        overview = (TextView) getActivity().findViewById(R.id.overview);
        vote = (TextView) getActivity().findViewById(R.id.vote);
        date = (TextView) getActivity().findViewById(R.id.date);
        addFav = (Button) getActivity().findViewById(R.id.favoritos);


        Bundle b = getActivity().getIntent().getExtras();
        movie_id = b.getInt("id");

        movie_vote = b.getDouble("vote");
        String vote_string = Double.toString(movie_id);

        vote.setText(vote_string);

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarFavoritos();
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_fragment_layout, container, false);
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


}
