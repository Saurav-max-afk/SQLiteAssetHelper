package com.aptron.sqliteassethelper.RecyclerPackage;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aptron.sqliteassethelper.R;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.DbViewHolder> implements Filterable  {

   public ArrayList<DbModelClass> objDbModelClassArrayList ;
   public ArrayList<DbModelClass> examplelistfull;
    public ArrayList<DbModelClass> examplelistfull1;


    public DbAdapter(ArrayList<DbModelClass> objDbModelClassArrayList) {
        this.objDbModelClassArrayList = objDbModelClassArrayList;
        examplelistfull= new ArrayList<>(objDbModelClassArrayList);
        examplelistfull1= new ArrayList<>(objDbModelClassArrayList);


    }

    @NonNull
    @Override
    public DbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singeRow = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row,parent,false);
        return new DbViewHolder(singeRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolder holder, int position) {


        final DbModelClass objDbModelClass = objDbModelClassArrayList.get(position);
        holder.textView1.setText(objDbModelClass.getText1());
        holder.textView2.setText(objDbModelClass.getText2());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("name1",objDbModelClass.getText1());
                intent .putExtra("name2",objDbModelClass.getText2());
                v.getContext().startActivity(intent);


            }
        });



    }

    @Override
    public int getItemCount() {
      //  objDbModelClassArrayList=new ArrayList<>();

        return objDbModelClassArrayList.size();

    }

    public Filter getExapleFilter1() {
        return exapleFilter1;
    }

    private Filter exapleFilter1=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DbModelClass> filteredList1= new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList1.addAll(examplelistfull1);

            }else {
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (DbModelClass item : examplelistfull){
                    if (item.getText2().toLowerCase().contains(filterpattern)){
                        filteredList1.add(item);

                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList1;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            objDbModelClassArrayList.clear();
            objDbModelClassArrayList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    @Override
    public Filter getFilter()
    {

        return exapleFilter;
    }

    private Filter exapleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DbModelClass> filteredList= new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(examplelistfull);

            }else {
                String filterpattern = constraint.toString().toLowerCase().trim();

                for (DbModelClass item : examplelistfull){
                    if (item.getText1().toLowerCase().contains(filterpattern)){
                        filteredList.add(item);

                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            objDbModelClassArrayList.clear();
            objDbModelClassArrayList.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

    public static class DbViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView1;TextView textView2;
        LinearLayout linearLayout;
        public DbViewHolder(@NonNull View itemView) {
            super(itemView);


            textView1=itemView.findViewById(R.id.text1);
            textView2=itemView.findViewById(R.id.text2);
            linearLayout=itemView.findViewById(R.id.linearLayout);
        }
    }

}
