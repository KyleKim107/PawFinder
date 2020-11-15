package com.example.pawfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {

    private Context mContext;
    private FavoritesAdapter mAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, ArrayList<Pet> favorites, ArrayList<String> keys) {
        mContext = context;
        mAdapter = new FavoritesAdapter(favorites, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mAdapter);
    }

    class FavoritesAdapter extends RecyclerView.Adapter<FavoriteItemView> {

        private ArrayList<Pet> mFavorites;
        private ArrayList<String> mKeys;

        public FavoritesAdapter(ArrayList<Pet> favorites, ArrayList<String> keys) {
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

    class FavoriteItemView extends RecyclerView.ViewHolder {

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, name + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(final Pet pet, String key) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(pet.getImage())
                    .into(image);
            name.setText(pet.getName());
            age.setText(pet.getAge());
            gender.setText(pet.getGender());
            this.key = key;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("FavoriteItemView", "onClick: clicked on: " + pet.getName());

                    Toast.makeText(mContext, pet.getName() + "!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
