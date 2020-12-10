package com.example.pawfinder.Pets;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewPagerImageFragment extends Fragment {

    // widgets
    private ImageView mImage;

    // vars
    private PetfinderPet.PetfinderPetPhotos mPhoto;
    private String mUrl;

    public static ViewPagerImageFragment getInstance(PetfinderPet.PetfinderPetPhotos photo){
        ViewPagerImageFragment fragment = new ViewPagerImageFragment();

        if(photo != null){
            Bundle bundle = new Bundle();
            bundle.putParcelable("photo", photo);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public static ViewPagerImageFragment getInstance(String url){
        ViewPagerImageFragment fragment = new ViewPagerImageFragment();

        if(url != null){
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            if (getArguments().getString("url") != null) {
                mUrl = getArguments().getString("url");
            } else {
                mPhoto = getArguments().getParcelable("photo");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mImage = view.findViewById(R.id.viewpager_image);
        init();
    }

    private void init(){
        if(mPhoto != null){
            String url = "https://tameme.ru/static/img/catalog/default_pet.jpg";
            if (mPhoto.getFull() == null) {
                if (mPhoto.getLarge() == null) {
                    if (mPhoto.getMedium() == null) {
                        if (mPhoto.getSmall() != null) { url = mPhoto.getSmall(); }
                    } else { url = mPhoto.getMedium(); }
                } else { url = mPhoto.getLarge(); }
            } else { url = mPhoto.getFull(); }

            Glide.with(requireActivity())
                .asBitmap()
                .load(url)
                .into(mImage);
        } else {
            Glide.with(requireActivity())
                    .asBitmap()
                    .load(mUrl)
                    .into(mImage);
        }
    }
}
