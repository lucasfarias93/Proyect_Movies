package webview.project.movies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import webview.project.movies.Entities.MovieData;

/**
 * Created by Elias on 18/04/2017.
 */

public class FavoriteMoviesDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FavoriteMovies.db";
    private static final String TABLE_NAME_DETAILS = "details";
    private static final String COLUMN_TITLE = "title";
    private static final String ID = "id";
    private static final String COLUMN_OVERVIEW = "overview";
    private static final String COLUMN_POSTER = "poster";
    private static final String COLUMN_RAITING = "raiting";
    private static final String COLUMN_DATE = "date";

    SQLiteDatabase db;

    private static final String TABLE_CREATE_DETAILS = "create table details (id integer primary key not null , " +
            "title text not null , overview text not null , poster text not null , raiting text not null , date text not null);";

    public FavoriteMoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DETAILS);
        this.db = db;
    }

    public void insertMovieData(MovieData mData) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = " select * from details ";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(ID, count);
        values.put(COLUMN_TITLE, mData.getTitle());
        values.put(COLUMN_OVERVIEW, mData.getOverview());
        values.put(COLUMN_POSTER, mData.getBackdrop_path());
        values.put(COLUMN_RAITING, mData.getPopularity());
        values.put(COLUMN_DATE, mData.getRelease_date());

        db.insert(TABLE_NAME_DETAILS, null, values);
        cursor.close();
        db.close();
    }

    public void onUpdate(MovieData mData, int movie) {
        db = this.getWritableDatabase();
        String query = "select id from " + TABLE_NAME_DETAILS;
        Cursor cursor = db.rawQuery(query, null);
        Integer a;
        int id = cursor.getCount();
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getInt(0);
                if (a.equals(movie)) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMN_TITLE, mData.getTitle());
                    values.put(COLUMN_OVERVIEW, mData.getOverview());
                    values.put(COLUMN_POSTER, mData.getBackdrop_path());
                    values.put(COLUMN_RAITING, mData.getPopularity());
                    values.put(COLUMN_DATE, mData.getRelease_date());

                    insertMovieData(mData);
                    db.close();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public MovieData getMovieData(Integer movieID) {
        MovieData movie = new MovieData();
        db = this.getReadableDatabase();
        String query = " select title , overview , poster , raiting , date from " + TABLE_NAME_DETAILS;
        Cursor cursor = db.rawQuery(query, null);
        String a;
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(3);
                if (a.equals(movieID)) {
                    movie.setTitle(cursor.getString(3));
                    movie.setOverview(cursor.getString(4));
                    movie.setBackdrop_path(cursor.getString(0));
                    movie.setPopularity(cursor.getDouble(1));
                    movie.setRelease_date(cursor.getString(2));
                }
            } while (cursor.moveToNext());
            cursor.close();
            return movie;
        }
        cursor.close();
        return null;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DETAILS);
        onCreate(db);
    }

}
