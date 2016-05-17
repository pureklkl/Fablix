package com.example.pengyuanfan.fablix.movieListAdapter;

import com.example.pengyuanfan.fablix.util.JSONCodeLocator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class MovieListParser {

    public static void parse(String code, List<MvItemBean> result, MvPage mvPage){
        code= JSONCodeLocator.locate(code);
        try {
            JSONObject  mvL = new JSONObject(code);
            JSONArray mvs = mvL.optJSONArray("movies");
            mvPage.setCurPageS(mvL.getString("curPage"));
            mvPage.setMaxPageS(mvL.getString("maxPage"));
            mvPage.setQuery( mvL.getString("title"));
            for(int i=0;i<mvs.length();i++){
                JSONObject mv=mvs.getJSONObject(i);
                result.add(new MvItemBean(mv.getString("banner_url"),
                                          mv.getString("title"),
                                          mv.getString("dirctor")+" "+mv.getString("year"),
                                          mv.getString("id")));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
