package com.example.hainan.my_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ActivityResult extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        TextView result;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        Intent intent=getIntent();
        String r=intent.getStringExtra("r");
        result=(TextView)findViewById(R.id.textView);
        result.setText(r);
    }

}
