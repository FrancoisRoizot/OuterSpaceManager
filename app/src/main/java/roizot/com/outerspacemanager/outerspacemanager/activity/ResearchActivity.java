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
import roizot.com.outerspacemanager.outerspacemanager.helpers.Refresh;
import roizot.com.outerspacemanager.outerspacemanager.helpers.adapter.ResearchAdapter;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.ResearchResponse;

/**
 * Created by mac4 on 14/03/2017.
 */

public class ResearchActivity extends Activity implements Refresh {

    private String token;
    private RecyclerView rvResearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        token = Config.getToken(getApplicationContext());

        this.rvResearch = (RecyclerView) findViewById(R.id.researchList);
        rvResearch.setLayoutManager(new LinearLayoutManager(this));

        refresh();
    }

    @Override
    public void refresh() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<ResearchResponse> request = service.getResearchesInfos(token);

        request.enqueue(new Callback<ResearchResponse>() {
            @Override
            public void onResponse(Call<ResearchResponse> request, Response<ResearchResponse> response) {
                if (response.isSuccessful()) {
                    rvResearch.setAdapter(new ResearchAdapter(response.body().getResearches(), ResearchActivity.this, ResearchActivity.this));
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResearchResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((ResearchAdapter)rvResearch.getAdapter()).setRefreshing(false);
    }
}
