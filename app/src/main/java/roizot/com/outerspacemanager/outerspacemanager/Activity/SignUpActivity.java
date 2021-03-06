package roizot.com.outerspacemanager.outerspacemanager.activity;

import android.app.Activity;
import android.content.Intent;
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

public class SignUpActivity extends Activity implements View.OnClickListener {

    private Button connexion;
    private Button inscription;

    private EditText identifiant;
    private EditText password;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        connexion = (Button)findViewById(R.id.signUp);
        connexion.setOnClickListener(this);
        inscription = (Button)findViewById(R.id.signIn);
        inscription.setOnClickListener(this);
        identifiant = (EditText)findViewById(R.id.identifiant);
        password = (EditText)findViewById(R.id.password);
        email = (EditText)findViewById(R.id.email);


        String token = Config.getToken(getApplicationContext());
        if (!token.equals("")) {
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signUp :
                this.createAccount(this.identifiant.getText().toString(), this.password.getText().toString(), this.email.getText().toString());
                break;
            case R.id.signIn :
                Intent toSignUp = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(toSignUp);
                break;
        }
    }

    protected void createAccount(String identifiant, String password, String email) {
        Map<String, String> data = new HashMap<>();
        data.put("username", identifiant);
        data.put("password", password);
        data.put("email", email);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkManager.BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UserConnection> request = service.createAccount(data);

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
                    Toast.makeText(getApplicationContext(), "Erreur à la création !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserConnection> request, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Erreur de connexion à internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
