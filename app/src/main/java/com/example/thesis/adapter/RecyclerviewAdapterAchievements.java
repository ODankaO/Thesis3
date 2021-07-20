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
import com.example.thesis.model.AchievementsData;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerviewAdapterAchievements extends RecyclerView.Adapter<RecyclerviewAdapterAchievements.RecyclerviewHolder> {

    Context context;
    private RecyclerViewClickInterface recyclerViewClickInterface;
    List<AchievementsData> achievementsData;


    public RecyclerviewAdapterAchievements(Context context, List<AchievementsData> achievementsData, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.context = context;
        this.achievementsData = achievementsData;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }





    @NonNull
    @Override
    public RecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_row_item3,parent,false);

        return new RecyclerviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewHolder holder, int position) {

        holder.title.setText(achievementsData.get(position).getAchievement_title());
       holder.text.setText(achievementsData.get(position).getAchievement_text());
        if (achievementsData.get(position).getAchievement_status() == 0){
            holder.star.setVisibility(View.INVISIBLE);
            holder.background.setBackgroundResource(R.drawable.plankedit);
        }
        else {
            holder.star.setVisibility(View.VISIBLE);
            holder.background.setBackgroundResource(R.drawable.plankeditpng);
        }





    }

    @Override
    public int getItemCount() {
        return achievementsData.size();
    }

    public final class RecyclerviewHolder extends RecyclerView.ViewHolder{

            TextView title, text;
            ImageView star, background;

            public RecyclerviewHolder(@NonNull View itemView) {
                super(itemView);


                title = itemView.findViewById(R.id.ach_title);
                text = itemView.findViewById(R.id.ach_text);
                star = itemView.findViewById(R.id.ach_star);
                background = itemView.findViewById(R.id.backgroundach);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewClickInterface.onItemClick(getAdapterPosition());
                    }
                });

            }
    }

}
