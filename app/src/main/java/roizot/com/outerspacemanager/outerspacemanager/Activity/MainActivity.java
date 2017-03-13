package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.R;
import roizot.com.outerspacemanager.outerspacemanager.models.UserInfos;

/**
 * Created by mac4 on 07/03/2017.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private String token;

    private Button logout;
    private Button overView;
    private Button buildings;
    private Button fleet;
    private Button research;
    private Button sheepBuilder;
    private Button galaxy;
    private TextView userName;
    private TextView points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = Config.getToken(getApplicationContext());

        setContentView(R.layout.home_page);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        overView = (Button) findViewById(R.id.overView);
        overView.setOnClickListener(this);
        buildings = (Button) findViewById(R.id.buildings);
        buildings.setOnClickListener(this);
        fleet = (Button) findViewById(R.id.fleet);
        fleet.setOnClickListener(this);
        research = (Button) findViewById(R.id.research);
        research.setOnClickListener(this);
        sheepBuilder = (Button) findViewById(R.id.sheepBuilder);
        sheepBuilder.setOnClickListener(this);
        galaxy = (Button) findViewById(R.id.galaxy);
        galaxy.setOnClickListener(this);
        userName = (TextView) findViewById(R.id.userName);
        points = (TextView) findViewById(R.id.points);

        setUserInfos();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout :
                logout();
                break;
            case R.id.buildings :
                Intent toBuilding = new Intent(getApplicationContext(),BuildingActivity.class);
                startActivity(toBuilding);
                break;
            case R.id.overView :
            case R.id.fleet :
            case R.id.research :
            case R.id.sheepBuilder :
            case R.id.galaxy :
                Toast.makeText(getApplicationContext(), "Pas encore implémenté", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void logout() {
        Config.deleteToken(getApplicationContext());
        Intent myIntent = new Intent(getApplicationContext(),SignInActivity.class);
        startActivity(myIntent);
    }

    public void setUserInfos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UserInfos> request = service.getUserInfos(token);

        request.enqueue(new Callback<UserInfos>() {
            @Override
            public void onResponse(Call<UserInfos> request, Response<UserInfos> response) {
                if (response.isSuccessful()) {
                    userName.setText(response.body().getUsername());
                    points.setText(response.body().getPoints() + " Points");
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la récupération des données !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfos> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
