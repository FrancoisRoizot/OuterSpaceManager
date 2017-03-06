package roizot.com.outerspacemanager.outerspacemanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button connexion;
    private EditText identifiant;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        connexion = (Button)findViewById(R.id.connexion);
        connexion.setOnClickListener(this);
        identifiant = (EditText)findViewById(R.id.identifiant);
        password = (EditText)findViewById(R.id.password);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.connexion :
                this.createAccount(this.identifiant.getText().toString(), this.password.getText().toString());
                break;
        }
    }

    protected void createAccount(String identifiant, String password) {

    }
}
