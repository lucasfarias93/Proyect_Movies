package webview.project.movies.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import webview.project.movies.Activities.MovieDetailActivity;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.PersistentMovieData;
import webview.project.movies.R;


public class FavsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView moviePoster;
    RelativeLayout layoutItemGrid;
    public Context context;
    PersistentMovieData movie;

    private String original_title;
    private String overview;
    private String release_date;
    private Double vote_average;
    private String backdrop_path;
    private String poster_path;
    private Integer id;
    public FavsViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        moviePoster = (ImageView) v.findViewById(R.id.favorite_movies_poster);
        layoutItemGrid = (RelativeLayout) v.findViewById(R.id.relative_grid_layout_favs);
    }

    public void setOnClickListeners(PersistentMovieData movie) {
        layoutItemGrid.setOnClickListener(this);
        this.movie = movie;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, MovieDetailActivity.class);
        getMovieDetailItems(movie);
        i.putExtra("title", original_title);
        i.putExtra("overview", overview);
        i.putExtra("date", release_date);
        i.putExtra("vote", vote_average);
        i.putExtra("backdrop", backdrop_path);
        i.putExtra("id", id);
        i.putExtra("poster", poster_path);
        context.startActivity(i);

    }
    public void getMovieDetailItems(PersistentMovieData movie) {
        original_title = movie.getTitle();
        overview = movie.getOverview();
        release_date = movie.getRelease_date();
        vote_average = movie.getVote_average();
        backdrop_path = movie.getBackdrop_path();
        poster_path = movie.getPoster_path();
        id = movie.getId();
    }
}

