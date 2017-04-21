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

/**
 * Created by Elias on 20/04/2017.
 */

public class FavsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView moviePoster;
    RelativeLayout layoutItemGrid;
    public Context context;
    PersistentMovieData movie;


    public FavsViewHolder(View v, Context context) {
        super(v);
        this.context = context;
        moviePoster = (ImageView) v.findViewById(R.id.favorite_movies_poster);
        layoutItemGrid = (RelativeLayout) v.findViewById(R.id.relative_grid_layout_favs);
    }

    public void setOnClickListeners(PersistentMovieData movie) {
        layoutItemGrid.setOnClickListener(this);
        this.movie  = movie;
    }
    @Override
    public void onClick(View view) {
        Intent i = new Intent(context, MovieDetailActivity.class);
        int id = movie.getId();
        i.putExtra("id", id);

        context.startActivity(i);
    }
}

