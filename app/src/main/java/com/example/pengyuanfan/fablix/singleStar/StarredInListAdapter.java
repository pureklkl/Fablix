package com.example.pengyuanfan.fablix.singleStar;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pengyuanfan.fablix.json.Movie;

import java.util.List;

/**
 * Created by pengyuanfan on 5/15/2016.
 */
public class StarredInListAdapter extends BaseAdapter {

    List<Movie> movies;

    public StarredInListAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            TextView tmp = new TextView(parent.getContext());
            tmp.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tmp.setGravity(Gravity.CENTER);
            convertView = tmp;
        }
        Movie m=movies.get(position);
        ((TextView)convertView).setText(m.getTitle());
        return convertView;
    }
}
