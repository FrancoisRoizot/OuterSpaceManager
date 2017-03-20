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
import roizot.com.outerspacemanager.outerspacemanager.helpers.Adapter.BuildingAdapter;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Refresh;
import roizot.com.outerspacemanager.outerspacemanager.netWork.BuildingResponse;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingActivity extends Activity implements Refresh {

    private String token;
    private RecyclerView rvBuildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        token = Config.getToken(getApplicationContext());

        this.rvBuildings = (RecyclerView) findViewById(R.id.buildingList);
        rvBuildings.setLayoutManager(new LinearLayoutManager(this));

        refresh();
    }

    @Override
    public void refresh() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<BuildingResponse> request = service.getBuildingsInfos(token);

        request.enqueue(new Callback<BuildingResponse>() {
            @Override
            public void onResponse(Call<BuildingResponse> request, Response<BuildingResponse> response) {
                if (response.isSuccessful()) {
                    rvBuildings.setAdapter(new BuildingAdapter(response.body().getBuildings(), BuildingActivity.this, BuildingActivity.this));
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BuildingResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((BuildingAdapter)rvBuildings.getAdapter()).setRefreshing(false);
    }
}