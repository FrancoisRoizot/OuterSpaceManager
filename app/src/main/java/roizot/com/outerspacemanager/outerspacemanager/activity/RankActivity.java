package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.adapter.RankAdapter;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.UsersResponse;

/**
 * Created by mac4 on 14/03/2017.
 */

public class RankActivity extends Activity implements View.OnClickListener {

    private static final int LIMIT = 20;
    private String token;
    private RecyclerView rvRank;
    private Button nextRank;
    private Button previousRank;
    private int start = 0;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        token = Config.getToken(getApplicationContext());

        rvRank = (RecyclerView) findViewById(R.id.usersList);
        rvRank.setLayoutManager(new LinearLayoutManager(this));
        nextRank = (Button) findViewById(R.id.nextRank);
        nextRank.setOnClickListener(this);
        previousRank = (Button) findViewById(R.id.previousRank);
        previousRank.setOnClickListener(this);

        previousRank.setVisibility(View.GONE);

        loader = new ProgressDialog(this);
        loader.setTitle("Chargement du classement");
        loader.setCancelable(false);

        getRanks();

    }

    public void getRanks() {
        loader.setMessage("Rangs " + (1 + start) + " à " + (21 + start));
        loader.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UsersResponse> request = service.getUsersRank(token, start, LIMIT);

        request.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> request, Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getUsers().size() < LIMIT) {
                        nextRank.setVisibility(View.GONE);
                    }
                    rvRank.setAdapter(new RankAdapter(response.body().getUsers(), RankActivity.this));
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextRank :
                if(start == 0) {
                    previousRank.setVisibility(View.VISIBLE);
                }
                start = start + 20;
                break;
            case R.id.previousRank :
                start = start - 20;
                if(start == 0) {
                    previousRank.setVisibility(View.GONE);
                }
                if (nextRank.getVisibility() == View.GONE) {
                    nextRank.setVisibility(View.VISIBLE);
                }
                break;
        }
        getRanks();
    }
}
