package com.aptron.sqliteassethelper.RecyclerPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aptron.sqliteassethelper.DatabaseHelper;
import com.aptron.sqliteassethelper.R;
import com.aptron.sqliteassethelper.RecentItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    ImageView imageView;
    Button button1,button2;
    Boolean flag = false;
    String text1;
    String text2, text3, text4;

    private ArrayList<DbModelClass> dbModelClasses1;
    private DatabaseHelper databaseHelper;
    private ArrayList<FabItem> fabitem;
    private ArrayList<RecentItem> recentItems;

    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.textview_2);

        button1 = findViewById(R.id.image1);
        button2=findViewById(R.id.share_button);
        databaseHelper = new DatabaseHelper(this);

        getSupportActionBar().setTitle("Detail_Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-7167808774224016/7291174845");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        fabitem = new ArrayList();
        recentItems=new ArrayList();

        final String text1 = getIntent().getStringExtra("name1");
        final String text2 = getIntent().getStringExtra("name2");

        recentData();



        flag = inDb(text1, text2);

        if (flag) {


            button1.setBackgroundResource(R.drawable.ic_favorite_black_24dp);


        } else {
            button1.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

        }


        textView1.setText(text1);
        textView2.setText(text2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,text1 + " " + text2);
                startActivity(intent.createChooser(intent,"Share"));
                 }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    Boolean isinserted = databaseHelper.insertIntoTheDatabase(text1, text2);
                    button1.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

                    if (isinserted == true) {
                        Toast.makeText(DetailActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(DetailActivity.this, "data not inserted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    databaseHelper.DeleteData(text1, text2);
                    button1.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    Toast.makeText(DetailActivity.this, "Removed from Favourites", Toast.LENGTH_SHORT).show();


                }

            }
        });

        databaseHelper.insertIntoTheDatabase1(text1,text2);

    }


    private Boolean inDb(String name1, String name2) {
        Cursor cursor = databaseHelper.read_all_data();


        try {
            while (cursor.moveToNext()) {


                String text1 = cursor.getString(cursor.getColumnIndex("text1"));
                String text2 = cursor.getString(cursor.getColumnIndex("text2"));

                fabitem.add(new FabItem(text1, text2));

            }

        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
        }

        Log.e("message", "" + fabitem.size());
        Log.e("message", "" + fabitem.toString());

        if (fabitem.size() > 0) {
            for (int i = 0; i < fabitem.size(); i++) {
                FabItem item = fabitem.get(i);
                Log.e("name", "" + name1 + " " + name2);
                Log.e("name1", "" + item.text1 + " " + item.text2);

                if (name1.equals(item.text1) && name2.equals(item.text2)) {

                    return true;
                }
            }

        }


        return false;
    }

    public void recentData(){
        Cursor cursor1 = databaseHelper.read_recent_data();


        try {
            while (cursor1.moveToNext()) {


                String text1 = cursor1.getString(cursor1.getColumnIndex("text1"));
                String text2 = cursor1.getString(cursor1.getColumnIndex("text2"));

                recentItems.add(new RecentItem(text1,text2));

            }

        } finally {
            if (cursor1 != null && cursor1.isClosed())
                cursor1.close();
        }

    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
