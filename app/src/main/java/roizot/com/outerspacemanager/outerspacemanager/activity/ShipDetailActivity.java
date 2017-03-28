package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by mac4 on 27/03/2017.
 */

public class ShipDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_detail);
        Ship ship = (Ship)getIntent().getSerializableExtra("SHIP_SELECTED");
        double userMinerals = getIntent().getDoubleExtra("USER_MINERALS", 0);
        double userGas = getIntent().getDoubleExtra("USER_GAS", 0);

        ShipDetailFragment shipDetailsFrag = (ShipDetailFragment) getFragmentManager()
                .findFragmentById(R.id.shipDetailsFragment);
        shipDetailsFrag.fillShipDetail(ship, userMinerals, userGas);
    }
}
