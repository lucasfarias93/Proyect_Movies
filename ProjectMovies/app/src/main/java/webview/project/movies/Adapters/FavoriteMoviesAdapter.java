package webview.project.movies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.FavsViewHolder;

/**
 * Created by Elias on 20/04/2017.
 */

public class FavoriteMoviesAdapter extends RecyclerView.Adapter <FavsViewHolder>{

    final List<MovieData> posters;
    public Context context;

    public FavoriteMoviesAdapter(Context context ,List <MovieData> items) {
        this.context = context;
        this.posters = items;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FavsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_movies_layout, parent, false);
        FavsViewHolder viewHolder = new FavsViewHolder(recyclerLayoutView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavsViewHolder holder, int position) {

        MovieData movie = posters.get(position);
        Picasso.with(context)
                .load(movie.getPoster_path())
                .into(holder.moviePoster);

        holder.setOnClickListeners(movie);
    }

    @Override
    public int getItemCount() {
        return posters.size();
    }
}
