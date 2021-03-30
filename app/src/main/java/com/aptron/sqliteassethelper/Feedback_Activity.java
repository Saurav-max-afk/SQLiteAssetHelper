package com.aptron.sqliteassethelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback_Activity extends AppCompatActivity {
    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_);

        editText1 = findViewById(R.id.edit1);
        editText2 = findViewById(R.id.edit2);
        button = findViewById(R.id.buton);

        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recepients="sharpcode550@gmail.com";
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{recepients});
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback From Dictionary App");
                i.putExtra(Intent.EXTRA_TEXT,"Name:"+editText1.getText()+"\n Message:"+editText2.getText());
                i.setType("message/rfc822");

                startActivity(Intent.createChooser(i,"Please select Email Only"));

                
            }

        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
