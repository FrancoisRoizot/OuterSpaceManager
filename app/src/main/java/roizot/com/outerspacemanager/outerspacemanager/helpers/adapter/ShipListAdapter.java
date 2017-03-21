package roizot.com.outerspacemanager.outerspacemanager.helpers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;

/**
 * Created by roizotf on 21/03/2017.
 */
public class ShipListAdapter extends ArrayAdapter<Ship> {

    private Context context;
    private ArrayList<Ship> ships;

    public ShipListAdapter(Context context, ArrayList<Ship> ships) {
        super(context, R.layout.adapter_ship_list, ships);
        this.context = context;
        this.ships = ships;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflate.inflate(R.layout.adapter_ship_list, parent, false);

        TextView shipName;
        shipName = (TextView) rowView.findViewById(R.id.shipNameList);
        shipName.setText(ships.get(position).getName());

        return rowView;
    }
}
