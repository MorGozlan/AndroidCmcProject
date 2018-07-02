package com.morgozlan.cryptoproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mor Gozlan on 09/04/2018.
 */

public class ImageLoadingTask extends AsyncTask <String, Void, Bitmap> {

    private final static Map <String, Bitmap> flyweightImages = new HashMap();//empty by default

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            return BitmapFactory.decodeStream(new URL(urls[0]).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void loadImage(final String url, final ImageView img){
        //TODO - no image...
        //img.setImageBitmap(NOIMAGE);
        Bitmap bm = flyweightImages.get(url);
        if(bm == null){//if not exist - load
            new ImageLoadingTask(){
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    img.setImageBitmap(bitmap);
                    flyweightImages.put(url, bitmap);
                }
            }.execute(url);
        }else{//exists - reuse
            img.setImageBitmap(bm);
        }
    }

}
