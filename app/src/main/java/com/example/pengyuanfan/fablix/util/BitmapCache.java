package com.example.pengyuanfan.fablix.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class BitmapCache {
        Bitmap img;
        boolean first=true;
        boolean complete=false;
        public void showImg(ImageView v, String url){
            if(first) {
                new ImageDowloader(v,this).execute(url);
                first = false;
            }else if(complete){
                v.setImageBitmap(img);
            }
        }
        public  void showImgInLV(ImageView v, String url, PositionHolder ph, int position){
            if(first){
                new ListViewImageDLder(v, this, ph, position).execute(url);
                first = false;
            }else if(complete){
                v.setImageBitmap(img);
            }
        }
        public void setImg(Bitmap img) {
            this.img = img;
            complete=true;
        }
}
