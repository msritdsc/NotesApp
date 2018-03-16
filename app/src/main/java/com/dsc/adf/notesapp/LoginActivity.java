package com.dsc.adf.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserName, mPassword;
    private SharedPreferences mAuthPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = findViewById(R.id.name);
        mPassword = findViewById(R.id.password);
        mAuthPref = getApplicationContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        Button loginButton = findViewById(R.id.login_button);
        TextView signupText = findViewById(R.id.signup);

        loginButton.setOnClickListener(this);
        signupText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_button){
            String username = mUserName.getEditableText().toString();
            String password = mPassword.getEditableText().toString();

            String savedUsername = mAuthPref.getString("username", "");
            String savedPassword = mAuthPref.getString("password", "");

            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please enter all details",
                        Toast.LENGTH_SHORT).show();
            }

            else if(!username.equals(savedUsername) || !password.equals(savedPassword)){
                Toast.makeText(getApplicationContext(), "Unable to login, Invalid details",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }else if (id == R.id.signup){
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
    }
}
