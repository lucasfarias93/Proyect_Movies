package webview.project.movies;


import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;

import webview.project.movies.Adapters.GridLayoutAdapter;
import webview.project.movies.Clients.MoviesDataAsynkConnection;
import webview.project.movies.Entities.MovieData;
import webview.project.movies.Utils.AppConstants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MoviesDataAsynkConnection.Callback {

    private RecyclerView recyclerView;
    private GridLayoutAdapter gridLayoutAdapter;
    private String page_num = "1";
    private GridLayoutManager gridLayoutManager;
    private List<MovieData> myMovieData;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        MoviesDataAsynkConnection moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, 0);
        moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
                moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, 1);
                moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);
                break;

            case R.id.top_rated_movies:
                moviesDataAsynkConnection = new MoviesDataAsynkConnection(this, this, 2);
                moviesDataAsynkConnection.execute(AppConstants.API_KEY, AppConstants.LANGUAJE_ES, page_num);
                break;

            case R.id.favorite_movies:
                break;

            case R.id.settings:
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

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }
}
