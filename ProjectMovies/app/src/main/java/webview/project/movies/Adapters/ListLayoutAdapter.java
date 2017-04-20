package webview.project.movies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import webview.project.movies.Entities.ReviewDetails;
import webview.project.movies.R;

/**
 * Created by LUCAS on 19/04/2017.
 */
public class ListLayoutAdapter extends RecyclerView.Adapter<ListLayoutAdapter.ListLayoutViewHolder> {
    public List<ReviewDetails> reviewDetailsList;
    public Context context;

    public ListLayoutAdapter(Context context, List<ReviewDetails> reviewDetailsList) {
        this.reviewDetailsList = reviewDetailsList;
        this.context = context;
    }

    public static class ListLayoutViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView author;
        private TextView content;
        private TextView url;
        private TextView reviewer_id;
        LinearLayout layoutItemList;

        public ListLayoutViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            author = (TextView) v.findViewById(R.id.author);
            content = (TextView) v.findViewById(R.id.content);
            url = (TextView) v.findViewById(R.id.url);
            reviewer_id = (TextView) v.findViewById(R.id.id);
            layoutItemList = (LinearLayout) v.findViewById(R.id.linear_layout_list);
        }
    }

        @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ListLayoutAdapter.ListLayoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View recyclerLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list, parent, false);
        ListLayoutViewHolder viewHolder = new ListLayoutViewHolder(recyclerLayoutView, context);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return reviewDetailsList.size();
    }

    @Override
    public void onBindViewHolder(ListLayoutAdapter.ListLayoutViewHolder holder, int position) {
        ReviewDetails reviews = reviewDetailsList.get(position);
        holder.reviewer_id.setText("REVIEWER ID: " + reviews.getId());
        holder.author.setText("AUTHOR: " + reviews.getAuthor());
        holder.content.setText("COMMENTS: " + reviews.getContent());
        holder.url.setText("URL: " + reviews.getUrl());
    }
}