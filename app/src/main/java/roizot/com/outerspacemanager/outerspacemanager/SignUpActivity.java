package roizot.com.outerspacemanager.outerspacemanager;

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

public class SignUpActivity extends Activity implements View.OnClickListener {

    public static final String PREFS_NAME = "OGamePrefs";

    private Button connexion;
    private Button inscription;
    private EditText identifiant;
    private EditText password;

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

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("token", "");
        if (!token.equals("")) {
            Intent myIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.signUp :
                this.createAccount(this.identifiant.getText().toString(), this.password.getText().toString());
                break;
            case R.id.signIn :
                Intent toSignUp = new Intent(getApplicationContext(),SignInActivity.class);
                startActivity(toSignUp);
                break;
        }
    }

    protected void createAccount(String identifiant, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("username", identifiant);
        data.put("password", password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://outer-space-manager.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkManager service = retrofit.create(NetWorkManager.class);
        Call<UserConnection> request = service.createAccount(data);

        request.enqueue(new Callback<UserConnection>() {
            @Override
            public void onResponse(Call<UserConnection> request, Response<UserConnection> response) {
                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("token", response.body().getToken());
                    editor.commit();
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
                Toast.makeText(getApplicationContext(), "Erreur de connexion !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
