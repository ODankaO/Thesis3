package com.example.thesis;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.adapter.RecyclerviewAdapterAchievements;
import com.example.thesis.adapter.RecyclerviewAdapterEdit;
import com.example.thesis.model.AchievementsData;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;

import java.util.List;

public class AchievementsFragment extends Fragment implements RecyclerViewClickInterface{
    RecyclerView stepsRecycler;
    RecyclerviewAdapterAchievements recyclerviewAdapterAchievements;
    List<AchievementsData> achievementsData;
    View view;

    DataBaseHelper dataBaseHelper;
    Context thiscontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_achievement, container,false);
        View viewadd2 = inflater.inflate(R.layout.recyclerview_row_item3, container,false);

        thiscontext = container.getContext();

        dataBaseHelper = new DataBaseHelper(thiscontext);
        achievementsData = dataBaseHelper.getAchievements();
        stepsRecycler = (RecyclerView) view.findViewById(R.id.stepsRecycler3);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, RecyclerView.VERTICAL, false);
        stepsRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapterAchievements = new RecyclerviewAdapterAchievements(thiscontext, achievementsData,this);
        stepsRecycler.setAdapter(recyclerviewAdapterAchievements);


        return view;
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onItemLongClick(int position) {

    }
}