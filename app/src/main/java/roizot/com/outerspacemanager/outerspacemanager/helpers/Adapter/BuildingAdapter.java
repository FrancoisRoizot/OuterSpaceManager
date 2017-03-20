package roizot.com.outerspacemanager.outerspacemanager.helpers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.DB.BuildingDB;
import roizot.com.outerspacemanager.outerspacemanager.helpers.DB.OuterSpaceManagerDAO;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Refresh;
import roizot.com.outerspacemanager.outerspacemanager.models.Building;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingAdapter extends RecyclerView.Adapter<BuildingAdapter.BuildingViewHolder> {

    private final ArrayList<Building> buildings;
    private final Context context;
    private final Refresh refresh;
    private boolean isRefreshing = true;

    public void setRefreshing(boolean refreshing) {
        isRefreshing = refreshing;
    }


    public BuildingAdapter(ArrayList<Building> buildings, Context context, Refresh refresh) {
        this.buildings = buildings;
        this.context = context;
        this.refresh = refresh;
    }

    @Override
    public BuildingAdapter.BuildingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_building, parent, false);
        BuildingViewHolder viewHolder = new BuildingViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BuildingAdapter.BuildingViewHolder holder, int position) {
        Building building = buildings.get(position);

        String gasCost = String.valueOf(building.getGasCostLevel0() + building.getLevel() * building.getGasCostByLevel());
        String mineralCost = String.valueOf(building.getMineralCostLevel0() + building.getLevel() * building.getMineralCostByLevel());
        String level = "lvl." + String.valueOf(building.getLevel());
        String time = String.valueOf(building.getTimeToBuildLevel0() + building.getLevel() * building.getTimeToBuildByLevel());
        String image = building.getImageUrl();
        final int buildingId = building.getBuildingId();
        final long timeToBuild = (building.getTimeToBuildLevel0() + building.getLevel() * building.getTimeToBuildByLevel()) * 1000;

        holder.buildingName.setText(building.getName());
        holder.buildingNextCostGas.setText(gasCost);
        holder.buildingNextCostMinerals.setText(mineralCost);
        holder.buildingLevel.setText(level);
        holder.buildingTimeBuild.setText(time);

        if(building.isBuilding()) {
            holder.upgradeBuilding.setText(R.string.construction_en_cours);
            holder.upgradeBuilding.setClickable(false);
            OuterSpaceManagerDAO bd = new OuterSpaceManagerDAO(context);
            bd.open();
            ArrayList<BuildingDB> buildingTime = bd.getBuildingRunning(buildingId);
            bd.close();
            if (buildingTime.size() > 0 ) {
                final long timeStamp = buildingTime.get(0).getFinishDate();
                final android.os.Handler handler = new android.os.Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        String hms = Config.formatTime(timeStamp - System.currentTimeMillis());
                        holder.upgradeBuilding.setText(hms);
                        if (isRefreshing){
                            handler.postDelayed(this, 1000);
                        }
                    }
                };
                handler.post(runnable);
            }
        } else {
            holder.upgradeBuilding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buildBuilding(buildingId, timeToBuild);
                }
            });
        }

        Glide
            .with(context)
            .load(image)
            .centerCrop()
            .crossFade()
            .into(holder.buildingImage);
    }


    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public class BuildingViewHolder extends RecyclerView.ViewHolder {
        private TextView buildingName;
        private TextView buildingNextCostGas;
        private TextView buildingNextCostMinerals;
        private TextView buildingLevel;
        private TextView buildingTimeBuild;
        private ImageView buildingImage;
        private Button upgradeBuilding;

        public BuildingViewHolder(View itemView) {
            super(itemView);
            buildingName = (TextView) itemView.findViewById(R.id.buildingName);
            buildingNextCostGas = (TextView) itemView.findViewById(R.id.buildingNextCostGas);
            buildingNextCostMinerals = (TextView) itemView.findViewById(R.id.buildingNextCostMinerals);
            buildingLevel = (TextView) itemView.findViewById(R.id.buildingLevel);
            buildingTimeBuild = (TextView) itemView.findViewById(R.id.buildingTimeBuild);
            buildingImage = (ImageView) itemView.findViewById(R.id.buildingImage);
            upgradeBuilding = (Button) itemView.findViewById(R.id.upgradeBuilding);
        }
    }

    private void buildBuilding(final int id, final long timeToBuild) {
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
                    OuterSpaceManagerDAO bd = new OuterSpaceManagerDAO(context);
                    bd.open();
                    bd.deleteBuildings();
                    bd.addBuildingRunning(System.currentTimeMillis() + timeToBuild, id);
                    bd.close();
                    refresh.refresh();
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
