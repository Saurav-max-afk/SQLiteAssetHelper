package com.aptron.sqliteassethelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Splash_Activity extends AppCompatActivity {
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        if (Build.VERSION.SDK_INT>=21){
            window=this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.color3));

        }

        getSupportActionBar().hide();

        Timer timer=new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(Splash_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },3000);

}
}
