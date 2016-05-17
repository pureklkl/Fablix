package com.example.pengyuanfan.fablix.singleStar;

import com.example.pengyuanfan.fablix.json.SingleStar;
import com.example.pengyuanfan.fablix.util.JSONCodeLocator;
import com.google.gson.Gson;

/**
 * Created by pengyuanfan on 5/15/2016.
 */
public class SingleStarParser {
    public static SingleStar parse(String code){
        code = JSONCodeLocator.locate(code);
        if(code!=null) {
            Gson gs = new Gson();
            return gs.fromJson(code, SingleStar.class);
        }else {
            return null;
        }
    }
}
