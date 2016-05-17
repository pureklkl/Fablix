package com.example.pengyuanfan.fablix.movieListAdapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.example.pengyuanfan.fablix.util.BitmapCache;
import com.example.pengyuanfan.fablix.util.PositionHolder;

/**
 * Created by pengyuanfan on 5/12/2016.
 */
public class MvItemBean {
    String imgSrc;
    String title;
    String content;
    String id;

    BitmapCache bitmapCache = new BitmapCache();

    public MvItemBean(String imgSrc, String title, String content, String id) {
        this.imgSrc = imgSrc;
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public void showImg(ImageView v, PositionHolder ph, int position){
        bitmapCache.showImgInLV(v, imgSrc, ph, position);
    }
}
