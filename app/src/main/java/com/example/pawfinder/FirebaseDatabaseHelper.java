package com.example.pawfinder;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFavorites;
    private DatabaseReference mReferencePets;
    private DatabaseReference mReferenceAllLost;
    private DatabaseReference mReferenceMyLost;
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Pet> favorites = new ArrayList<>();
    private ArrayList<Pet> allLost = new ArrayList<>();
    private ArrayList<Pet> myLost = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(ArrayList<Pet> favorites, ArrayList<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper(String ref, String id) {
        mDatabase = FirebaseDatabase.getInstance();
        if (ref.equals("pets")) {
            mReferencePets = mDatabase.getReference("Pets");
        } else if (ref.equals("favorites")){
            mReferenceFavorites = mDatabase.getReference("Users").child(id).child("favorites");
        } else if (ref.equals("myLost")) {
            mReferenceMyLost = mDatabase.getReference("Users").child(id).child("lost");
        } else { // ref is "allLost
            mReferenceAllLost = mDatabase.getReference("Lost");
        }
    }

    public void readPets(final DataStatus dataStatus) {
        mReferencePets.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pets.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Pet pet = keyNode.getValue(Pet.class);
                    pets.add(pet);
                }
                dataStatus.DataIsLoaded(pets, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readFavorites(final DataStatus dataStatus) {
        mReferenceFavorites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favorites.clear();
                CardStackConfig.ids.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Pet pet = keyNode.getValue(Pet.class);
                    favorites.add(pet);
                    CardStackConfig.ids.add(pet.getId());
                }
                dataStatus.DataIsLoaded(favorites, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readAllLost(final DataStatus dataStatus) {
        mReferenceAllLost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allLost.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Pet pet = keyNode.getValue(Pet.class);
                    allLost.add(pet);
                }
                dataStatus.DataIsLoaded(allLost, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readMyLost(final DataStatus dataStatus) {
        mReferenceMyLost.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myLost.clear();
                ArrayList<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Pet pet = keyNode.getValue(Pet.class);
                    myLost.add(pet);
                }
                dataStatus.DataIsLoaded(myLost, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addFavorite(Pet pet, final DataStatus dataStatus) {
        if (!CardStackConfig.ids.contains(pet.getId())) {
            String key = mReferenceFavorites.push().getKey();
            mReferenceFavorites.child(key).setValue(pet)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dataStatus.DataIsInserted();
                        }
                    });
        }
    }

    // TODO: figure out how to add to both my lost and all lost
    public void addMyLost(Pet pet, final DataStatus dataStatus) {
        String key = mReferenceFavorites.push().getKey();
        mReferenceFavorites.child(key).setValue(pet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });
    }

    public void deleteFavorite(String key, final DataStatus dataStatus) {
        mReferenceFavorites.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
                    }
                });
    }
}
