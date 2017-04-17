package webview.project.movies.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import webview.project.movies.MainActivity;
import webview.project.movies.R;

/**
 * Created by lfarias on 4/17/17.
 */
public class GridLayoutViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView moviePoster;
    RelativeLayout layoutItemGrid;
    public GridLayoutViewHolder(View v) {
        super(v);
        moviePoster = (ImageView) v.findViewById(R.id.movie_image_id);
        layoutItemGrid = (RelativeLayout) v.findViewById(R.id.relative_grid_layout);
    }

    public void setOnClickListeners() {
        layoutItemGrid.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "a,msdhjkashdkask", Toast.LENGTH_SHORT).show();
    }
}


