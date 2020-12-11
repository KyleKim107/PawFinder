package com.example.pawfinder.Lost;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.example.pawfinder.Utils.LostPetsListAdapter;
import com.example.pawfinder.Utils.RecyclerViewConfig;
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
        mListView = root.findViewById(R.id.listView_all);
        mAllLostPets = new ArrayList<>();

        new FirebaseDatabaseHelper().readAllLost(new FirebaseDatabaseHelper.DataStatusLost() {
            @Override
            public void DataIsLoaded(ArrayList<LostPet> allLost, ArrayList<String> keys) {
                mAllLostPets = allLost;

                if (mAllLostPets != null) {
                    if (mAllLostPets.size() > 0) {
                        mAllLostPetsText.setVisibility(View.GONE);
                        Collections.sort(mAllLostPets, new Comparator<LostPet>() {
                            @Override
                            public int compare(LostPet lostPet1, LostPet lostPet2) {
                                return lostPet2.getDate_posted().compareTo(lostPet1.getDate_posted());
                            }
                        });
                    } else {
                        mAllLostPetsText.setText("No lost pets have been reported.");
                        mAllLostPetsText.setVisibility(View.VISIBLE);
                    }
                    mAdapter = new LostPetsListAdapter(getActivity(), R.layout.layout_alllostpets_listitem, mAllLostPets);
                    mAdapter.setEllipsesInvisible();
                    mListView.setAdapter(mAdapter);
                }

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        mOnAllLostPetSelectedListener.onAllLostPetSelected(mAllLostPets.get(i));
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
            mOnAllLostPetSelectedListener = (OnAllLostPetSelectedListener) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
        super.onAttach(context);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        final Fragment f = new ViewLostPetFragment();
//
//        if(getView() == null){
//            return;
//        }
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    FragmentTransaction trans = requireActivity().getSupportFragmentManager().beginTransaction();
//                    trans.replace(R.id.main_activity_container, f);
//                    trans.addToBackStack(null);
//                    trans.commit();
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
}