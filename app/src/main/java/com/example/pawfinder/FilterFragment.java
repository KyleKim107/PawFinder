package com.example.pawfinder;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FilterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        return view;
    }

    /**
     * This method sets up the Selection Fragment
     */
    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();

        return fragment;
    }



}