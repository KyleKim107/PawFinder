package com.example.pawfinder.Lost;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.LostPetsListAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AllLostPetsFragment extends Fragment {

    private final String TAG = "AllLostPetsFragment";

    public interface OnAllLostPetSelectedListener {
        void onAllLostPetSelected(LostPet lostPet);
    }
    OnAllLostPetSelectedListener mOnAllLostPetSelectedListener;

    TextView mAllLostPetsText;

    private ArrayList<LostPet> mAllLostPets;
    private ListView mListView;
    private LostPetsListAdapter mAdapter;

    View root;

    public AllLostPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_all_lost_pets, container, false);

        mAllLostPetsText = root.findViewById(R.id.allLostPetsText);
        mAllLostPetsText.setVisibility(View.VISIBLE);

        mListView = root.findViewById(R.id.listView_all);
        mAllLostPets = new ArrayList<>();

        setUpListView();

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            mOnAllLostPetSelectedListener = (OnAllLostPetSelectedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
        super.onAttach(context);
    }

    private void setUpListView() {
        Log.d(TAG, "setUpListView: Setting up all lost pet list.");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("AllLostPets");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    mAllLostPetsText.setVisibility(View.VISIBLE);
                }
                else {
                    mAllLostPetsText.setVisibility(View.GONE);
                }
                for (DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    LostPet lostPet = singleSnapshot.getValue(LostPet.class);
                    mAllLostPets.add(lostPet);
                }
                displayLostPets();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }

    private void displayLostPets() {
        if (mAllLostPets != null) {
            Collections.sort(mAllLostPets, new Comparator<LostPet>() {
                @Override
                public int compare(LostPet lostPet1, LostPet lostPet2) {
                    return lostPet2.getDate_posted().compareTo(lostPet1.getDate_posted());
                }
            });
            mAdapter = new LostPetsListAdapter(getActivity(), R.layout.layout_alllostpets_listitem, mAllLostPets);
            mAdapter.setEllipsesInvisible();
            mListView.setAdapter(mAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mOnAllLostPetSelectedListener.onAllLostPetSelected(mAllLostPets.get(i));
                }
            });
        }
    }
}