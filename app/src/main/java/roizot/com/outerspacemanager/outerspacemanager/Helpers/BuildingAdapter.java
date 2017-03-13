package roizot.com.outerspacemanager.outerspacemanager.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.models.Building;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingAdapter extends ArrayAdapter<Building> {

    private final Context context;
    private final ArrayList<Building> values;
    private Building building;


    public BuildingAdapter(Context context, ArrayList<Building> values) {
        super(context, R.layout.adapter_building, values);
        this.context = context;
        this.values = values;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_building, parent, false);

        building = values.get(position);

        TextView buildingName = (TextView) rowView.findViewById(R.id.buildingName);
//        TextView building_effect = (TextView) rowView.findViewById(R.id.buil);
        TextView buildingNextCostGas = (TextView) rowView.findViewById(R.id.buildingNextCostGas);
        TextView buildingNextCostMinerals = (TextView) rowView.findViewById(R.id.buildingNextCostMinerals);
        TextView buildingLevel = (TextView) rowView.findViewById(R.id.buildingLevel);
        TextView buildingTimeBuild = (TextView) rowView.findViewById(R.id.buildingTimeBuild);
        Button upgradeBuilding = (Button) rowView.findViewById(R.id.upgradeBuilding);

        String name = building.getName();
        String effect = building.getEffect();
        String gasCost = String.valueOf(building.getGasCostLevel0() + building.getLevel() * building.getGasCostByLevel());
        String mineralCost = String.valueOf(building.getMineralCostLevel0() + building.getLevel() * building.getMineralCostByLevel());
        String level = "lvl." + String.valueOf(building.getLevel());
        String time = String.valueOf(building.getTimeToBuildLevel0() + building.getLevel() * building.getTimeToBuildByLevel());
        final int buildingId = values.get(position).getBuildingId();
        
        buildingName.setText(name);
//        building_effect.setText(effect);
        buildingNextCostGas.setText(gasCost);
        buildingNextCostMinerals.setText(mineralCost);
        buildingLevel.setText(level);
        buildingTimeBuild.setText(time);
        upgradeBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildBuilding(buildingId);
            }
        });

        return rowView;
    }


    private void buildBuilding(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<PostResponse> request = service.buildBuilding(Config.getToken(context), id);

        request.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> request, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Construction lancée !", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Log.d("Error errorBody", response.errorBody().toString());
                    Toast.makeText(context, "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(context, "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
