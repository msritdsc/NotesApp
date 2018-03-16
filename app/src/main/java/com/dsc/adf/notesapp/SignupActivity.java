package com.dsc.adf.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mUserName, mPassword, mConfirmPassword;
    private SharedPreferences mAuthPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mUserName = findViewById(R.id.name);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        Button signupButton = findViewById(R.id.signup_button);
        mAuthPref = getApplicationContext().getSharedPreferences("auth", Context.MODE_PRIVATE);

        signupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_button){
            String name = mUserName.getEditableText().toString();
            String password = mPassword.getEditableText().toString();
            String confirmPassword = mConfirmPassword.getEditableText().toString();

            if (name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this, "Enter all the details",Toast.LENGTH_SHORT).show();
            }else{
                if (password.equals(confirmPassword)){
                    SharedPreferences.Editor editor = mAuthPref.edit();
                    editor.putString("username",name);
                    editor.putString("password",password);
                    editor.apply();

                    Toast.makeText(this,"Signup sucessful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, HomeActivity.class));
                }else{
                    Toast.makeText(this,"Passwords donot match",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
