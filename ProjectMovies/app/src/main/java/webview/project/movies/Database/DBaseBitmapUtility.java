package webview.project.movies.Database;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import webview.project.movies.Utils.AppConstants;

/**
 * Created by LUCAS on 20/04/2017.
 */
public class DBaseBitmapUtility {

    public static String imagePath;
    public static DBaseBitmapUtility instance = null;

    public DBaseBitmapUtility getInstance(){
        if(instance == null){
            instance = new DBaseBitmapUtility();
        }
        return instance;
    }
    //target to save image
    public static Target imageDownload(Context context, final String imageDir, final String imageName){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = imageName + "_" + timeStamp;
                        File storageDir = new File(Environment.getExternalStorageDirectory().toString(), imageDir);

                        OutputStream fOutputStream = null;
                        try {
                            if(!storageDir.exists()){
                                storageDir.mkdirs();

                            File image = File.createTempFile(
                                    imageFileName,  // prefix
                                    ".jpg",         // suffix
                                    storageDir      // directory
                            );
                            }
                            fOutputStream = new FileOutputStream(storageDir);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOutputStream);
                            fOutputStream.flush();
                            fOutputStream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }
    /*String path = (Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES) + File.separator + imageDir);
    //creating directory
    File file = new File(path);
    try {
        file.mkdirs();
        //creating file
        file = new File(path, imageName + ".jpeg");
        FileOutputStream ostream = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
        ostream.flush();
        ostream.close();*/
}

