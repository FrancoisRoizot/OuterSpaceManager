package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.adapter.ShipListAdapter;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;
import roizot.com.outerspacemanager.outerspacemanager.netWork.ShipsResponse;

/**
 * Created by roizotf on 21/03/2017.
 */
public class ShipDetailFragment extends Fragment {

    private String token;

    private TextView shipName;
    private TextView shipLife;
    private TextView shipShield;
    private TextView shipAttack;
    private TextView shipSpeed;
    private TextView shipGasCost;
    private TextView shipMineralCost;
    private TextView shipTimeToBuild;
    private TextView shipSpatioPortLvlNeeded;

    private Button shipBuildShip;
    private Button shipReset;
    private Button shipBuildMax;
    private Button shipPlusCent;

    private EditText shipAmountToBuild;

    private ImageView shipImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_detail, container);

        token = Config.getToken(getActivity());

        shipName = (TextView)v.findViewById(R.id.shipName);
        shipShield = (TextView)v.findViewById(R.id.shipShield);
        shipLife = (TextView)v.findViewById(R.id.shipLife);
        shipSpeed = (TextView)v.findViewById(R.id.shipSpeed);
        shipGasCost = (TextView)v.findViewById(R.id.shipGasCost);
        shipMineralCost = (TextView)v.findViewById(R.id.shipMineralCost);
        shipAttack = (TextView)v.findViewById(R.id.shipAttack);
        shipTimeToBuild = (TextView)v.findViewById(R.id.shipTimeToBuild);
        shipSpatioPortLvlNeeded = (TextView)v.findViewById(R.id.shipSpatioPortLvlNeeded);

        shipBuildShip = (Button)v.findViewById(R.id.shipBuildShip);
        shipReset = (Button)v.findViewById(R.id.shipReset);
        shipBuildMax = (Button)v.findViewById(R.id.shipBuildMax);
        shipPlusCent = (Button)v.findViewById(R.id.shipPlusCent);

        shipAmountToBuild = (EditText) v.findViewById(R.id.shipAmountToBuild);

        shipImage = (ImageView) v.findViewById(R.id.shipImage);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fillShipDetail(final Ship ship, double userMinerals, double userGas) {

        String vShipname = ship.getName();
        String vshipGasCost = "Gaz : " + String.valueOf(ship.getGasCost());
        String vshipMineralCost = "Métaux : " + String.valueOf(ship.getMineralCost());
        String vshipAttack = "Attaque : " + ship.getMinAttack();
        String vshipShield = "Bouclier : " + ship.getShield();
        String vshipLife = "Vie : " + ship.getLife();
        String vshipSpeed = "Vitesse : " + ship.getSpeed();
        String vshipTimeToBuild = "Temps de construction : " + Config.formatTime(ship.getTimeToBuild());
        String vShipSpatioPortLvlNeeded = "Niveau du SpatioPort requis : " + ship.getSpatioportLevelNeeded();
        long maxshipsBuildable = 0;

        shipName.setText(vShipname);
        shipGasCost.setText(vshipGasCost);
        shipMineralCost.setText(vshipMineralCost);
        shipAttack.setText(vshipAttack);
        shipShield.setText(vshipShield);
        shipLife.setText(vshipLife);
        shipSpeed.setText(vshipSpeed);
        shipTimeToBuild.setText(vshipTimeToBuild);
        shipSpatioPortLvlNeeded.setText(vShipSpatioPortLvlNeeded);
        shipAmountToBuild.setText("0");

        int idImage = getActivity().getResources().getIdentifier("sheep_"+ ship.getShipId(), "drawable", getActivity().getPackageName());

        Glide
                .with(getActivity())
                .load(idImage)
                .fitCenter()
                .crossFade()
                .into(shipImage);

        long nbShipWithMinerals = (long) Math.floor(userMinerals / ship.getMineralCost());
        long nbShipWithGas = (long) Math.floor(userGas / ship.getGasCost());
        if(ship.getMineralCost() == 0) {
            maxshipsBuildable = nbShipWithGas;
        } else if(ship.getGasCost() == 0) {
            maxshipsBuildable = nbShipWithMinerals;
        } else {
            if(nbShipWithGas <= nbShipWithMinerals){
                maxshipsBuildable = nbShipWithGas;
            } else {
                maxshipsBuildable = nbShipWithMinerals;
            }
        }
        final long finalMaxshipsBuildable = maxshipsBuildable;

        shipReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipAmountToBuild.setText("0");
            }
        });
        shipBuildMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shipAmountToBuild.setText(String.valueOf(finalMaxshipsBuildable));
            }
        });

        shipPlusCent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int shipBuildNumber = Integer.valueOf(String.valueOf(shipAmountToBuild.getText()));
                if(finalMaxshipsBuildable >= 100 + shipBuildNumber) {
                    shipAmountToBuild.setText(String.valueOf(shipBuildNumber + 100));
                }
            }
        });
        shipBuildShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildShips(ship.getShipId(), Integer.valueOf(shipAmountToBuild.getText().toString()));
            }
        });
    }

    public void buildShips(int shipId, int amount) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Map<String, Integer> amountMap = new HashMap<>();
        amountMap.put("amount", amount);
        Call<PostResponse> request = service.buildShips(token, shipId, amountMap);

        request.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> request, Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Vaisseaux en construction...", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getActivity(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
