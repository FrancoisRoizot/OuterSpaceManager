package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.helpers.BuildingAdapter;
import roizot.com.outerspacemanager.outerspacemanager.models.Building;
import roizot.com.outerspacemanager.outerspacemanager.netWork.BuildingResponse;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingActivity extends Activity implements View.OnClickListener {

    private String token;

    private ArrayList<Building> buildings;
    private ListView buildingList;
    private static BuildingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        token = Config.getToken(getApplicationContext());
        getBuildingInfos();

        buildingList = (ListView) findViewById(R.id.buildingList);
    }

    @Override
    public void onClick(View v) {
        Log.i("Clicked", v.toString());
    }

    public void getBuildingInfos() {
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
                    buildings = response.body().getBuildings();
                    adapter = new BuildingAdapter(getApplicationContext(), buildings);
                    buildingList.setAdapter(adapter);
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
}
