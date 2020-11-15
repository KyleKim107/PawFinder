package com.example.pawfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CardStackConfig {
    private static final String TAG = "CardStackConfig";
    private Context mContext;
    private PetAdapter mAdapter;
    private CardStackLayoutManager manager;
    CardStackView mCardStackView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferencePets;

    public void setConfig(CardStackView cardStackView, Context context, ArrayList<Pet> pets, ArrayList<String> keys) {
        // Firebase
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        final View[] root = new View[1];
        mContext = context;
        mAdapter = new PetAdapter(pets, keys);
        mCardStackView = cardStackView;
        manager = new CardStackLayoutManager(mContext, new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
//                    Toast.makeText(mContext, "Added to favorites!", Toast.LENGTH_SHORT).show();

                    final TextView id_tv = root[0].findViewById(R.id.item_id);
                    final TextView name_tv = root[0].findViewById(R.id.item_name);
                    final TextView age_tv = root[0].findViewById(R.id.item_age);
                    final TextView gender_tv = root[0].findViewById(R.id.item_gender);

                    String id = id_tv.getText().toString();
                    mReferencePets = mDatabase.getReference("Pets").child(id);
                    mReferencePets.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                        @Override
                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                String id = id_tv.getText().toString();
                                String image = (String) dataSnapshot.child("image").getValue();
                                String name = name_tv.getText().toString();
                                String age = age_tv.getText().toString();
                                String gender = gender_tv.getText().toString();
                            Toast.makeText(mContext, "ID is: " + id, Toast.LENGTH_SHORT).show();

                                Pet favorite = new Pet(id, image, name, age, gender);
                                new FirebaseDatabaseHelper("favorites", user.getUid()).addFavorite(favorite, new FirebaseDatabaseHelper.DataStatus() {
                                    @Override
                                    public void DataIsLoaded(ArrayList<Pet> favorites, ArrayList<String> keys) {
                                    }
                                    @Override
                                    public void DataIsInserted() {
                                        // pet was added to favorites
                                    }
                                    @Override
                                    public void DataIsUpdated() {
                                    }
                                    @Override
                                    public void DataIsDeleted() {
                                    }
                                });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                if (manager.getTopPosition() == mAdapter.getItemCount()){
                    Toast.makeText(mContext, "You've reached the end!", Toast.LENGTH_SHORT).show();
//                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
                root[0] = view;
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        mCardStackView.setLayoutManager(manager);
        mCardStackView.setAdapter(mAdapter);
        mCardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    class PetAdapter extends RecyclerView.Adapter<PetItemView> {

        private ArrayList<Pet> mPets;
        private ArrayList<String> mKeys;

        public PetAdapter(ArrayList<Pet> pets, ArrayList<String> keys) {
            this.mPets = pets;
            this.mKeys = keys;
        }

        @NonNull
        @Override
        public PetItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_card, parent, false);
            return new PetItemView(view);
        }

        @Override
        public void onBindViewHolder(PetItemView holder, int position) {
            Log.d("FavoritesAdapter", "onBindViewHolder: called.");
            holder.bind(mPets.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mPets.size();
        }
    }

    class PetItemView extends RecyclerView.ViewHolder {

        ImageView image;
        TextView id, name, age, gender;
        String key;
        CardView layout;

        public PetItemView(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.item_id);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            age = itemView.findViewById(R.id.item_age);
            gender = itemView.findViewById(R.id.item_gender);
            layout = itemView.findViewById(R.id.item_layout);
        }

        public void bind(final Pet pet, String key) {
            Picasso.get()
                    .load(pet.getImage())
                    .fit()
                    .centerCrop()
                    .into(image);
            id.setText(pet.getId());
            name.setText(pet.getName());
            age.setText(pet.getAge());
            gender.setText(pet.getGender());
            this.key = key;

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("PetItemView", "onClick: clicked on: " + pet.getName());

                    Toast.makeText(mContext, pet.getName() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
