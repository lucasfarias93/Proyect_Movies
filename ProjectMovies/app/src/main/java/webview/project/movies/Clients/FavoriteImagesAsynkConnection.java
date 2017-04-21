package webview.project.movies.Clients;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by LUCAS on 21/04/2017.
 */
public class FavoriteImagesAsynkConnection extends AsyncTask<String, Void, ArrayList<Bitmap>> {

    Callback callback;
    Bitmap bmp_poster;
    Bitmap bmp_backdrop;
    String movie_name;
    Context context;
    String imagePosterPath = null;
    String imageBackdropPath = null;
    ArrayList<Bitmap> images;

    public interface Callback {
        String getImagePath(ArrayList<String> imagePaths);
    }

    public FavoriteImagesAsynkConnection(Callback callback, Context context){
        this.callback = callback;
        this.context = context;
    }
    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {
        movie_name = params[2];
        images = new ArrayList<>();
        try {
            URL url_poster = new URL(params[0]);
            HttpURLConnection connection_1 = (HttpURLConnection) url_poster.openConnection();
            connection_1.setDoInput(true);
            connection_1.connect();
            InputStream input_1 = connection_1.getInputStream();
            bmp_poster = BitmapFactory.decodeStream(input_1);
            images.add(bmp_poster);
            connection_1.disconnect();

            URL url_backdrop = new URL(params[1]);
            HttpURLConnection connection_2 = (HttpURLConnection) url_backdrop.openConnection();
            connection_2.setDoInput(true);
            connection_2.connect();
            InputStream input_2 = connection_2.getInputStream();
            bmp_backdrop = BitmapFactory.decodeStream(input_2);
            images.add(bmp_backdrop);
            connection_2.disconnect();

        }catch(Exception e){
            e.printStackTrace();
        }

        return images;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> bitmap) {
        Bitmap poster_bitmap = bitmap.get(0);
        Bitmap backdrop_bitmap = bitmap.get(1);
        File sd = Environment.getExternalStorageDirectory();
        File dest_poster = new File(sd, movie_name + "_poster");
        File dest_backdrop = new File(sd, movie_name + "_backdrop");
        ArrayList<String> imagePaths = new ArrayList<>();

        try {
            FileOutputStream out_1 = new FileOutputStream(dest_poster);
            poster_bitmap.compress(Bitmap.CompressFormat.PNG, 90, out_1);
            out_1.flush();
            out_1.close();
            imagePosterPath = dest_poster.getAbsolutePath();
            imagePaths.add(imagePosterPath);

            FileOutputStream out_2 = new FileOutputStream(dest_backdrop);
            backdrop_bitmap.compress(Bitmap.CompressFormat.PNG, 90, out_2);
            out_2.flush();
            out_2.close();
            imageBackdropPath = dest_backdrop.getAbsolutePath();
            imagePaths.add(imageBackdropPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        callback.getImagePath(imagePaths);
    }
}
