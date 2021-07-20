package com.example.thesis.adapter;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
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

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerviewHolder> {

    Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclerviewAdapter(Context context, List<StepsData> stepsDataList, List<StepsStatisticsData> stepsStatsDataList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.stepsDataList = stepsDataList;
        this.stepsStatsDataList = stepsStatsDataList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public RecyclerviewAdapter(Context context) {
        this.context = context;

    }

    List<StepsData> stepsDataList;
    List<StepsStatisticsData> stepsStatsDataList;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();


    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item,parent,false);



        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {




        holder.userName.setText(stepsDataList.get(position).getStepName());
        switch(stepsDataList.get(position).getColor()){
            case "Red":

                holder.color.setBackgroundResource(R.drawable.taskcolorred);
                break;
            case "Blue":

                holder.color.setBackgroundResource(R.drawable.taskcolorblue);
                break;
            case "Green":

                holder.color.setBackgroundResource(R.drawable.taskcolorgreen);
                break;
            case "Grey":

                holder.color.setBackgroundResource(R.drawable.taskcolorgrey);
                break;
        }

        for(int i = 0; i < stepsStatsDataList.size(); i++){
            if (stepsDataList.get(position).getStepTimes()> 1 && stepsDataList.get(position).getStepId() == stepsStatsDataList.get(i).getStepId() ) {
                holder.textCount.setVisibility(View.VISIBLE);
                holder.textCount.setText(stepsStatsDataList.get(i).getStepCount()+"/"+ stepsDataList.get(position).getStepTimes());
            }

            if (stepsDataList.get(position).getStepId() == stepsStatsDataList.get(i).getStepId() &&  stepsStatsDataList.get(i).getStepActionDate().equals(dateFormat.format(date)) && stepsStatsDataList.get(i).getStepCount() == stepsDataList.get(position).getStepTimes()){

                holder.background.setBackgroundResource(R.drawable.rowplankpurplechecked);
                holder.textCount.setVisibility(View.INVISIBLE);

            }
        }



    }

    @Override
    public int getItemCount() {
        return stepsDataList.size();
    }

    public final class RecyclerviewHolder extends RecyclerView.ViewHolder{

            ImageView background;
            ImageView color;
            TextView userName;
        TextView textCount;

            public RecyclerviewHolder(@NonNull View itemView) {
                super(itemView);


                userName = itemView.findViewById(R.id.ach_title);

                color = itemView.findViewById(R.id.color_view);
                background = itemView.findViewById(R.id.background);
                textCount = itemView.findViewById(R.id.textCount);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        recyclerViewClickInterface.onItemLongClick(getAdapterPosition());
                        return true;
                    }
                });




            }

        public void refreshData(String table){

            notifyDataSetChanged();
        }
    }

}
