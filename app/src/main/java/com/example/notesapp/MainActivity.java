package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final static String PREFS_SETTINGS = "prefs_settings";
    private SharedPreferences prefUser;

     EditText mTextUsername;
     EditText mTextPassword;
     Button mButtonLogin;
     TextView mTextViewRegister;
     DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        mTextUsername=(EditText)findViewById(R.id.edittext_username);
        mTextPassword=(EditText)findViewById(R.id.edittext_password);
        mButtonLogin=(Button) findViewById(R.id.button_login);
        mTextViewRegister=(TextView) findViewById(R.id.textview_register);
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefUser = getSharedPreferences(PREFS_SETTINGS,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefUser.edit();
                String user = mTextUsername.getText().toString().trim();
                String pwd = mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if( res == true )
                {
                    editor.putInt("ID_USER",db.getId(user,pwd));
                    Intent HomePage = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Login Error",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}