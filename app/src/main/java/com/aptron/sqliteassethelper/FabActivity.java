package com.aptron.sqliteassethelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aptron.sqliteassethelper.RecyclerPackage.DbAdapter;
import com.aptron.sqliteassethelper.RecyclerPackage.FabItem;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class FabActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private ArrayList<FabItem> fabItems;
    private FabAdapter fabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        recyclerView = findViewById(R.id.fav_Recycler);
        databaseHelper = new DatabaseHelper(this);
        fabItems = new ArrayList<>();

        showData();





        getSupportActionBar().setTitle("Favourites");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayout = findViewById(R.id.linear2);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable1.setEnterFadeDuration(2000);
        animationDrawable1.setExitFadeDuration(4000);
        animationDrawable1.start();


    }

    public void showData() {
        Cursor cursor = databaseHelper.read_all_data();


        try {
            while (cursor.moveToNext()) {


                String text1 = cursor.getString(cursor.getColumnIndex("text1"));
                String text2 = cursor.getString(cursor.getColumnIndex("text2"));

                FabItem item = new FabItem(text1, text2);
                fabItems.add(item);


            }

        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
        }

        if (fabItems.size() > 0) {
            fabAdapter = new FabAdapter(this, fabItems);
            // recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(fabAdapter);
        } else {
            Toast.makeText(this, "Nothing to show in Favourites", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
