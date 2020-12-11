package com.example.pawfinder.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pawfinder.Lost.LostFragment;
import com.example.pawfinder.R;

public class ViewFavoritePetFragment extends Fragment {

    ImageView backArrow;

    public ViewFavoritePetFragment() {
        // Required empty public constructor
//        super();
//        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_favorite_pet, container, false);

        backArrow = root.findViewById(R.id.backArrow_viewFavoritePet);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return root;
    }
}