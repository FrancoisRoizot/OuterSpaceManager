package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import roizot.com.outerspacemanager.outerspacemanager.R;

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

        if(shipDetailsFrag == null || !shipDetailsFrag.isInLayout()){
            /*Intent i = new Intent(getApplicationContext(), ShipDetailActivity.class);
            i.putExtra("SHIP_SELECTED", shipListFrag.getShipAt(position));
            i.putExtra("USER_MINERALS", getIntent().getFloatExtra("USER_MINERALS", 0));
            i.putExtra("USER_GAS", getIntent().getFloatExtra("USER_GAS", 0));

            startActivity(i);*/
        } else {
            shipDetailsFrag.fillShipDetail(
                    shipListFrag.getShipAt(position),
                    getIntent().getFloatExtra("USER_MINERALS", 0),
                    getIntent().getFloatExtra("USER_GAS", 0)
            );
        }
    }
}
