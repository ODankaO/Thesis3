package com.example.thesis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.R;
import com.example.thesis.RecyclerViewClickInterface;
import com.example.thesis.model.StepsData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerviewAdapterEdit extends RecyclerView.Adapter<RecyclerviewAdapterEdit.RecyclerviewHolder> {

    Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerviewAdapterEdit(Context context, List<StepsData> stepsDataList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.stepsDataList = stepsDataList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    List<StepsData> stepsDataList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item2,parent,false);

        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {

        holder.userName.setText(stepsDataList.get(position).getStepName());




    }

    @Override
    public int getItemCount() {
        return stepsDataList.size();
    }

    public final class RecyclerviewHolder extends RecyclerView.ViewHolder{

            TextView userName;

            public RecyclerviewHolder(@NonNull View itemView) {
                super(itemView);


                userName = itemView.findViewById(R.id.ach_title);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    }
                });

            }
    }

}
