package webview.project.movies.Adapters;

import android.content.Context;
import android.graphics.Typeface;
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
        private TextView author_header;
        private TextView comments_header;
        LinearLayout layoutItemList;

        public ListLayoutViewHolder(View v, Context context) {
            super(v);
            this.context = context;
            author = (TextView) v.findViewById(R.id.author);
            content = (TextView) v.findViewById(R.id.content);
            author_header = (TextView) v.findViewById(R.id.author_header);
            comments_header = (TextView) v.findViewById(R.id.comments_header);
            layoutItemList = (LinearLayout) v.findViewById(R.id.linear_layout_list);
            Typeface customFont = Typeface.createFromAsset(context.getAssets(),"fonts/Supersonic.ttf");
            Typeface customFont2 = Typeface.createFromAsset(context.getAssets(),"fonts/GiantRobotArmy-Medium.ttf");
            content.setTypeface(customFont2);
            author.setTypeface(customFont);
            author_header.setTypeface(customFont2);
            comments_header.setTypeface(customFont2);
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
        holder.author.setText(reviews.getAuthor());
        holder.content.setText(reviews.getContent());
    }
}
