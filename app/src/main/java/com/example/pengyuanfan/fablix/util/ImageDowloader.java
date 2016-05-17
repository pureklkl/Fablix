package com.example.pengyuanfan.fablix.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by pengyuanfan on 5/12/2016.
 */
public class ImageDowloader extends AsyncTask<String, Void, Bitmap> {

    protected ImageView show;
    protected BitmapCache img=null;
    public ImageDowloader(ImageView show, BitmapCache img) {this.show=show; this.img=img;}
    public ImageDowloader(ImageView show) {
        this.show = show;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String imageUrl = url[0];
        Bitmap img = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            img = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        return img;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null)
            show.setImageBitmap(bitmap);
        if(img!=null)
            img.setImg(bitmap);
    }
}
