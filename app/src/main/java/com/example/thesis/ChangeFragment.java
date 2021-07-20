package com.example.thesis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thesis.adapter.RecyclerviewAdapterChange;
import com.example.thesis.adapter.RecyclerviewAdapterEdit;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;

import java.util.Calendar;
import java.util.List;

public class ChangeFragment extends Fragment implements RecyclerViewClickInterface{
    RecyclerView stepsRecycler;
    RecyclerviewAdapterChange recyclerviewAdapter;
    List<StepsData> stepsDataList;
    View view;

    DataBaseHelper dataBaseHelper;
    Context thiscontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change, container,false);


        thiscontext = container.getContext();

stepsRecycler = view.findViewById(R.id.changeRecycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, RecyclerView.VERTICAL, false);
        stepsRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter = new RecyclerviewAdapterChange(thiscontext,this);
        stepsRecycler.setAdapter(recyclerviewAdapter);


        return view;
    }


    @Override
    public void onItemClick(int position) {
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putString("helper", String.valueOf(position));
        sharedEditor.apply();
        String helper = "";
        if (position == 0) helper = "Vasya";
        else helper = "Luda";
        Toast.makeText(thiscontext, "You chose " + helper, Toast.LENGTH_LONG).show();




    }

    @Override
    public void onItemLongClick(int position) {

    }
}