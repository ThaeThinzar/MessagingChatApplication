package com.example.messagingchatapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);
    }
    @OnClick(R.id.btn_login)
    public void clickLogin(){
        startActivity(new Intent(MainActivity.this,UserActivity.class));
    }
}
