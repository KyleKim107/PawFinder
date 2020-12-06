package com.example.pawfinder.Lost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.example.pawfinder.Utils.LostPetsListAdapter;
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

import static android.content.ContentValues.TAG;

public class MyLostPetsFragment extends Fragment {

    private final String TAG = "MyLostPetsFragment";

    public interface OnMyLostPetSelectedListener {
        void onMyLostPetSelected(LostPet lostPet);
    }
    MyLostPetsFragment.OnMyLostPetSelectedListener mOnMyLostPetSelectedListener;

    private TextView mMyLostPetsText;
    private Context mContext;

    private ArrayList<LostPet> mMyLostPets;
    private ListView mListView;
    private LostPetsListAdapter mAdapter;

    public MyLostPetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_lost_pets, container, false);

        mMyLostPetsText = root.findViewById(R.id.myLostPetsText);
        mListView = root.findViewById(R.id.listView_my);
        mMyLostPets = new ArrayList<>();

        new FirebaseDatabaseHelper().readMyLost(new FirebaseDatabaseHelper.DataStatusLost() {
            @Override
            public void DataIsLoaded(ArrayList<LostPet> allLost, ArrayList<String> keys) {
                mMyLostPets = allLost;

                if (mMyLostPets != null) {
                    if (mMyLostPets.size() > 0) {
                        mMyLostPetsText.setVisibility(View.GONE);
                        Collections.sort(mMyLostPets, new Comparator<LostPet>() {
                            @Override
                            public int compare(LostPet lostPet1, LostPet lostPet2) {
                                return lostPet2.getDate_posted().compareTo(lostPet1.getDate_posted());
                            }
                        });
                        mAdapter = new LostPetsListAdapter(getActivity(), R.layout.layout_alllostpets_listitem, mMyLostPets);
                        mListView.setAdapter(mAdapter);
                    } else {
                        mMyLostPetsText.setText("No lost pets have been reported.");
                        mMyLostPetsText.setVisibility(View.VISIBLE);
                    }
                }

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mOnMyLostPetSelectedListener.onMyLostPetSelected(mMyLostPets.get(i));
                    }
                });
            }
            @Override
            public void DataIsInserted() {
            }
            @Override
            public void DataIsUpdated() {
            }
            @Override
            public void DataIsDeleted() {
            }
        });

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            mOnMyLostPetSelectedListener = (MyLostPetsFragment.OnMyLostPetSelectedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
        super.onAttach(context);
    }
}