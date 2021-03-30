package com.aptron.sqliteassethelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aptron.sqliteassethelper.RecyclerPackage.DbAdapter;
import com.aptron.sqliteassethelper.RecyclerPackage.DbModelClass;
import com.aptron.sqliteassethelper.RecyclerPackage.DetailActivity;
import com.aptron.sqliteassethelper.SQLitepackage.MyDbClass;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Boolean ascending = true;
    MyDbClass objMyDbClass;
    ArrayList<DbModelClass> objDbModelClassArrayList;
    RecyclerView recyclerView;
    DbAdapter objDbAdapter;
    Menu toolbarMenu;
    DatabaseHelper myDb;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAd1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        objMyDbClass = new MyDbClass(this);
        objDbModelClassArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.dataRV);

        myDb = new DatabaseHelper(this);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7167808774224016/1436212931");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                startActivity(new Intent(getApplicationContext(), FabActivity.class));

                mInterstitialAd.loadAd(new AdRequest.Builder().build());


            }
        });
        mInterstitialAd1 = new InterstitialAd(this);
        mInterstitialAd1.setAdUnitId("ca-app-pub-7167808774224016/1436212931");
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());

        mInterstitialAd1.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                startActivity(new Intent(getApplicationContext(), RecentActivity.class));

                mInterstitialAd1.loadAd(new AdRequest.Builder().build());


            }
        });


       toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        drawer = findViewById(R.id.drawer_layout1);


        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.mygradient));
        }

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        View view = navigationView.getHeaderView(0);
        TextView name = view.findViewById(R.id.name);
        TextView email = view.findViewById(R.id.email);

        name.setText("All in One");
        email.setText("Dictionary");

        showData();
        sortview();
    }


    public void showData() {
        try {

            objDbModelClassArrayList = objMyDbClass.getAllData();
            objDbAdapter = new DbAdapter(objDbModelClassArrayList);
            // recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(objDbAdapter);


        } catch (Exception e) {
            Log.e(this.toString(), e.getMessage());
            Toast.makeText(this, "Show Data:-" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void sortview() {
        Collections.sort(objDbModelClassArrayList, DbModelClass.ByDownload);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        toolbarMenu = menu;


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {


            toolbarMenu.getItem(1).setEnabled(false);
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Search in English");

            MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {

                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    toolbarMenu.getItem(1).setEnabled(true);

                    return true;
                }
            });


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    objDbAdapter.getFilter().filter(newText);


                    return false;
                }
            });
            return true;
        }
        if (id == R.id.action_search1) {

            toolbarMenu.getItem(0).setEnabled(false);

            SearchView searchView1 = (SearchView) item.getActionView();
            searchView1.setQueryHint("Search in Hindi");

            MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {

                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    toolbarMenu.getItem(0).setEnabled(true);

                    return true;
                }
            });

            searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    objDbAdapter.getExapleFilter1().filter(newText);


                    return false;

                }
            });

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog .Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("Do u want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.super.onBackPressed();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home1:
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.favourite1:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), FabActivity.class));

                }


                break;
            case R.id.recent:
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    startActivity(new Intent(getApplicationContext(), RecentActivity.class));

                }


                break;
            case R.id.feedback:
                startActivity(new Intent(getApplicationContext(), Feedback_Activity.class));

                break;

            case R.id.description:
                startActivity(new Intent(getApplicationContext(), Discription_Activity.class));

                break;
            case R.id.exit:
                AlertDialog .Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Do u want to Exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.super.onBackPressed();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                break;

            case R.id.share1:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "All in One Dictionary");
                    String shareMessage= "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
                break;
            case R.id.contact:
                startActivity(new Intent(getApplicationContext(),Contact_Activity.class));

                break;

        }
        return true;


    }


}


