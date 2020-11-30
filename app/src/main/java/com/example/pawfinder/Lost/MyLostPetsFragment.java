package com.example.pawfinder.Lost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pawfinder.R;

import static android.content.ContentValues.TAG;

public class MyLostPetsFragment extends Fragment {

    private TextView mMyLostPetsText;

    private Context mContext;

    public MyLostPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_lost_pets, container, false);

        mContext = getActivity();

        mMyLostPetsText = root.findViewById(R.id.myLostPetsText);
        mMyLostPetsText.setVisibility(View.VISIBLE);

        return root;
    }

    private void setUpListView() {

    }
}