package com.example.pengyuanfan.fablix.movieListAdapter;

/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class MvPage {
    int curPage;
    int maxPage;
    String curPageS;
    String maxPageS;
    String query;
    public MvPage() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCurPageS() {
        return curPageS;
    }

    public void setCurPageS(String curPageS) {
        this.curPageS = curPageS;
        curPage=Integer.valueOf(curPageS);
    }

    public String getMaxPageS() {
        return maxPageS;
    }

    public void setMaxPageS(String maxPageS) {
        this.maxPageS = maxPageS;
        maxPage=Integer.valueOf(maxPageS);
    }


    public int getCurPage() {
        return curPage;
    }

    public int getMaxPage() {
        return maxPage;
    }
}
