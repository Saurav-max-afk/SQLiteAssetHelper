package com.aptron.sqliteassethelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aptron.sqliteassethelper.RecyclerPackage.DetailActivity;
import com.aptron.sqliteassethelper.RecyclerPackage.FabItem;

import java.util.ArrayList;

public class FabAdapter extends RecyclerView.Adapter<FabAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FabItem> fabItems;
    private DatabaseHelper databaseHelper;

    FabAdapter(Context context, ArrayList<FabItem> fabItems){
        this.context=context;
        this.fabItems=fabItems;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fab_list,parent,false);
        databaseHelper=new DatabaseHelper(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.text1.setText(fabItems.get(position).getText1());
        holder.text2.setText(fabItems.get(position).getText2());


    }

    @Override
    public int getItemCount() {
        return fabItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        Button fab_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text1=itemView.findViewById(R.id.text_name1);
            text2=itemView.findViewById(R.id.text_name2);
            fab_button=itemView.findViewById(R.id.fab_button);

            fab_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fab_button.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

                    Toast.makeText(context, "Removed From Favourites", Toast.LENGTH_SHORT).show();
                    int position=getAdapterPosition();
                    final FabItem fabItem=fabItems.get(position);
                    databaseHelper.DeleteData(fabItem.getText1(),fabItem.getText2());
                    removeItem(position);
                }
            });
        }

        private void removeItem(int position){
            fabItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,fabItems.size());
        }
    }
}
