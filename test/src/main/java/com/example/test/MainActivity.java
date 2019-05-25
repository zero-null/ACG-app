package com.example.test;

import android.icu.text.BreakIterator;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import static android.provider.UserDictionary.Words.APP_ID;

public class MainActivity extends AppCompatActivity {

    private Button loginButtton;
    private TextView mBaseMessageText;
    private TextView mMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        loginButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void initViews() {
        loginButtton = (Button) findViewById(R.id.loginButton);
        mBaseMessageText = (TextView) findViewById(R.id.mBaseMessageText);
        mMessageText = (TextView)findViewById(R.id.mMessageText);
    }


}
