package com.aptron.sqliteassethelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RecentItem> recentItems;
     DatabaseHelper databaseHelper;

    RecentAdapter(Context context,ArrayList<RecentItem> recentItems){
        this.context=context;
        this.recentItems=recentItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recentlist,parent,false);
        databaseHelper=new DatabaseHelper(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text1.setText(recentItems.get(position).getText1());
        holder.text2.setText(recentItems.get(position).getText2());

    }

    @Override
    public int getItemCount()
    {
        return recentItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text1=itemView.findViewById(R.id.text_recent1);
            text2=itemView.findViewById(R.id.text_recent2);


            }

    }
}
