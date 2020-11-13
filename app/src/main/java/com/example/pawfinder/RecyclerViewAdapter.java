package com.example.pawfinder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImages = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAges = new ArrayList<>();
    private ArrayList<String> mGenders = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mImages, ArrayList<String> mNames, ArrayList<String> mAges, ArrayList<String> mGenders) {
        this.mImages = mImages;
        this.mNames = mNames;
        this.mAges = mAges;
        this.mGenders = mGenders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_pets_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.name.setText(mNames.get(position));
        holder.age.setText(mAges.get(position));
        holder.gender.setText(mGenders.get(position));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mNames.get(position));

                Toast.makeText(mContext, mNames.get(position) + "!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, age, gender;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.recyclerImage);
            name = itemView.findViewById(R.id.recyclerName);
            age = itemView.findViewById(R.id.recyclerAge);
            gender = itemView.findViewById(R.id.recyclerGender);
            layout = itemView.findViewById(R.id.recyclerLayout);
        }

    }
}
