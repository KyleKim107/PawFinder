package com.example.pawfinder.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {

    private static final String TAG = "RecyclerViewConfig";

    private Context mContext;
    private FavoritesAdapter mAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<PetfinderPet> favorites, ArrayList<String> keys) {
        mContext = context;
        mAdapter = new FavoritesAdapter(favorites, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
    }

    public class FavoritesAdapter extends RecyclerView.Adapter<FavoriteItemView> {

        private ArrayList<PetfinderPet> mFavorites;
        private ArrayList<String> mKeys;

        public FavoritesAdapter(ArrayList<PetfinderPet> favorites, ArrayList<String> keys) {
            this.mFavorites = favorites;
            this.mKeys = keys;
        }

        @NonNull
        @Override
        public FavoriteItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.favorite_pets_view, parent, false);
            return new FavoriteItemView(view);
        }

        @Override
        public void onBindViewHolder(FavoriteItemView holder, int position) {
            Log.d("FavoritesAdapter", "onBindViewHolder: called.");
            holder.bind(mFavorites.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mFavorites.size();
        }
    }

    public class FavoriteItemView extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, age, gender;
        String key;
        CardView layout;
        public RelativeLayout viewBackground, viewForeground;

        public FavoriteItemView(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.recyclerImage);
            name = itemView.findViewById(R.id.recyclerName);
            age = itemView.findViewById(R.id.recyclerAge);
            gender = itemView.findViewById(R.id.recyclerGender);
            layout = itemView.findViewById(R.id.recyclerLayout);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        public void bind(final PetfinderPet pet, String key) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(pet.getPhotos().get(0).getFull())
                    .into(image);
            name.setText(pet.getName());
            age.setText(pet.getAge());
            gender.setText(pet.getGender());
            this.key = key;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("FavoriteItemView", "onClick: clicked on: " + pet.getName());
                    Log.d(TAG, "onClick: Loading more pet information.");
                    ((MainActivity)mContext).onFavoritePetSelected(pet);
                }
            });
        }
    }
}
