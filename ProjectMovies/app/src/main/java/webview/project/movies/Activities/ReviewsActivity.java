package webview.project.movies.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import webview.project.movies.Adapters.GridLayoutAdapter;
import webview.project.movies.Adapters.ListLayoutAdapter;
import webview.project.movies.Clients.MoviesDataAsynkConnection;
import webview.project.movies.Clients.ReviewsMovieAsynkConnection;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.ReviewDetails;
import webview.project.movies.Entities.ReviewResult;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;

public class ReviewsActivity extends AppCompatActivity implements ReviewsMovieAsynkConnection.Callback {

    ProgressDialog progressDialog;
    private LinearLayoutManager listLayoutManager;
    private ListLayoutAdapter listAdapter;
    private RecyclerView recyclerView;
    private int movie_id;
    private List<ReviewDetails> reviewDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        Bundle b = getIntent().getExtras();
        movie_id = b.getInt("movie_id");

        initView();

        ReviewsMovieAsynkConnection reviewsMovieAsynkConnection = new ReviewsMovieAsynkConnection(this, this, movie_id);
        this.progressDialog = ProgressDialog.show(this,AppConstants.PROCESS_REQUEST, AppConstants.LOADING);
        reviewsMovieAsynkConnection.execute(AppConstants.API_KEY);
    }

    @Override
    public void getReviewsCallback(Object reviews) {
        reviewDetailsList = (List<ReviewDetails>) reviews;
        listAdapter = new ListLayoutAdapter(ReviewsActivity.this, reviewDetailsList);
        recyclerView.setAdapter(listAdapter);
        if(this.progressDialog != null){
            this.progressDialog.dismiss();
        }
    }

    public void initView() {

        listLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(listLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
