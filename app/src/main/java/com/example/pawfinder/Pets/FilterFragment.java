package com.example.pawfinder.Pets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.pawfinder.Lost.ViewLostPetFragment;
import com.example.pawfinder.Models.PetfinderPet;
import com.example.pawfinder.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterFragment extends Fragment {

    private static final String TAG = "FilterFragment";

    private HashMap<String, Boolean> filtersApplied;
    private HashMap<String, String> params;

    private View root;

    ImageView mBackArrow;
    ToggleButton mDogToggle, mCatToggle, mRabbitToggle, mFurryToggle, mHorseToggle, mBirdToggle, mScalesToggle, mBarnyardToggle;
    ToggleButton mSmallToggle, mMediumToggle, mLargeToggle, mXLargeToggle;
    ToggleButton mMaleToggle, mFemaleToggle;
    ToggleButton mBabyToggle, mYoungToggle, mAdultToggle, mSeniorToggle;

    private String oldParams;
    private String newParams;

    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_filter, container, false);

        mDogToggle = root.findViewById(R.id.type_dog);
        mCatToggle = root.findViewById(R.id.type_cat);
        mRabbitToggle = root.findViewById(R.id.type_rabbit);
        mFurryToggle = root.findViewById(R.id.type_furry);
        mHorseToggle = root.findViewById(R.id.type_horse);
        mBirdToggle = root.findViewById(R.id.type_bird);
        mScalesToggle = root.findViewById(R.id.type_scales);
        mBarnyardToggle = root.findViewById(R.id.type_barnyard);

        mSmallToggle = root.findViewById(R.id.size_small);
        mMediumToggle = root.findViewById(R.id.size_medium);
        mLargeToggle = root.findViewById(R.id.size_large);
        mXLargeToggle = root.findViewById(R.id.size_xlarge);

        mMaleToggle = root.findViewById(R.id.gender_male);
        mFemaleToggle = root.findViewById(R.id.gender_female);

        mBabyToggle = root.findViewById(R.id.age_baby);
        mYoungToggle = root.findViewById(R.id.age_young);
        mAdultToggle = root.findViewById(R.id.age_adult);
        mSeniorToggle = root.findViewById(R.id.age_senior);

        setTypeToggleListener();

        mBackArrow = root.findViewById(R.id.backArrow_filter);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkToggles();
                newParams = "";
                if (params != null) {
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        newParams += entry.getValue();
                    }
                }
                if (oldParams.equals(newParams) || params == null) {
                    requireActivity().onBackPressed();
                } else {
                    Log.d(TAG, "onBackArrowClicked: Navigating back to Pets Fragment");
                    PetsFragment fragment = new PetsFragment();
                    Bundle args = new Bundle();
                    args.putSerializable("filtersApplied", filtersApplied);
                    args.putSerializable("params", params);
                    fragment.setArguments(args);

                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main_activity_container, fragment, "navigation_pets");
                    transaction.addToBackStack("navigation_pets");
                    transaction.commit();
                }
            }
        });

        try {
            filtersApplied = getFiltersFromBundle();
            oldParams = "";
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    oldParams += entry.getValue();
                }
            }
            setToggles();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: lost pet was null from bundle " + e.getMessage());
        }

        return root;
    }

    private void setTypeToggleListener() {
        mDogToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mCatToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mRabbitToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mFurryToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mHorseToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mBirdToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mScalesToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mBarnyardToggle.setChecked(false);
                }
            }
        });
        mBarnyardToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mDogToggle.setChecked(false);
                    mCatToggle.setChecked(false);
                    mRabbitToggle.setChecked(false);
                    mFurryToggle.setChecked(false);
                    mHorseToggle.setChecked(false);
                    mBirdToggle.setChecked(false);
                    mScalesToggle.setChecked(false);
                }
            }
        });
    }

    private void setToggles() {
        if (filtersApplied.get("dog")) { mDogToggle.setChecked(true); } else { mDogToggle.setChecked(false);}
        if (filtersApplied.get("cat")) { mCatToggle.setChecked(true); } else { mCatToggle.setChecked(false);}
        if (filtersApplied.get("rabbit")) { mRabbitToggle.setChecked(true); } else { mRabbitToggle.setChecked(false);}
        if (filtersApplied.get("small-furry")) { mFurryToggle.setChecked(true); } else { mFurryToggle.setChecked(false);}
        if (filtersApplied.get("horse")) { mHorseToggle.setChecked(true); } else { mHorseToggle.setChecked(false);}
        if (filtersApplied.get("bird")) { mBirdToggle.setChecked(true); } else { mBirdToggle.setChecked(false);}
        if (filtersApplied.get("scales-fins-other")) { mScalesToggle.setChecked(true); } else { mScalesToggle.setChecked(false);}
        if (filtersApplied.get("barnyard")) { mBarnyardToggle.setChecked(true); } else { mBarnyardToggle.setChecked(false);}
        if (filtersApplied.get("small")) { mSmallToggle.setChecked(true); } else { mSmallToggle.setChecked(false);}
        if (filtersApplied.get("medium")) { mMediumToggle.setChecked(true); } else { mMediumToggle.setChecked(false);}
        if (filtersApplied.get("large")) { mLargeToggle.setChecked(true); } else { mLargeToggle.setChecked(false);}
        if (filtersApplied.get("xlarge")) { mXLargeToggle.setChecked(true); } else { mXLargeToggle.setChecked(false);}
        if (filtersApplied.get("male")) { mMaleToggle.setChecked(true); } else { mMaleToggle.setChecked(false);}
        if (filtersApplied.get("female")) { mFemaleToggle.setChecked(true); } else { mFemaleToggle.setChecked(false);}
        if (filtersApplied.get("baby")) { mBabyToggle.setChecked(true); } else { mBabyToggle.setChecked(false);}
        if (filtersApplied.get("young")) { mYoungToggle.setChecked(true); } else { mYoungToggle.setChecked(false);}
        if (filtersApplied.get("adult")) { mAdultToggle.setChecked(true); } else { mAdultToggle.setChecked(false);}
        if (filtersApplied.get("senior")) { mSeniorToggle.setChecked(true); } else { mSeniorToggle.setChecked(false);}
    }

    private void checkToggles() {
        params = new HashMap<>();
        String type = "";
        if (mDogToggle.isChecked()) {
            filtersApplied.replace("dog", true);
            type += "dog";
        } else {
            filtersApplied.replace("dog", false);
        }
        if (mCatToggle.isChecked()) {
            filtersApplied.replace("cat", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "cat";
        } else {
            filtersApplied.replace("cat", false);
        }
        if (mRabbitToggle.isChecked()) {
            filtersApplied.replace("rabbit", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "rabbit";
        } else {
            filtersApplied.replace("rabbit", false);
        }
        if (mFurryToggle.isChecked()) {
            filtersApplied.replace("small-furry", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "small-furry";
        } else {
            filtersApplied.replace("small-furry", false);
        }
        if (mHorseToggle.isChecked()) {
            filtersApplied.replace("horse", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "horse";
        } else {
            filtersApplied.replace("horse", false);
        }
        if (mBirdToggle.isChecked()) {
            filtersApplied.replace("bird", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "bird";
        } else {
            filtersApplied.replace("bird", false);
        }
        if (mScalesToggle.isChecked()) {
            filtersApplied.replace("scales-fins-other", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "scales-fins-other";
        } else {
            filtersApplied.replace("scales-fins-other", false);
        }
        if (mBarnyardToggle.isChecked()) {
            filtersApplied.replace("barnyard", true);
            if (!type.equals("")) {
                type += ",";
            }
            type += "barnyard";
        } else {
            filtersApplied.replace("barnyard", false);
        }

        String size = "";
        if (mSmallToggle.isChecked()) {
            filtersApplied.replace("small", true);
            size += "small";
        } else {
            filtersApplied.replace("small", false);
        }
        if (mMediumToggle.isChecked()) {
            filtersApplied.replace("medium", true);
            if (!size.equals("")) {
                size += ",";
            }
            size += "medium";
        } else {
            filtersApplied.replace("medium", false);
        }
        if (mLargeToggle.isChecked()) {
            filtersApplied.replace("large", true);
            if (!size.equals("")) {
                size += ",";
            }
            size += "large";
        } else {
            filtersApplied.replace("large", false);
        }
        if (mXLargeToggle.isChecked()) {
            filtersApplied.replace("xlarge", true);
            if (!size.equals("")) {
                size += ",";
            }
            size += "xlarge";
        } else {
            filtersApplied.replace("xlarge", false);
        }

        String gender = "";
        if (mMaleToggle.isChecked()) {
            filtersApplied.replace("male", true);
            gender += "male";
        } else {
            filtersApplied.replace("male", false);
        }
        if (mFemaleToggle.isChecked()) {
            filtersApplied.replace("female", true);
            if (!gender.equals("")) {
                gender += ",";
            }
            gender += "female";
        } else {
            filtersApplied.replace("female", false);
        }

        String age = "";
        if (mBabyToggle.isChecked()) {
            filtersApplied.replace("baby", true);
            age += "baby";
        } else {
            filtersApplied.replace("baby", false);
        }
        if (mYoungToggle.isChecked()) {
            filtersApplied.replace("young", true);
            if (!age.equals("")) {
                age += ",";
            }
            age += "young";
        } else {
            filtersApplied.replace("young", false);
        }
        if (mAdultToggle.isChecked()) {
            filtersApplied.replace("adult", true);
            if (!age.equals("")) {
                age += ",";
            }
            age += "adult";
        } else {
            filtersApplied.replace("adult", false);
        }
        if (mSeniorToggle.isChecked()) {
            filtersApplied.replace("senior", true);
            if (!age.equals("")) {
                age += ",";
            }
            age += "senior";
        } else {
            filtersApplied.replace("senior", false);
        }

        if (!type.equals("")) {
            params.put("type", type);
        }
        if (!size.equals("")) {
            params.put("size", size);
        }
        if (!gender.equals("")) {
            params.put("gender", gender);
        }
        if (!age.equals("")) {
            params.put("age", age);
        }
    }

    private HashMap<String, Boolean> getFiltersFromBundle() {
        Log.d(TAG, "getFiltersFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            params = (HashMap<String, String>) bundle.getSerializable("params");
            return (HashMap<String, Boolean>) bundle.getSerializable("filtersApplied");
        } else {
            return null;
        }
    }
}