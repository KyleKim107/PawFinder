package com.example.pawfinder.Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;

public class FirebaseDatabaseHelper {

    private static final String TAG = "FirebaseDatabaseHelper";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFavorites;
    private DatabaseReference mReferencePets;
    private DatabaseReference mReferenceAllLost;
    private DatabaseReference mReferenceMyLost;
    private StorageReference mStorageReference;

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

    public FirebaseDatabaseHelper() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mReferencePets = mDatabase.getReference("Pets");
        mReferenceFavorites = mDatabase.getReference("Users").child(user.getUid()).child("favorites");
        mReferenceAllLost = mDatabase.getReference().child("AllLostPets");
        mReferenceMyLost = mDatabase.getReference().child("MyLostPets").child(user.getUid());
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

    public void deleteFavorite(String key, final DataStatus dataStatus) {
        mReferenceFavorites.child(key).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsDeleted();
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

    public int getLostPetCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot.child("MyLostPets").child(user.getUid()).getChildren()){
            count++;
        }
        return count;
    }

    public void uploadNewLostPet(int count, final String petName,
                                 final String petGender, final String dateMissing, final String areaMissing,
                                 final String message, final String email, final String phone, final Bitmap bm) {

        Log.d(TAG, "uploadNewLostPet: uploading new pet photo: ");

        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference storageReference = mStorageReference
                .child("photos/users/" + user_id + "/pet_photo" + (count + 1));

        // Get bytes from bitmap
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        UploadTask uploadTask;
        uploadTask = storageReference.putBytes(bytes);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String firebaseUrl = task.getResult().toString();
                        addLostPetToDatabase(firebaseUrl, petName, petGender, dateMissing, areaMissing,
                                message, email, phone);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Photo upload failed.");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
            }
        });
    }

    private void addLostPetToDatabase(String url, String petName, String petGender,
                                    String dateMissing, String areaMissing, String message,
                                    String email, String phone) {
        Log.d(TAG, "addPhotoToDatabase: adding lost pet to database.");

        String newLostPetKey = mReferenceAllLost.push().getKey();
        LostPet lostPet = new LostPet();
        lostPet.setDate_posted(getTimestamp());
        lostPet.setImage_path(url);
        lostPet.setLost_pet_id(newLostPetKey);
        lostPet.setUser_id(user.getUid());
        lostPet.setPet_name(petName);
        lostPet.setPet_gender(petGender);
        lostPet.setDate_missing(dateMissing);
        lostPet.setArea_missing(areaMissing);
        lostPet.setMessage(message);
        lostPet.setEmail(email);
        lostPet.setPhone(phone);

        //insert into database
        mReferenceMyLost.child(newLostPetKey).setValue(lostPet);
        mReferenceAllLost.child(newLostPetKey).setValue(lostPet);
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
        return sdf.format(new Date());
    }
}
