package zappos.abhishek.ilovezapposapp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Abhishek on 2/5/2017.
 */

public class ImageLoadTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... urls) {

        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

