package com.example.pawfinder.Lost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawfinder.R;

public class ReportFoundPetFragment extends Fragment {

    public ReportFoundPetFragment() {
        // Required empty public constructor
    }

    public static ReportFoundPetFragment newInstance() {
        ReportFoundPetFragment fragment = new ReportFoundPetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report_found_pet, container, false);
    }
}