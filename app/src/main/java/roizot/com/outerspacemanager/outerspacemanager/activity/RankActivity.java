package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Adapter.RankAdapter;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.UsersResponse;

/**
 * Created by mac4 on 14/03/2017.
 */

public class RankActivity extends Activity{

    private static final int LIMIT = 20;
    private String token;
    private RecyclerView rvRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        token = Config.getToken(getApplicationContext());

        this.rvRank = (RecyclerView) findViewById(R.id.usersList);
        rvRank.setLayoutManager(new LinearLayoutManager(this));

        getRanks();
    }

    public void getRanks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UsersResponse> request = service.getUsersRank(token, 0, LIMIT);

        request.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> request, Response<UsersResponse> response) {
                if (response.isSuccessful()) {
                    rvRank.setAdapter(new RankAdapter(response.body().getUsers(), RankActivity.this));
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
