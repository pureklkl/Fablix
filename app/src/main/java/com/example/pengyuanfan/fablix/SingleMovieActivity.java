package com.example.pengyuanfan.fablix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengyuanfan.fablix.json.SingleMovie;
import com.example.pengyuanfan.fablix.json.SingleStar;
import com.example.pengyuanfan.fablix.json.Star;
import com.example.pengyuanfan.fablix.singleMS.SingleMovieParser;
import com.example.pengyuanfan.fablix.singleMS.StarBean;
import com.example.pengyuanfan.fablix.singleMS.StarListAdapter;
import com.example.pengyuanfan.fablix.util.ConnectionState;
import com.example.pengyuanfan.fablix.util.HttpGetThread;
import com.example.pengyuanfan.fablix.util.ImageDowloader;
import com.example.pengyuanfan.fablix.util.URLParam;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SingleMovieActivity extends AppCompatActivity {

    TextView movieIdV;
    TextView movieTitleV;
    TextView directorV;
    TextView yearV;
    TextView genreV;
    TextView priceV;
    ImageView bannerV;
    GridView starsGrid;
    private ProgressDialog progressDialog;
    String movieId;

    SingleMovie sg;
    String singleMovieUrl;

    private ConnectionState cs;
    private Context appContext;
    private Context actContext;

    Handler onGetSingleMovie=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            if(msg.what == HttpGetThread.success){
                sg = SingleMovieParser.parse(msg.getData().getString("result"));
                if(sg!=null&&sg.getId().equals(movieId))
                    showSingleMovie(sg);
                else
                    Toast.makeText(actContext, "Data cropped", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(actContext, "DownLoad Data failed", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        movieId = getIntent().getStringExtra(Fablix_Search.SINGLE_MOVIE_ID);

        movieIdV=(TextView)findViewById(R.id.singleMovie_movieId);
        movieTitleV=(TextView)findViewById(R.id.singleMovie_title);
        directorV=(TextView)findViewById(R.id.singleMovie_director);
        yearV=(TextView)findViewById(R.id.singleMovie_year);
        genreV=(TextView)findViewById(R.id.singleMovie_grene);
        priceV=(TextView)findViewById(R.id.singleMovie_price);
        bannerV=(ImageView)findViewById(R.id.singleMovie_img);
        starsGrid=(GridView) findViewById(R.id.grid_stars);

        starsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String starId = sg.getStars().get(position).getId();
                Intent singleMovieToSingleStar = new Intent(SingleMovieActivity.this, SingleStarActivity.class);
                singleMovieToSingleStar.putExtra(Fablix_Search.SINGLE_STAR_ID, starId);
                startActivity(singleMovieToSingleStar);
            }
        });

        movieIdV.setText(movieId);

        appContext = this.getApplicationContext();
        actContext = this.getBaseContext();

        cs = new ConnectionState(appContext);


        //generateData();

        singleMovieUrl = appContext.getString(R.string.fablix_Url) + appContext.getString(R.string.fablix_singleMovieUrl);
        downLoadSingleMovie(movieId);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void downLoadSingleMovie(String movieId){
        if(cs.isConnectingToInternet()){
            progressDialog = ProgressDialog.show(this, "", "Downloading...");
            try {
                URI singleMovieRequestUri = URLParam.appendUri(singleMovieUrl, "id="+movieId);
                Log.d("sg", singleMovieRequestUri.toString());
                new HttpGetThread(singleMovieRequestUri.toURL(), onGetSingleMovie).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showSingleMovie(SingleMovie data){
        movieIdV.setText(data.getId());
        movieTitleV.setText(data.getTitle());
        directorV.setText(data.getDirctor());
        yearV.setText(data.getYear());
        genreV.setText(data.getGenre());
        priceV.setText(data.getPrice());
        starsGrid.setAdapter(new StarListAdapter(data.getStars()));
        new ImageDowloader(bannerV).execute(data.getBanner_url());
    }


}
