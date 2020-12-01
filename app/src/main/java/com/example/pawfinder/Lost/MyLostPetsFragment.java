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

import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        setUpListView();

        return root;
    }

    private void setUpListView() {
        Log.d(TAG, "setUpListView: Setting up lost pet list.");

        final ArrayList<LostPet> lostPets = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("MyLostPets")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    LostPet lostPet = new LostPet();
                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                    try {
                        lostPet.setDate_posted(objectMap.get("date_posted").toString());
                        lostPet.setImage_path(objectMap.get("image_path").toString());
                        lostPet.setLost_pet_id(objectMap.get("lost_pet_id").toString());
                        lostPet.setUser_id(objectMap.get("user_id").toString());
                        lostPet.setPet_name(objectMap.get("pet_name").toString());
                        lostPet.setPet_gender(objectMap.get("pet_gender").toString());
                        lostPet.setDate_missing(objectMap.get("date_missing").toString());
                        lostPet.setArea_missing(objectMap.get("area_missing").toString());
                        lostPet.setMessage(objectMap.get("message").toString());
                        lostPet.setEmail(objectMap.get("email").toString());
                        lostPet.setPhone(objectMap.get("phone").toString());

                        lostPets.add(lostPet);

                    }catch(NullPointerException e){
                        Log.e(TAG, "onDataChange: NullPointerException: " + e.getMessage() );
                    }
                }

//                //setup our image grid
//                int gridWidth = getResources().getDisplayMetrics().widthPixels;
//                int imageWidth = gridWidth/NUM_GRID_COLUMNS;
//                gridView.setColumnWidth(imageWidth);
//
//                ArrayList<String> imgUrls = new ArrayList<String>();
//                for(int i = 0; i < lostPets.size(); i++){
//                    imgUrls.add(lostPets.get(i).getImage_path());
//                }
//                GridImageAdapter adapter = new GridImageAdapter(getActivity(),R.layout.layout_grid_imageview,
//                        "", imgUrls);
//                gridView.setAdapter(adapter);
//
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mOnGridImageSelectedListener.onGridImageSelected(photos.get(position), ACTIVITY_NUM);
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }
}