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

import com.example.pengyuanfan.fablix.json.SingleStar;
import com.example.pengyuanfan.fablix.singleMS.SingleMovieParser;
import com.example.pengyuanfan.fablix.singleStar.SingleStarParser;
import com.example.pengyuanfan.fablix.singleStar.StarredInListAdapter;
import com.example.pengyuanfan.fablix.util.ConnectionState;
import com.example.pengyuanfan.fablix.util.HttpGetThread;
import com.example.pengyuanfan.fablix.util.ImageDowloader;
import com.example.pengyuanfan.fablix.util.URLParam;

import java.net.URI;

public class SingleStarActivity extends AppCompatActivity {

    private ImageView photoV;
    private TextView nameV;
    private TextView idV;
    private TextView dobV;
    private GridView moviesGrid;

    private ProgressDialog progressDialog;

    String starId;
    SingleStar ss;
    String singleStarUrl;

    private ConnectionState cs;
    private Context appContext;
    private Context actContext;

    private Handler onGetSingleStar = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            if(msg.what == HttpGetThread.success){
                ss = SingleStarParser.parse(msg.getData().getString("result"));
                if(ss!=null&&ss.getId().equals(starId))
                    showSingleStar(ss);
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
        setContentView(R.layout.activity_single_star);

        starId = getIntent().getStringExtra(Fablix_Search.SINGLE_STAR_ID);
        Log.d("actSwitch","newSingleStar"+starId);
        photoV = (ImageView) findViewById(R.id.singleStar_img);
        idV = (TextView) findViewById(R.id.singleStar_starId);
        dobV = (TextView) findViewById(R.id.singleStar_birthday);
        moviesGrid = (GridView) findViewById(R.id.grid_movies);
        nameV = (TextView) findViewById(R.id.singleStar_name);

        moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieId = ss.getMovies().get(position).getId();
                Intent singleStarToSingleMovie = new Intent(SingleStarActivity.this, SingleMovieActivity.class);
                singleStarToSingleMovie.putExtra(Fablix_Search.SINGLE_MOVIE_ID, movieId);
                startActivity(singleStarToSingleMovie);
            }
        });

        appContext = this.getApplicationContext();
        actContext = this.getBaseContext();

        cs = new ConnectionState(appContext);

        singleStarUrl = appContext.getString(R.string.fablix_Url)+appContext.getString(R.string.fablix_singleStarUrl);

        downLoadSingleStar(starId);
    }

    private void downLoadSingleStar(String starId){
        if(cs.isConnectingToInternet()){
            progressDialog = ProgressDialog.show(this, "", "Downloading...");
            try {
                URI singleStarRequestUri = URLParam.appendUri(singleStarUrl, "id="+starId);
                Log.d("ss", singleStarRequestUri.toString());
                new HttpGetThread(singleStarRequestUri.toURL(), onGetSingleStar).start();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void showSingleStar(SingleStar ss){
        nameV.setText(ss.getFname()+" "+ss.getLname());
        idV.setText(ss.getId());
        dobV.setText(ss.getDob());
        moviesGrid.setAdapter(new StarredInListAdapter(ss.getMovies()));
        new ImageDowloader(photoV).execute(ss.getPhoto_url());
    }
}
