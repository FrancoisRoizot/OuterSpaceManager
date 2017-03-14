package roizot.com.outerspacemanager.outerspacemanager.helpers;

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
import roizot.com.outerspacemanager.outerspacemanager.models.Research;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;

/**
 * Created by mac4 on 14/03/2017.
 */

public class ResearchAdapter extends RecyclerView.Adapter<ResearchAdapter.ResearchViewHolder> {
    
    private final ArrayList<Research> researchs;
    private final Context context;
    private final Refresh refresh;

    public ResearchAdapter(ArrayList<Research> Researchs, Context context, Refresh refresh) {
        this.researchs = Researchs;
        this.context = context;
        this.refresh = refresh;
    }

    @Override
    public ResearchAdapter.ResearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_research, parent, false);
        ResearchAdapter.ResearchViewHolder viewHolder = new ResearchAdapter.ResearchViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ResearchAdapter.ResearchViewHolder holder, int position) {
        Research research = researchs.get(position);

        String gasCost = String.valueOf(research.getGasCostLevel0() + research.getLevel() * research.getGasCostByLevel());
        String mineralCost = String.valueOf(research.getMineralCostLevel0() + research.getLevel() * research.getMineralCostByLevel());
        String level = "lvl." + String.valueOf(research.getLevel());
        String time = String.valueOf(research.getTimeToBuildLevel0() + research.getLevel() * research.getTimeToBuildByLevel());
        final int ResearchId = research.getResearchId();

        holder.researchName.setText(research.getName());
        holder.researchNextCostGas.setText(gasCost);
        holder.researchNextCostMinerals.setText(mineralCost);
        holder.researchLevel.setText(level);
        holder.researchTimeBuild.setText(time);
        if(research.isSearching()) {
            holder.upgradeResearch.setText(R.string.recherche_en_cours);
            holder.upgradeResearch.setClickable(false);
        } else {
            holder.upgradeResearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeResearch(ResearchId);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return researchs.size();
    }

    public class ResearchViewHolder extends RecyclerView.ViewHolder {
        private TextView researchName;
        private TextView researchNextCostGas;
        private TextView researchNextCostMinerals;
        private TextView researchLevel;
        private TextView researchTimeBuild;
        private ImageView researchImage;
        private Button upgradeResearch;

        public ResearchViewHolder(View itemView) {
            super(itemView);
            researchName = (TextView) itemView.findViewById(R.id.researchName);
            researchNextCostGas = (TextView) itemView.findViewById(R.id.researchNextCostGas);
            researchNextCostMinerals = (TextView) itemView.findViewById(R.id.researchNextCostMinerals);
            researchLevel = (TextView) itemView.findViewById(R.id.researchLevel);
            researchTimeBuild = (TextView) itemView.findViewById(R.id.researchTimeBuild);
//            researchImage = (ImageView) itemView.findViewById(R.id.buildingImage);
            upgradeResearch = (Button) itemView.findViewById(R.id.upgradeResearch);
        }
    }

    private void makeResearch(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<PostResponse> request = service.makeResearch(Config.getToken(context), id);

        request.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> request, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Construction lancée !", Toast.LENGTH_SHORT).show();
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
