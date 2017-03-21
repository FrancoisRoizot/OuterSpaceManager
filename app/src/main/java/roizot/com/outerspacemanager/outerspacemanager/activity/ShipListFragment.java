package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Refresh;
import roizot.com.outerspacemanager.outerspacemanager.helpers.adapter.ShipListAdapter;
import roizot.com.outerspacemanager.outerspacemanager.models.Ship;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.ShipsResponse;

/**
 * Created by roizotf on 21/03/2017.
 */
public class ShipListFragment extends Fragment implements Refresh {

    private ListView lvShips;
    private ArrayList<Ship> ships;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ship_list, container, false);
        lvShips = (ListView)v.findViewById(R.id.shipListView);

        token = Config.getToken(getActivity());
        refresh();

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvShips.setOnItemClickListener((SpatioPortActivity)getActivity());
    }

    public Ship getShipAt(Integer position) {
        return (Ship)lvShips.getAdapter().getItem(position);
    }

    @Override
    public void refresh() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<ShipsResponse> request = service.getShips(token);

        request.enqueue(new Callback<ShipsResponse>() {
            @Override
            public void onResponse(Call<ShipsResponse> request, Response<ShipsResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("Ships", response.body().toString());
                    ships = response.body().getShips();
                    lvShips.setAdapter(new ShipListAdapter(getActivity(), ships));
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getActivity(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShipsResponse> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getActivity(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
