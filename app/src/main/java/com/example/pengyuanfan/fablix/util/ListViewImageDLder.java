package com.example.pengyuanfan.fablix.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class ListViewImageDLder extends ImageDowloader {
    int position;
    PositionHolder ph;
    public ListViewImageDLder(ImageView show, BitmapCache img, PositionHolder ph, int position){
        super(show, img);
        this.position=position;
        this.ph=ph;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(position==ph.getP())
            show.setImageBitmap(bitmap);
        if(img!=null)
            img.setImg(bitmap);
    }
}
