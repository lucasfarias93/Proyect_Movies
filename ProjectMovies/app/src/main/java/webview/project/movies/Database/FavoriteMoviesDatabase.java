package webview.project.movies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import webview.project.movies.Entities.MovieData;
import webview.project.movies.Entities.PersistentMovieData;

/**
 * Created by Elias on 18/04/2017.
 */

public class FavoriteMoviesDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteMovies.db";
    private static final String TABLE_NAME_DETAILS = "details";
    private static final String COLUMN_TITLE = "title";
    private static final String ID = "id";
    private static final String ID_MOVIE = "idmovie";
    private static final String COLUMN_OVERVIEW = "overview";
    //private static final String COLUMN_POSTER = "poster";
    private static final String COLUMN_VOTE = "vote";
    private static final String COLUMN_DATE = "date";
    //private static final String COLUMN_MAIN_POSTER = "posterMain";
    SQLiteDatabase db;

    private static final String TABLE_CREATE_DETAILS = "create table details (id integer primary key not null , " +
            "idmovie text not null , title text not null , overview text not null , vote text key not null , date text not null);";

    public FavoriteMoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DETAILS);
        this.db = db;
    }

    public void insertMovieData(PersistentMovieData mData) {
        db = this.getWritableDatabase();
        //String posterPath = new String(mData.getPoster_path());
        //String backdropPath = new String(mData.getBackdrop_path());
        String voteCount = Double.toString(mData.getVote_average());
        ContentValues values = new ContentValues();
        String query = " select * from details ";
        Cursor cursor = db.rawQuery(query, null);

        int count = cursor.getCount();

        values.put(ID, count);
        values.put(ID_MOVIE,mData.getId());
        values.put(COLUMN_TITLE, mData.getTitle());
        values.put(COLUMN_OVERVIEW, mData.getOverview());
        //values.put(COLUMN_POSTER, backdropPath);
        values.put(COLUMN_VOTE, voteCount);
        values.put(COLUMN_DATE, mData.getRelease_date());
        //values.put(COLUMN_MAIN_POSTER, posterPath);

        db.insert(TABLE_NAME_DETAILS, null , values);
        cursor.close();
        db.close();
    }

    public MovieData getMovieData(int movieID) {
        MovieData movie = new MovieData();
        db = this.getReadableDatabase();
        String query = " select idMovie , title , overview, vote , date from " + TABLE_NAME_DETAILS;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(movieID)) {
                    movie.setId(cursor.getInt(0));
                    movie.setTitle(cursor.getString(3));
                    movie.setOverview(cursor.getString(4));
                    //movie.setBackdrop_path(cursor.getString(0));
                    movie.setVote_average(cursor.getDouble(1));
                    movie.setRelease_date(cursor.getString(2));
                    //movie.setPoster_path(cursor.getString(5));
                }
            } while (cursor.moveToNext());
            cursor.close();
            return movie;
        }
        cursor.close();
        return null;
    }

    public List<PersistentMovieData> getFavoriteMovies() {
       List <PersistentMovieData> movies = new ArrayList<>();
        db = this.getReadableDatabase();
        String query = " select * from " + TABLE_NAME_DETAILS;
        Cursor cursor = db.rawQuery(query, null);
        if( cursor != null && cursor.moveToFirst() ) {
            do{
                PersistentMovieData movieObject = new PersistentMovieData();
                movieObject.setId(cursor.getInt(0));
                movieObject.setTitle(cursor.getString(1));
                movieObject.setOverview(cursor.getString(2));
                //movieObject.setBackdrop_path(cursor.getString(3).getBytes());
                movieObject.setVote_average(cursor.getDouble(3));
                movieObject.setRelease_date(cursor.getString(4));
                //movieObject.setPoster_path(cursor.getString(6).getBytes());
                movies.add(movieObject);
            }  while (cursor.moveToNext());
            cursor.close();
        }
        return movies;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DETAILS);
        onCreate(db);
    }

}
