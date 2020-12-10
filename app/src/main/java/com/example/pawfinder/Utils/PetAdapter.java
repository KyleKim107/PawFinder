package com.example.pawfinder.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetItemView> {

    private static final String TAG = "PetAdapter";

    public static ArrayList<PetfinderPet> mPets;
    private Context mContext;

    public PetAdapter(ArrayList<PetfinderPet> pets, Context context) {
        this.mPets = pets;
        this.mContext = context;
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
        Log.d("PetAdapter", "onBindViewHolder: called.");
        holder.bind(mPets.get(position));
    }

    @Override
    public int getItemCount() {
        return mPets.size();
    }

    class PetItemView extends RecyclerView.ViewHolder {

        ImageView image;
        TextView id, name, age, gender;
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

        public void bind(final PetfinderPet pet) {
            String url = "https://tameme.ru/static/img/catalog/default_pet.jpg";
            if (pet.getPhotos().size() > 0) {
                if (pet.getPhotos().get(0).getFull() == null) {
                    if (pet.getPhotos().get(0).getLarge() == null) {
                        if (pet.getPhotos().get(0).getMedium() == null) {
                            if (pet.getPhotos().get(0).getSmall() != null) {
                                url = pet.getPhotos().get(0).getSmall();
                            }
                        } else { url = pet.getPhotos().get(0).getMedium(); }
                    } else { url = pet.getPhotos().get(0).getLarge(); }
                } else { url = pet.getPhotos().get(0).getFull(); }
            }
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(image);
            id.setText(pet.getId());
            name.setText(pet.getName());
            age.setText(pet.getAge());
            gender.setText(pet.getGender());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("PetItemView", "onClick: clicked on: " + pet.getName());
                    Log.d(TAG, "onClick: Loading more pet information.");
                    ((MainActivity)mContext).onPetSelected(pet);
                }
            });
        }
    }
}
