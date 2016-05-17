package com.example.pengyuanfan.fablix.singleMS;

import com.example.pengyuanfan.fablix.json.SingleMovie;
import com.example.pengyuanfan.fablix.util.JSONCodeLocator;
import com.google.gson.*;
/**
 * Created by pengyuanfan on 5/14/2016.
 */
public class SingleMovieParser {
    public static SingleMovie parse(String code){
        code = JSONCodeLocator.locate(code);
        if(code!=null) {
            Gson gs = new Gson();
            return gs.fromJson(code, SingleMovie.class);
        }else {
            return null;
        }
    }
}
