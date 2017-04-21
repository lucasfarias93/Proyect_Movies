package webview.project.movies.Adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import webview.project.movies.Entities.PersistentMovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.FavsViewHolder;

public class FavoriteMoviesAdapter extends RecyclerView.Adapter <FavsViewHolder>{

    public List<PersistentMovieData> posters;
    public Context context;

    public FavoriteMoviesAdapter(Context context ,List <PersistentMovieData> items) {
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
        PersistentMovieData movie = posters.get(position);
        File imgFile = new File(movie.getPoster_path());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.moviePoster.setImageBitmap(myBitmap);

            holder.setOnClickListeners(movie);
        }
    }

    @Override
    public int getItemCount() {
        return posters.size();
    }
}
