package com.aptron.sqliteassethelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.ArrayLinkedVariables;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aptron.sqliteassethelper.RecyclerPackage.FabItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecentActivity<deletedItems> extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    ArrayList<RecentItem> recentItems, deletedItems;
    private RecentAdapter recentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        recyclerView = findViewById(R.id.Rec_Recycler);
        databaseHelper = new DatabaseHelper(this);
        recentItems = new ArrayList<>();

        getSupportActionBar().setTitle("Recent");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout linearLayout = findViewById(R.id.linear1);
        AnimationDrawable animationDrawable1 = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable1.setEnterFadeDuration(2000);
        animationDrawable1.setExitFadeDuration(4000);
        animationDrawable1.start();


        recentData();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    public void recentData() {
        Cursor cursor = databaseHelper.read_recent_data();
        recentItems.clear();

        try {

            while (cursor.moveToNext()) {


                String text1 = cursor.getString(cursor.getColumnIndex("text1"));
                String text2 = cursor.getString(cursor.getColumnIndex("text2"));

                RecentItem item = new RecentItem(text1, text2);
                recentItems.add(item);

            }

        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
        }
        if (recentItems.size() > 15) {
            removefromlist();
        } else {
            setData();
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void removefromlist() {
        Collections.reverse(recentItems);
        for (int i = 0; i < recentItems.size(); i++) {
            if (i > 14) {
                databaseHelper.DeleteRecentData(recentItems.get(i).text1, recentItems.get(i).text2);
                recentItems.remove(i);
            }
        }
        Collections.reverse(recentItems);
        setData();

    }

    public void setData() {

        Collections.reverse(recentItems);
        if (recentItems.size() > 0) {
            recentAdapter = new RecentAdapter(this, recentItems);
            // recyclerView.hasFixedSize();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(recentAdapter);
            recentAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
        }

    }

    RecentItem deletedRecentItems = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedRecentItems = recentItems.get(position);
                    databaseHelper.DeleteRecentData(deletedRecentItems.getText1(), deletedRecentItems.getText2());
                    recentItems.remove(position);
                    recentAdapter.notifyItemRemoved(position);


                    Snackbar.make(recyclerView, deletedRecentItems.getText1() + " is Deleted", Snackbar.LENGTH_LONG).setAction("undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recentItems.add(position, deletedRecentItems);
                            recentAdapter.notifyItemInserted(position);
                        }
                    }).show();


                    break;
            }


        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive)

                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(RecentActivity.this, R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
