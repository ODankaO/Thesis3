package com.example.thesis;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thesis.adapter.RecyclerviewAdapterEdit;
import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.StepsData;
import com.example.thesis.model.StepsStatisticsData;

import java.util.List;

public class EditViewFragment extends Fragment implements RecyclerViewClickInterface{
    RecyclerView stepsRecycler;
    RecyclerviewAdapterEdit recyclerviewAdapter;
    List<StepsData> stepsDataList;
    View view;

    DataBaseHelper dataBaseHelper;
    Context thiscontext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit, container,false);
        View viewadd2 = inflater.inflate(R.layout.recyclerview_row_item2, container,false);

        thiscontext = container.getContext();

        dataBaseHelper = new DataBaseHelper(thiscontext);
        stepsDataList = dataBaseHelper.getEveryone();
        stepsRecycler = (RecyclerView) view.findViewById(R.id.stepsRecycler2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(thiscontext, RecyclerView.VERTICAL, false);
        stepsRecycler.setLayoutManager(layoutManager);
        recyclerviewAdapter = new RecyclerviewAdapterEdit(thiscontext, stepsDataList,this);
        stepsRecycler.setAdapter(recyclerviewAdapter);


        return view;
    }


    @Override
    public void onItemClick(int position) {
        int id = stepsDataList.get(position).getStepId();

        EditViewFragmentDirections.EditactAction action = EditViewFragmentDirections.editactAction();
        action.setStepId(id);
        Navigation.findNavController(view).navigate(action);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onItemLongClick(int position) {

    }
}