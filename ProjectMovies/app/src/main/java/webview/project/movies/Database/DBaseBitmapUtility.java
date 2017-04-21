package webview.project.movies.Database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by LUCAS on 20/04/2017.
 */
public class DBaseBitmapUtility {

    public static DBaseBitmapUtility instance = null;

    public DBaseBitmapUtility getInstance(){
        if(instance == null){
            instance = new DBaseBitmapUtility();
        }
        return instance;
    }

        // convert from bitmap to byte array
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);}

}

