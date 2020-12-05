package com.example.pawfinder.Lost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.R;

public class ViewLostPetFragment extends Fragment {

    ImageView backArrow;

    public ViewLostPetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_lost_pet, container, false);

        backArrow = root.findViewById(R.id.backArrow_viewLostPet);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LostFragment fragment = new LostFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_container, fragment);
                transaction.addToBackStack("Lost"); // TODO: or (null) ????
                transaction.commit();
            }
        });
        return root;
    }
}