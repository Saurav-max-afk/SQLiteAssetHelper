package com.aptron.sqliteassethelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Discription_Activity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discription_);
        textView=findViewById(R.id.text1);

        String params ="All in One Dictionary App is the one of the best apps to learn english and hindi as well. You can search hindi meaning of any english words and english meaning of any hindi words. \n"

             +   "\n" +

        "The main advantage of this app is that it is easy to use so every user can learn english and hindi as well from this app easily.\n " + "\n" +

                "All in one Dictionary App is Offline mode app and the user having no internet connection also can use this app effectively.\n " + "\n" +

          "And the most important fact is that the size of this app is moderate so that this app consume very low space in device.";



        textView.setText(params);
        textView.setMovementMethod(new ScrollingMovementMethod());

        getSupportActionBar().setTitle("Description");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
