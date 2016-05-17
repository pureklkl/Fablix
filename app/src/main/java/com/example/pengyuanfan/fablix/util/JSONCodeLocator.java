package com.example.pengyuanfan.fablix.util;

import android.util.Log;

/**
 * Created by pengyuanfan on 5/14/2016.
 */
public class JSONCodeLocator {
    public static String locate(String code){
        if(code!=null){
            int contentpos=code.indexOf("content");
            if(contentpos>=0){
                code=code.substring(contentpos);
                int start=code.indexOf("{");
                int end=code.indexOf(";");
                Log.d("lp", Integer.toString(start)+","+Integer.toString(end));
                if(start>=0&&end>=0){
                    code = code.substring(code.indexOf("{"),code.indexOf(";"));
                }
                else {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        return code;
    }
}
