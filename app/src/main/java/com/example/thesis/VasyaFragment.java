package com.example.thesis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thesis.model.DataBaseHelper;
import com.example.thesis.model.PhraseData;


public class VasyaFragment extends Fragment {
 private Context thiscontext;
private  DataBaseHelper dataBaseHelper;
SharedPreferences sharedPreferences;
ImageView vasya, back;
TextView phrase;
Button ach_btn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vasya, container, false);
        thiscontext = container.getContext();
        sharedPreferences =  PreferenceManager.getDefaultSharedPreferences(thiscontext);
        final SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        dataBaseHelper = new DataBaseHelper(thiscontext);
        int level = Integer.parseInt(sharedPreferences.getString("level","0"));
        int helper = Integer.parseInt(sharedPreferences.getString("helper","0"));
        dataBaseHelper.emptyVasya(level);

        if (!dataBaseHelper.VasyaisEmpty(level)) {
            if (helper == 0)
            dataBaseHelper.fillVasyaAll(level);
            else
                dataBaseHelper.fillLudaAll(level);
        }
        vasya = view.findViewById(R.id.vasya2);
        phrase = view.findViewById(R.id.vasya_phrase2);
        back = view.findViewById(R.id.vas_back);
        if (helper !=0) back.setVisibility(View.INVISIBLE);
        if (level == 1 ) back.setBackgroundResource(R.drawable.vasya_back_lev2);
        else if (level == 2 ) back.setBackgroundResource(R.drawable.vasya_back_lev1);
        else if (level == 3 ) back.setBackgroundResource(R.drawable.vasya_back_lev2);
        PhraseData phraseData = dataBaseHelper.randomPhrase(Integer.parseInt(sharedPreferences.getString("level","0")));
        phrase.setText(phraseData.getPhrase());
        vasya.setBackgroundResource(getResources().getIdentifier(phraseData.getEmotion(), "drawable", getActivity().getPackageName()));
        ach_btn = view.findViewById(R.id.ach_btn);
        ach_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.vastoach_action);
            }
        });
        vasya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int count = Integer.parseInt( sharedPreferences.getString("counttalk", "0"));
               count = count + 1;
               if (count == 10) {
                   dataBaseHelper.finishAchievement(4);
                   Toast.makeText(thiscontext, "Achievement 4", Toast.LENGTH_LONG).show();
               }
               if (count == 500) { dataBaseHelper.finishAchievement(5);
               Toast.makeText(thiscontext, "Achievement 4", Toast.LENGTH_LONG).show();}
               sharedEditor.putString("counttalk", String.valueOf(count));
               sharedEditor.commit();
                PhraseData phraseData = dataBaseHelper.randomPhrase(Integer.parseInt(sharedPreferences.getString("level","0")));
                phrase.setText(phraseData.getPhrase());
                vasya.setBackgroundResource(getResources().getIdentifier(phraseData.getEmotion(), "drawable", getActivity().getPackageName()));

            }
        });
        return view;
    }
}