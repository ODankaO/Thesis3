package com.example.thesis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.google.android.gms.common.SignInButton;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SettingsFragment extends Fragment {
    SignInButton sign_in;
    MaterialButton google_button;
    Context thiscontext;
    Button changebutt;
    Button editbutt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container,false);
        google_button = view.findViewById(R.id.google_button);
        thiscontext = container.getContext();
        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thiscontext, LoginActivity.class);
                startActivity(intent);
            }
        });

        editbutt = view.findViewById(R.id.editbutt);
        changebutt = view.findViewById(R.id.change_helper_btn);
        editbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.action_settingsFragment2_to_editViewFragment22);

            }
        });

        changebutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.settochange_action);

            }
        });
        return view;
    }
}
