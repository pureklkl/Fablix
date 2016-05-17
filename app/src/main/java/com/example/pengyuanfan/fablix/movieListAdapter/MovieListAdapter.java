package com.example.pengyuanfan.fablix.movieListAdapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pengyuanfan.fablix.R;
import com.example.pengyuanfan.fablix.util.ImageDowloader;
import com.example.pengyuanfan.fablix.util.PositionHolder;

import java.io.File;
import java.util.List;

/**
 * Created by pengyuanfan on 5/12/2016.
 */
public class MovieListAdapter extends BaseAdapter {

    private List<MvItemBean> mvList;
    private LayoutInflater mvItemInf;

    public MovieListAdapter(Context context, List<MvItemBean> mvList) {
        this.mvList = mvList;
        mvItemInf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mvList.size();
    }

    @Override
    public Object getItem(int position) {
        return mvList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        Log.d("d",Integer.toString(position));
        if(convertView == null){
            Log.d("b",Integer.toString(position));
            convertView = mvItemInf.inflate(R.layout.movie_item, null);
            vh = new ViewHolder();
            vh.setImgSrc((ImageView) convertView.findViewById(R.id.mv_img));
            vh.setTitle((TextView)convertView.findViewById(R.id.mv_title));
            vh.setContent((TextView)convertView.findViewById(R.id.mv_content));
            vh.setPosition(new PositionHolder(position));
            convertView.setTag(vh);
        }else{
             vh =  (ViewHolder) convertView.getTag();
        }

        vh.getPosition().setP(position);
        mvList.get(position).showImg(vh.getImgSrc(), vh.getPosition(), position);
        vh.getTitle().setText(mvList.get(position).getTitle());
        vh.getContent().setText(mvList.get(position).getContent());

        return convertView;
    }

    class ViewHolder{

        ImageView imgSrc;
        TextView title;
        TextView content;

        PositionHolder position;

        public PositionHolder getPosition() {
            return position;
        }

        public ImageView getImgSrc() {
            return imgSrc;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getContent() {
            return content;
        }

        public void setImgSrc(ImageView imgSrc) {
            this.imgSrc = imgSrc;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public void setContent(TextView content) {
            this.content = content;
        }

        public void setPosition(PositionHolder position) {
            this.position = position;
        }
    }

    public static int viewHolderTag = 0;
    public static int positionHolderTag = 1;
}
