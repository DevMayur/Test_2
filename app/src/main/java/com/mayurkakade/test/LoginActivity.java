package com.mayurkakade.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout til_username,til_password;
    Button bt_submit;
    SharedPreferences sharedPreferences;
    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("LOGIN_SESSION",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("userLoggedIn",false)) {
            LogUserIn(sharedPreferences.getString("userName","null"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        til_username = findViewById(R.id.til_username);
        til_password = findViewById(R.id.til_password);
        bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(til_username,til_password);
            }
        });
    }

    private void submit(TextInputLayout til_username, TextInputLayout til_password) {
        til_username.setHelperText("");
        til_password.setHelperText("");
        if (!TextUtils.isEmpty(til_username.getEditText().getText())) {
            if (!TextUtils.isEmpty(til_username.getEditText().getText())) {
                if (til_username.getEditText().getText().toString().equals("123") && til_password.getEditText().getText().toString().equals("123")) {
                    LogUserIn(til_username.getEditText().getText().toString());
                } else {
                    if (!til_username.getEditText().getText().toString().equals("123")) {
                        showError(til_username, "incorrect username");
                    } else {
                        if (!til_password.getEditText().getText().toString().equals("123"))
                            showError(til_password, "incorrect password");
                    }
                }
            } else {
                Toast.makeText(this, "Please Enter your password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Enter your username", Toast.LENGTH_SHORT).show();
        }
    }

    private void showError(TextInputLayout til,String error) {
        til.setHelperText(error);
    }


    private void LogUserIn(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userLoggedIn",true);
        editor.putString("userName",username);
        editor.apply();

        Intent intent = new Intent(LoginActivity.this,SecondActivity.class);
        startActivity(intent);
        finish();

    }
}