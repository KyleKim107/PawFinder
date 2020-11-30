package com.example.pawfinder.Lost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pawfinder.R;
import com.google.android.material.tabs.TabLayout;

public class AllLostPetsFragment extends Fragment {

    TextView mAllLostPetsText;

    public AllLostPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_all_lost_pets, container, false);
        mAllLostPetsText = root.findViewById(R.id.allLostPetsText);
        mAllLostPetsText.setVisibility(View.VISIBLE);
        return root;
    }
}