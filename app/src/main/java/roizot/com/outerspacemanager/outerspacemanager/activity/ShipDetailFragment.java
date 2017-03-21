package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Refresh;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.PostResponse;

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

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void fillShipDetail(final Ship ship, Float userMinerals, Float userGas) {

        shipName.setText(ship.getName());
        shipGasCost.setText(String.valueOf(ship.getGasCost()));
        shipMineralCost.setText(String.valueOf(ship.getMineralCost()));
        shipAttack.setText(String.valueOf(ship.getMinAttack()));
        shipShield.setText(String.valueOf(ship.getShield()));
        shipLife.setText(String.valueOf(ship.getLife()));
        shipSpeed.setText(String.valueOf(ship.getSpeed()));
        shipTimeToBuild.setText(String.valueOf(ship.getTimeToBuild()));

        int nbShipWithMinerals = Math.round(userMinerals / ship.getMineralCost()) - 1;
        int nbShipWithGas = Math.round(userGas / ship.getGasCost()) - 1;
        Integer nbShip = 0;
        if(ship.getMineralCost() == 0) {
            nbShip = nbShipWithGas;
        } else if(ship.getGasCost() == 0) {
            nbShip = nbShipWithMinerals;
        } else {
            if(nbShipWithGas <= nbShipWithMinerals){
                nbShip = nbShipWithGas;
            } else {
                nbShip = nbShipWithMinerals;
            }
        }

        shipReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        shipBuildMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        shipPlusCent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        Call<PostResponse> createShipCall = service.buildShips(token, shipId, amount);
        createShipCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if(response.code() == 200) {
                    Toast.makeText(getActivity(), "Vaisseaux en construction...", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                } else {
                    Toast.makeText(getActivity(), "Une erreur est survenue...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast error = Toast.makeText(getActivity(), "Une erreur est survenue...", Toast.LENGTH_LONG);
                error.show();
            }
        });
    }
}
