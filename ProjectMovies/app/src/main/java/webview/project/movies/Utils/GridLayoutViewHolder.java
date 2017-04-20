package webview.project.movies.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import webview.project.movies.Activities.MovieDetailActivity;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.R;

/**
 * Created by lfarias on 4/17/17.
 */
public class GridLayoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView moviePoster;
    RelativeLayout layoutItemGrid;
    public Context context;
    MovieData movie;
    private String original_title;
    private String overview;
    private String release_date;
    private Double vote_average;
    private String backdrop_path;
    private String poster_path;
    private Integer id;

    public GridLayoutViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        moviePoster = (ImageView) v.findViewById(R.id.movie_image_id);
        layoutItemGrid = (RelativeLayout) v.findViewById(R.id.relative_grid_layout);
    }

    public void setOnClickListeners(MovieData movie) {
        layoutItemGrid.setOnClickListener(this);
        this.movie  = movie;
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, MovieDetailActivity.class);
        getMovieDetailItems(movie);
        i.putExtra("title",original_title);
        i.putExtra("overview", overview);
        i.putExtra("date", release_date);
        i.putExtra("vote", vote_average);
        i.putExtra("backdrop", backdrop_path);
        i.putExtra("id", id);
        i.putExtra("poster", poster_path);
        context.startActivity(i);
    }

    public void getMovieDetailItems(MovieData movie){
        original_title = movie.getOriginal_title();
        overview = movie.getOverview();
        release_date = movie.getRelease_date();
        vote_average = movie.getVote_average();
        backdrop_path = movie.getBackdrop_path();
        poster_path = movie.getPoster_path();
        id = movie.getId();
    }
}


