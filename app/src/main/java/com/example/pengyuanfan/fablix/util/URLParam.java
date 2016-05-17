package com.example.pengyuanfan.fablix.util;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class URLParam {
    public static URI appendUri(String uri, String... appendQuery) throws URISyntaxException {
        URI oldUri = new URI(uri);

        String newQuery = oldUri.getQuery();
        StringBuffer connectQuery = new StringBuffer();
        for(String q:appendQuery){
            connectQuery.append("&"+q);
        }
        if (newQuery == null) {
            newQuery = connectQuery.substring(1);
        } else {
            newQuery += connectQuery;
        }

        URI newUri = new URI(oldUri.getScheme(), oldUri.getAuthority(),
                oldUri.getPath(), newQuery, oldUri.getFragment());

        return newUri;
    }
}
