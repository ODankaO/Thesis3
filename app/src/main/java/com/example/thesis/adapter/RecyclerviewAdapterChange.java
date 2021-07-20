package com.example.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.R;
import com.example.thesis.RecyclerViewClickInterface;
import com.example.thesis.model.StepsData;
import com.example.thesis.model.StepsStatisticsData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerviewAdapterChange extends RecyclerView.Adapter<RecyclerviewAdapterChange.RecyclerviewHolder> {

    Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerviewAdapterChange(Context context, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;

        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public RecyclerviewAdapterChange(Context context) {
        this.context = context;

    }




    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item4,parent,false);



        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {
    if (position == 1){
        holder.helpericon.setBackgroundResource(R.drawable.luda_demo);

    }






    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public final class RecyclerviewHolder extends RecyclerView.ViewHolder{


            ImageView helpericon;
            public RecyclerviewHolder(@NonNull View itemView) {
                super(itemView);
                helpericon = itemView.findViewById(R.id.helpericon);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        return true;
                    }
                });




            }

        public void refreshData(String table){

            notifyDataSetChanged();
        }
    }

}
