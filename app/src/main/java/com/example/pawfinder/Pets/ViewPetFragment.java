package com.example.pawfinder.Pets;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.ImagePagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPetFragment extends Fragment {

    private final String TAG = "ViewPetFragment";

    private PetfinderPet mPet;
    private ImageView mImage, mBackArrow;
    private TextView mName, mPosted;
    private TextView mBreedText, mBreed;
    private TextView mPhysicalText, mSize, mGender, mAge, mCoat, mColor;
    private TextView mHealthText, mSpayedNeutered, mDeclawed, mSpecialNeeds, mVaccinations;
    private TextView mBehavioralText, mHouseTrained;
    private TextView mMeetPetText, mDescription;

    private ViewPager mMyViewPager;
    private TabLayout mTabLayout;

    private View root;

    public ViewPetFragment() {
        // Required empty public constructor
        super();
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_view_pet, container, false);

        mTabLayout = root.findViewById(R.id.tab_layout);
        mMyViewPager = root.findViewById(R.id.view_pager);

        // Title Info
        mName = root.findViewById(R.id.pet_name_view);
        mPosted = root.findViewById(R.id.pet_posted_view);

        // Breed
        mBreedText = root.findViewById(R.id.text_breed_view);
        mBreed = root.findViewById(R.id.petbreed_view);

        // Physical Characteristics
        mPhysicalText = root.findViewById(R.id.text_physical_view);
        mSize = root.findViewById(R.id.petsize_view);
        mGender = root.findViewById(R.id.petgender_view);
        mAge = root.findViewById(R.id.petage_view);
        mCoat = root.findViewById(R.id.petcoat_view);
        mColor = root.findViewById(R.id.petcolor_view);

        // Health
        mHealthText = root.findViewById(R.id.text_health_view);
        mSpayedNeutered = root.findViewById(R.id.spayedneutered_view);
        mDeclawed = root.findViewById(R.id.declawed_view);
        mSpecialNeeds = root.findViewById(R.id.specialneeds_view);
        mVaccinations = root.findViewById(R.id.vaccinations_view);

        // Behavioral Characteristics
        mBehavioralText = root.findViewById(R.id.text_behavioral_view);
        mHouseTrained = root.findViewById(R.id.housetrained_view);

        // Description
        mMeetPetText = root.findViewById(R.id.text_meet_view);
        mDescription = root.findViewById(R.id.petdescription_view);

        try {
            mPet = getPetFromBundle();
//            setupWidgets();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: pet was null from bundle " + e.getMessage());
        }
        setupWidgets();
        // Toolbar
        mBackArrow = root.findViewById(R.id.backArrow_viewPet);
        mBackArrow.setRotation(90);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return root;
    }

    private void setupWidgets() {
        // Image
        initImages();

        // Name
        if (mPet.getName() != null) {
            mName.setText(mPet.getName());
        }

        // Date posted
        String timestampDiff = getTimestampDifference();
        if(!timestampDiff.equals("0")){
            if(timestampDiff.equals("1")) {
                mPosted.setText("POSTED " + timestampDiff + " DAY AGO");
            } else {
                mPosted.setText("POSTED " + timestampDiff + " DAYS AGO");
            }
        } else{
            mPosted.setText("POSTED TODAY");
        }

        // Breed
        PetfinderPet.PetfinderPetBreeds breeds = mPet.getBreeds();
        if (breeds.getUnknown().equals("true")) {
            mBreed.setText("Breed: Unknown");
        } else {
            if (breeds.getMixed().equals("true")) {
                if (breeds.getPrimary() != null) {
                    if (breeds.getSecondary() != null) {
                        mBreed.setText("Breed: " + breeds.getPrimary() + " & " + breeds.getSecondary() + " Mix");
                    } else {
                        mBreed.setText("Breed: " + breeds.getPrimary());
                    }
                } else {
                    mBreed.setText("Breed: Mix");
                }
            } else {
                if (breeds.getPrimary() != null) {
                    mBreed.setText("Breed: " + breeds.getPrimary());
                } else {
                    mBreed.setVisibility(View.GONE);
                    mBreedText.setVisibility(View.GONE);
                }
            }
        }

        // Physical Characteristics
        int physical_gone = 0;
        if (mPet.getSize() != null) {
            mSize.setText("Size: " + mPet.getSize());
        } else {
            mSize.setVisibility(View.GONE);
            physical_gone++;
        }

        if (mPet.getGender() != null) {
            mGender.setText("Gender: " + mPet.getGender());
        } else {
            mGender.setVisibility(View.GONE);
            physical_gone++;
        }

        if (mPet.getAge() != null) {
            mAge.setText("Age: " + mPet.getAge());
        } else {
            mAge.setVisibility(View.GONE);
            physical_gone++;
        }

        if (mPet.getCoat() != null) {
            mCoat.setText("Coat: " + mPet.getCoat());
        } else {
            mCoat.setVisibility(View.GONE);
            physical_gone++;
        }

        PetfinderPet.PetfinderPetColors colors = mPet.getColors();
        if (colors.getPrimary() != null) {
            if (colors.getSecondary() != null) {
                if (colors.getTertiary() != null) {
                    mColor.setText("Colors: " + colors.getPrimary() + ", " + colors.getSecondary()
                            + ", & " + colors.getTertiary());
                } else {
                    mColor.setText("Colors: " + colors.getPrimary() + " & " + colors.getSecondary());
                }
            } else {
                mColor.setText("Color: " + colors.getPrimary());
            }
        } else {
            mColor.setVisibility(View.GONE);
            physical_gone++;
        }
        if (physical_gone == 5) {
            mPhysicalText.setVisibility(View.GONE);
        }

        // Health
        int health_gone = 0;
        PetfinderPet.PetfinderPetAttributes attributes = mPet.getAttributes();
        if (attributes.getSpayed_neutered() != null) {
            if (attributes.getSpayed_neutered().equals("false")) {
                mSpayedNeutered.setText("Not Spayed/Neutered");
            } else {
                mSpayedNeutered.setText("Spayed/Neutered");
            }
        } else {
            mSpayedNeutered.setVisibility(View.GONE);
            health_gone++;
        }

        if (attributes.getDeclawed() != null) {
            if (attributes.getDeclawed().equals("true")) {
                mDeclawed.setText("Declawed");
            } else {
                mDeclawed.setVisibility(View.GONE);
                health_gone++;
            }
        } else {
            mDeclawed.setVisibility(View.GONE);
            health_gone++;
        }

        if (attributes.getSpecial_needs() != null) {
            if (attributes.getSpecial_needs().equals("true")) {
                mSpecialNeeds.setText("Special Needs");
            } else {
                mSpecialNeeds.setVisibility(View.GONE);
                health_gone++;
            }
        } else {
            mSpecialNeeds.setVisibility(View.GONE);
            health_gone++;
        }

        if (attributes.getShots_current() != null) {
            if (attributes.getShots_current().equals("true")) {
                mVaccinations.setText("Vaccinations up-to-date");
            } else {
                mVaccinations.setText("Vaccinations not up-to-date");
            }
        } else {
            mVaccinations.setVisibility(View.GONE);
            health_gone++;
        }

        if (health_gone == 4) {
            mHealthText.setVisibility(View.GONE);
        }

        // Behavioral Characteristics
        if (attributes.getHouse_trained() != null) {
            if (attributes.getHouse_trained().equals("true")) {
                mHouseTrained.setText("House trained");
            } else {
                mHouseTrained.setText("Not house trained");
            }
        } else {
            mHouseTrained.setVisibility(View.GONE);
            mBehavioralText.setVisibility(View.GONE);
        }

        // Description
        if (mPet.getDescription() != null) {
            if (mPet.getName() != null) {
                mMeetPetText.setText("MEET " + mPet.getName().toUpperCase());
                mDescription.setText(mPet.getDescription());
            }
        } else {
            mMeetPetText.setVisibility(View.GONE);
            mDescription.setVisibility(View.GONE);
        }
    }

    private void initImages() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        List<PetfinderPet.PetfinderPetPhotos> photos = mPet.getPhotos();
        for(PetfinderPet.PetfinderPetPhotos photo : photos){
            ViewPagerImageFragment fragment = ViewPagerImageFragment.getInstance(photo);
            fragments.add(fragment);
        }
        if (photos.size() == 0) {
            String url = "https://tameme.ru/static/img/catalog/default_pet.jpg";
            ViewPagerImageFragment fragment = ViewPagerImageFragment.getInstance(url);
            fragments.add(fragment);
        }
        ImagePagerAdapter pagerAdapter = new ImagePagerAdapter(requireActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        mMyViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mMyViewPager, true);
    }

    private String getTimestampDifference(){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = mPet.getPublished_at();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }

    private PetfinderPet getPetFromBundle() {
        Log.d(TAG, "getLostPetFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("PET");
        } else {
            return null;
        }
    }
}