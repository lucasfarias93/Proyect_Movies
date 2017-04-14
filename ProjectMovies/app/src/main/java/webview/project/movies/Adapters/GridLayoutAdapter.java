package webview.project.movies.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import webview.project.movies.Entities.MovieData;
import webview.project.movies.R;

/**
 * Created by LUCAS on 13/04/2017.
 */
public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ViewHolder> {
    public List<MovieData> myMovieDataList;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView moviePoster;

        public ViewHolder(View v) {
            super(v);
            moviePoster = (ImageView) v.findViewById(R.id.movie_image_id);
        }
    }

    public GridLayoutAdapter(Context context, List<MovieData> movieDataList) {
        myMovieDataList = movieDataList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_grid, null);
        ViewHolder viewHolder = new ViewHolder(recyclerLayoutView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (MovieData movie : myMovieDataList) {
            try {
                Bitmap imagePath = BitmapFactory.decodeStream((InputStream) new URL(movie.getPoster_path()).getContent());
                holder.moviePoster.setImageBitmap(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

