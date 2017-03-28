package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.io.Serializable;

import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by roizotf on 21/03/2017.
 */
public class SpatioPortActivity extends Activity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spatioport);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ShipListFragment shipListFrag = (ShipListFragment)getFragmentManager().findFragmentById(R.id.shipListFragment);
        ShipDetailFragment shipDetailsFrag = (ShipDetailFragment)getFragmentManager().findFragmentById(R.id.shipDetailsFragment);

        Ship ship = shipListFrag.getShipAt(position);
        double gas = shipListFrag.getGas();
        double minerals = shipListFrag.getMinerals();

        if(shipDetailsFrag == null || !shipDetailsFrag.isInLayout()){
            Intent i = new Intent(getApplicationContext(), ShipDetailActivity.class);
            i.putExtra("SHIP_SELECTED", ship);
            i.putExtra("USER_MINERALS", gas);
            i.putExtra("USER_GAS", minerals);
            startActivity(i);
        } else {
            shipDetailsFrag.fillShipDetail(ship, gas, minerals);
        }
    }
}
