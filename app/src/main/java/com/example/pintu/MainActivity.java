package com.example.pintu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GamePintuLayout gamePintuLayout=findViewById(R.id.id_gameview);
        Button easy=findViewById(R.id.easy);
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.mColumn=3;
                gamePintuLayout.level();
            }
        });
        Button medium=findViewById(R.id.medium);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.mColumn=4;
                gamePintuLayout.level();
            }
        });
        Button diff=findViewById(R.id.diff);
        diff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.mColumn=5;
                gamePintuLayout.level();
            }
        });
        Button timestart=findViewById(R.id.timestart);
        Chronometer chronometer=findViewById(R.id.time);
        timestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.chron=chronometer;
                gamePintuLayout.timecount();
            }
        });
        Button picture1=findViewById(R.id.picture1);
        picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.changePicture1();
            }
        });
        Button picture2=findViewById(R.id.picture2);
        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.changePicture2();
            }
        });
        Button picture3=findViewById(R.id.picture3);
        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gamePintuLayout.changePicture3();
            }
        });
    }

}