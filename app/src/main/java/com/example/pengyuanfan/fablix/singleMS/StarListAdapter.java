package com.example.pengyuanfan.fablix.singleMS;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pengyuanfan.fablix.json.Star;

import java.util.List;

/**
 * Created by pengyuanfan on 5/14/2016.
 */
public class StarListAdapter extends BaseAdapter {

    List<Star> stars;

    public StarListAdapter(List<Star> stars) {
        this.stars = stars;
    }

    @Override
    public int getCount() {
        return stars.size();
    }

    @Override
    public Object getItem(int position) {
        return stars.get(position);
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
        Star s = stars.get(position);
        ((TextView)convertView).setText(s.getFname()+s.getLname());
        return convertView;
    }
}
