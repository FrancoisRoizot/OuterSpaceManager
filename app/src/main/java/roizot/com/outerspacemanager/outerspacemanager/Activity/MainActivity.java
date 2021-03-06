package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


    private static final int MY_PERMISSIONS_REQUEST_WRITE_DB = 0;

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
    private TextView gas;
    private TextView minerals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        token = Config.getToken(getApplicationContext());
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation? (to avoid a never ask again response)
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block this thread waiting for the user's response !
                // After the user sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_DB);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an app-defined int constant. The callback method gets the result of the request.
            }
        }

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
        gas = (TextView) findViewById(R.id.gas);
        minerals = (TextView) findViewById(R.id.minerals);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserInfos();
    }

    @Override
    public void onClick(View v) {
        Button clickedBtn = (Button) v;
        switch (v.getId()) {
            case R.id.logout :
                logout();
                break;
            case R.id.buildings :
                Intent toBuilding = new Intent(getApplicationContext(), BuildingActivity.class);
                startActivity(toBuilding);
                break;
            case R.id.research :
                Intent toResearch = new Intent(getApplicationContext(), ResearchActivity.class);
                startActivity(toResearch);
                break;
            case R.id.galaxy :
                Intent toRank = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(toRank);
                break;
            case R.id.sheepBuilder :
                Intent toSpatioPort = new Intent(getApplicationContext(), SpatioPortActivity.class);
                startActivity(toSpatioPort);
                break;
            case R.id.overView :
            case R.id.fleet :
                Toast.makeText(getApplicationContext(), clickedBtn.getText() + " pas encore implémenté", Toast.LENGTH_SHORT).show();
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
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UserInfos> request = service.getUserInfos(token);

        request.enqueue(new Callback<UserInfos>() {
            @Override
            public void onResponse(Call<UserInfos> request, Response<UserInfos> response) {
                if (response.isSuccessful()) {
                    userName.setText(response.body().getUsername());
                    points.setText( (long) response.body().getPoints() + " Points");
                    gas.setText(String.valueOf( (long) response.body().getGas()));
                    minerals.setText(String.valueOf( (long) response.body().getMinerals()));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_DB: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Config.setDBRights(true);
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    Config.setDBRights(false);
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request
        }
    }
}
