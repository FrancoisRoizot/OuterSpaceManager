package roizot.com.outerspacemanager.outerspacemanager.Helpers;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import roizot.com.outerspacemanager.outerspacemanager.Models.Building;
import roizot.com.outerspacemanager.outerspacemanager.R;

/**
 * Created by mac4 on 07/03/2017.
 */

public class BuildingAdapter extends ArrayAdapter {

    private ArrayList<Building> dataSet;
    Context mContext;

    public BuildingAdapter(ArrayList<Building> data, Context context) {
        super(context, R.layout.adapter_building, data);
        this.dataSet = data;
        this.mContext = context;

    }
}
