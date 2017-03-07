package roizot.com.outerspacemanager.outerspacemanager.Activity;

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
import roizot.com.outerspacemanager.outerspacemanager.Helpers.BuildingAdapter;
import roizot.com.outerspacemanager.outerspacemanager.Models.Building;
import roizot.com.outerspacemanager.outerspacemanager.NetWork.BuildingResponse;
import roizot.com.outerspacemanager.outerspacemanager.NetWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.R;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingActivity extends Activity implements View.OnClickListener {

    public static final String PREFS_NAME = "OGamePrefs";

    private String token;

    private ArrayList<Building> buildings;
    private ListView buildingList;
    private static BuildingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "");
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
                    adapter = new BuildingAdapter(buildings,getApplicationContext());
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
