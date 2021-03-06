package webview.project.movies.Activities;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import webview.project.movies.Adapters.FavoriteMoviesAdapter;
import webview.project.movies.Adapters.GridLayoutAdapter;
import webview.project.movies.Clients.MoviesDataAsynkConnection;
import webview.project.movies.Database.FavoriteMoviesDatabase;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.PersistentMovieData;
import webview.project.movies.R;
import webview.project.movies.Utils.AppConstants;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MoviesDataAsynkConnection.Callback {

    private ProgressDialog progressDialog = null;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewFavs;
    private GridLayoutAdapter gridLayoutAdapter;
    private String page_num = "1";
    private GridLayoutManager gridLayoutManager;
    private GridLayoutManager gridLayoutManager2;
    private List<MovieData> myMovieData;
    private FavoriteMoviesAdapter favoriteMoviesAdapter;
    private List<PersistentMovieData> posters;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FavoriteMoviesDatabase helper = new FavoriteMoviesDatabase(this);
    LinearLayout error_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        error_layout = (LinearLayout) findViewById(R.id.error_background_layout);

        initView();

        if(AppConstants.isNetworkConnected(this)){
            error_layout.setVisibility(View.GONE);
            MoviesDataAsynkConnection moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, AppConstants.NOW_PLAYING_MOVIES);
            this.progressDialog = new ProgressDialog(this,R.style.AlertDialogCustom);
            progressDialog.setTitle(AppConstants.PROCESS_REQUEST);
            progressDialog.setMessage(AppConstants.LOADING);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);

        } else {
            error_layout.setVisibility(View.VISIBLE);
            loadFavoriteMovies();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        MoviesDataAsynkConnection moviesDataAsynkConnection;

        switch(id){
            case R.id.popular_movies:
                if(AppConstants.isNetworkConnected(this)){
                    error_layout.setVisibility(View.GONE);
                    if(recyclerViewFavs != null){
                        recyclerViewFavs.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, AppConstants.POPULAR_MOVIES);
                    this.progressDialog = new ProgressDialog(this,R.style.AlertDialogCustom);
                    progressDialog.setTitle(AppConstants.PROCESS_REQUEST);
                    progressDialog.setMessage(AppConstants.LOADING);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewFavs.setVisibility(View.GONE);
                    error_layout.setVisibility(View.VISIBLE);
                    crearDialogoConexion("Internet problems", "Please check your internet connection.");
                }
                break;

            case R.id.top_rated_movies:
                if(AppConstants.isNetworkConnected(this)){
                    error_layout.setVisibility(View.GONE);
                    if(recyclerViewFavs != null){
                        recyclerViewFavs.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, AppConstants.TOP_RATED_MOVIES);
                    this.progressDialog = new ProgressDialog(this,R.style.AlertDialogCustom);
                    progressDialog.setTitle(AppConstants.PROCESS_REQUEST);
                    progressDialog.setMessage(AppConstants.LOADING);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    recyclerViewFavs.setVisibility(View.GONE);
                    error_layout.setVisibility(View.VISIBLE);
                    crearDialogoConexion("Internet problems", "Please check your internet connection.");
                }
                break;

            case R.id.favorite_movies:
                if(!AppConstants.isNetworkConnected(this)) {
                    error_layout.setVisibility(View.GONE);
                    loadFavoriteMovies();
                }else {
                    crearDialogoConexion("FEATURE ERROR", "Feature avaliable only without internet connection");
                }
                break;

            case R.id.settings:
                Toast.makeText(MainActivity.this, AppConstants.CUSTOM_SETTINGS, Toast.LENGTH_LONG).show();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getMovieDataListCallback(Object movies) {
        myMovieData = (List<MovieData>) movies;
        gridLayoutAdapter = new GridLayoutAdapter(MainActivity.this, myMovieData);
        recyclerView.setAdapter(gridLayoutAdapter);
        if(this.progressDialog != null){
            this.progressDialog.dismiss();
        }
    }

    public void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManager2 = new GridLayoutManager(MainActivity.this, 2);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewFavs = (RecyclerView) findViewById(R.id.recyclerView_favs);
        recyclerViewFavs.setLayoutManager(gridLayoutManager2);
        recyclerViewFavs.setHasFixedSize(true);


    }

    public void loadFavoriteMovies(){
        posters = helper.getFavoriteMovies();
        if(!posters.isEmpty()){
            error_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            recyclerViewFavs.setVisibility(View.VISIBLE);
            favoriteMoviesAdapter = new FavoriteMoviesAdapter(MainActivity.this, posters);
            recyclerViewFavs.setAdapter(favoriteMoviesAdapter);
        }else {
            error_layout.setVisibility(View.VISIBLE);
            crearDialogoConexion("No Favorite movies were found", "Check your internet connection in order to add movies to Favorite List");
        }
    }

    private AlertDialog crearDialogoConexion(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.create();
        DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        alertDialogBuilder.setPositiveButton("OK", listenerOk);
        return alertDialogBuilder.show();
    }

}
