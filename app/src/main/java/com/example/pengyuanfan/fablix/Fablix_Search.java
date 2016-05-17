package com.example.pengyuanfan.fablix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengyuanfan.fablix.movieListAdapter.MovieListAdapter;
import com.example.pengyuanfan.fablix.movieListAdapter.MovieListParser;
import com.example.pengyuanfan.fablix.movieListAdapter.MvItemBean;
import com.example.pengyuanfan.fablix.movieListAdapter.MvPage;
import com.example.pengyuanfan.fablix.util.ConnectionState;
import com.example.pengyuanfan.fablix.util.HttpGetThread;
import com.example.pengyuanfan.fablix.util.SoftKeyBoard;
import com.example.pengyuanfan.fablix.util.URLParam;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Fablix_Search extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText searchText;
    private ListView mvLV;
    private LinearLayout mainLayout;
    private PagerHolder mvLVPagerHolder;

    private List<MvItemBean> data = new ArrayList<MvItemBean>();
    private MvPage mvPage = new MvPage();

    private ConnectionState cs;
    private Context appContext;
    private Context actContext;

    private String searchURL;

    Handler onGetSearchResult=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            progressDialog.dismiss();
            Log.d("d","returned");
            if(msg.what== HttpGetThread.success){
                Log.d("d","showing");
                //showData(msg.getData().getString("result"));
                data.clear();
                MovieListParser.parse(msg.getData().getString("result"), data, mvPage);
                mvLV.setAdapter(new MovieListAdapter(Fablix_Search.this, data));
                if(!data.isEmpty()){
                    mvLVPagerHolder.getCur().setText(mvPage.getCurPageS());
                    mvLVPagerHolder.getMax().setText(mvPage.getMaxPageS());
                    mvLVPagerHolder.getPrev().setEnabled(true);
                    mvLVPagerHolder.getNext().setEnabled(true);
                    if(mvPage.getCurPage()==1){
                        mvLVPagerHolder.getPrev().setEnabled(false);
                    }
                    if(mvPage.getCurPage()==mvPage.getMaxPage()){
                        mvLVPagerHolder.getNext().setEnabled(false);
                    }
                    mvLVPagerHolder.getMvLVPager().setVisibility(View.VISIBLE);
                }else{
                    mvLVPagerHolder.getMvLVPager().setVisibility(View.GONE);
                   Toast.makeText(actContext,actContext.getString(R.string.no_movie), Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(actContext, "DownLoad Data failed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fablix);

        searchText = (EditText) findViewById(R.id.query_message);
        mvLV = (ListView)findViewById(R.id.mv_list);
        mainLayout = (LinearLayout)findViewById(R.id.main_content);
        mvLVPagerHolder = new PagerHolder();
        mvLVPagerHolder.setMvLVPager(getLayoutInflater().inflate(R.layout.movie_listpager, null));
        mvLVPagerHolder.setCur((TextView) mvLVPagerHolder.getMvLVPager().findViewById(R.id.curPage));
        mvLVPagerHolder.setMax((TextView) mvLVPagerHolder.getMvLVPager().findViewById(R.id.maxPage));
        mvLVPagerHolder.setPrev((Button) mvLVPagerHolder.getMvLVPager().findViewById(R.id.btn_prev));
        mvLVPagerHolder.setNext((Button) mvLVPagerHolder.getMvLVPager().findViewById(R.id.btn_next));
        mvLVPagerHolder.getMvLVPager().setVisibility(View.GONE);
        mvLV.addFooterView(mvLVPagerHolder.getMvLVPager());
        appContext = this.getApplicationContext();
        actContext = this.getBaseContext();

        cs = new ConnectionState(appContext);

        setupUI(mainLayout);

        mvLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieId=data.get(position).getId();
                Intent searchTosingleMovie = new Intent(Fablix_Search.this, SingleMovieActivity.class);
                searchTosingleMovie.putExtra(SINGLE_MOVIE_ID, movieId);
                startActivity(searchTosingleMovie);
            }
        });

        searchURL = appContext.getString(R.string.fablix_Url) + appContext.getString(R.string.fablix_searchUrl);

        if(savedInstanceState!=null)
            onRestartSearch(savedInstanceState);
    }

    private void onRestartSearch(Bundle savedInstanceState){
        String query=savedInstanceState.getString(QUERY),
               page=savedInstanceState.getString(PAGE);
        if(query!=null){
            searchText.setText(query);
            if(page!=null){
                searchOnline("title="+query+"&"+"page="+page);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void searchOnline(String query){
        //List<MvItemBean> data = generateData();
        if(cs.isConnectingToInternet()){
            progressDialog = ProgressDialog.show(this, "", "Downloading...");

            try{
                URI queryUrl = URLParam.appendUri(searchURL, query,
                        "normal=true");

                Log.d("d","clicking");
                new HttpGetThread(queryUrl.toURL(), onGetSearchResult).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void sendQuery(View view) {
        String queryTitle = searchText.getText().toString();
        if(queryTitle.trim().equals("")){
            Toast.makeText(actContext, actContext.getText(R.string.no_title), Toast.LENGTH_SHORT).show();
        }else {
            searchOnline("title="+queryTitle);
        }

    }

    public void nextPage(View view){
        String query=mvPage.getQuery();
        if(!query.trim().equals("")){
            searchOnline("title="+query+"&"+"page="+Integer.toString(mvPage.getCurPage()+1));
        }
    }

    public void backPage(View view){
        String query=mvPage.getQuery();
        if(!query.trim().equals("")){
            searchOnline("title="+query+"&"+"page="+Integer.toString(mvPage.getCurPage()-1));
        }
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    SoftKeyBoard.hideSoftKeyboard(Fablix_Search.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mvPage.getQuery()!=null&&!mvPage.getQuery().trim().equals("")){
            outState.putString(QUERY, mvPage.getQuery());
        }
        if(!data.isEmpty()){
            outState.putString(PAGE, mvPage.getCurPageS());
        }
    }

    private List<MvItemBean> generateData(){
        List<MvItemBean> data=new ArrayList<MvItemBean>();
        for(int i=0;i<20;i++){
            data.add(new MvItemBean("null", "movie"+i, "content"+i, Integer.toString(i)));
        }
        return data;
    }
    void showData(String str){
        TextView tmp=new TextView(this);
        Log.d("d",str);
        tmp.setText(str);
        mainLayout.addView(tmp);
    }
    private class PagerHolder{
        View mvLVPager;
        Button prev;
        Button next;
        TextView max;
        TextView cur;

        public View getMvLVPager() {
            return mvLVPager;
        }

        public void setMvLVPager(View mvLVPager) {
            this.mvLVPager = mvLVPager;
        }

        public Button getPrev() {
            return prev;
        }

        public void setPrev(Button prev) {
            this.prev = prev;
        }

        public Button getNext() {
            return next;
        }

        public void setNext(Button next) {
            this.next = next;
        }

        public TextView getMax() {
            return max;
        }

        public void setMax(TextView max) {
            this.max = max;
        }

        public TextView getCur() {
            return cur;
        }

        public void setCur(TextView cur) {
            this.cur = cur;
        }
    }

    private String QUERY="QUERY",
                   PAGE ="PAGE";

    static public String SINGLE_MOVIE_ID="FABLIX.SINGLE_MOVIE_ID";
    static public String SINGLE_STAR_ID = "FABLIX.SINGLE_STAR_ID";
}
