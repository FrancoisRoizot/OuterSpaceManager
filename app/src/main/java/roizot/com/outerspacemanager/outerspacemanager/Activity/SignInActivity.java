package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roizot.com.outerspacemanager.outerspacemanager.helpers.Config;
import roizot.com.outerspacemanager.outerspacemanager.netWork.NetWorkManager;
import roizot.com.outerspacemanager.outerspacemanager.netWork.UserConnection;
import roizot.com.outerspacemanager.outerspacemanager.R;

/**
 * Created by mac4 on 07/03/2017.
 */

public class SignInActivity extends Activity implements View.OnClickListener{

    private Button connexion;
    private Button inscription;
    private EditText identifiant;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        connexion = (Button)findViewById(R.id.signUp);
        connexion.setOnClickListener(this);
        inscription = (Button)findViewById(R.id.signIn);
        inscription.setOnClickListener(this);
        identifiant = (EditText)findViewById(R.id.identifiant);
        password = (EditText)findViewById(R.id.password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signIn :
                this.connectAccount(this.identifiant.getText().toString(), this.password.getText().toString());
                break;
            case R.id.signUp :
                Intent toSignIn = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(toSignIn);
                break;
        }
    }

    protected void connectAccount(String identifiant, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("username", identifiant);
        data.put("password", password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UserConnection> request = service.connectAccount(data);

        request.enqueue(new Callback<UserConnection>() {
            @Override
            public void onResponse(Call<UserConnection> request, Response<UserConnection> response) {
                if (response.isSuccessful()) {
                    Config.setToken(getApplicationContext(), response.body().getToken());
                    Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(myIntent);
                } else {
                    Log.d("Error", "Erreur de parsing ou autres");
                    Log.d("Why", response.toString());
                    Toast.makeText(getApplicationContext(), "Erreur à la connection !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserConnection> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion  à internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
