package com.example.pawfinder.Lost;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.Models.Pet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ViewLostPetFragment extends Fragment {

    private final String TAG = "ViewLostPetFragment";

    private LostPet mLostPet;
    private RoundedImageView mPetImage;
    private ImageView mEllipses, mBackArrow;
    private TextView mPetName, mPetPosted, mPetGender, mPetType, mLastSeen, mPetArea, mPetDate,
                        mEmail, mPhone, mAdditionalInfo, mMessage;
    private Boolean myLost;

    public ViewLostPetFragment() {
        // Required empty public constructor
        super();
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_lost_pet, container, false);

        // Main Info
        mPetImage = root.findViewById(R.id.pet_image_view);
        mPetName = root.findViewById(R.id.pet_name_view);
        mPetPosted = root.findViewById(R.id.pet_posted_view);

        // Characteristics
        mPetGender = root.findViewById(R.id.pet_gender_view);
        mPetType = root.findViewById(R.id.pet_type_view);

        // Last Seen
        mLastSeen = root.findViewById(R.id.last_seen_view);
        mPetArea = root.findViewById(R.id.pet_area_view);
        mPetDate = root.findViewById(R.id.pet_date_view);

        // Contact Info
        mEmail = root.findViewById(R.id.contact_email_view);
        mPhone = root.findViewById(R.id.contact_phone_view);

        // Additional Info
        mAdditionalInfo = root.findViewById(R.id.additional_info_view);
        mMessage = root.findViewById(R.id.message_view);

        try {
            mLostPet = getLostPetFromBundle();
            Glide.with(getActivity())
                    .asBitmap()
                    .load(mLostPet.getImage_path())
                    .into(mPetImage);
            setupWidgets();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: lost pet was null from bundle " + e.getMessage());
        }

        // Toolbar
        mBackArrow = root.findViewById(R.id.backArrow_viewLostPet);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        mEllipses = root.findViewById(R.id.viewLostPetEllipses);
        if (!myLost) {
            mEllipses.setVisibility(View.GONE);
        }
        mEllipses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupMenu();
            }
        });

        return root;
    }

    private void setupWidgets() {
        // Name
        mPetName.setText(mLostPet.getPet_name());

        // Date posted
        String timestampDiff = getTimestampDifference();
        if(!timestampDiff.equals("0")){
            if(timestampDiff.equals("1")) {
                mPetPosted.setText(timestampDiff + " DAY AGO");
            } else {
                mPetPosted.setText(timestampDiff + " DAYS AGO");
            }
        } else{
            mPetPosted.setText("TODAY");
        }

        // Gender
        if (mLostPet.getPet_gender().equals("")) {
            mPetGender.setVisibility(View.GONE);
        } else {
            mPetGender.setText("Gender: " + mLostPet.getPet_gender());
        }

        // Type
        mPetType.setText("Type: " + mLostPet.getPet_type());

        // Last Seen / Recently Found
        if (mLostPet.getStatus().equals("missing")) {
            mLastSeen.setText("LAST SEEN");
        } else {
            mLastSeen.setText("RECENTLY FOUND");
        }

        // Area
        mPetArea.setText("Area: " + mLostPet.getArea_missing());

        // Date
        mPetDate.setText("Date: " + mLostPet.getDate_missing());

        // Email
        if (myLost) {
            mEmail.setText("Email: " + mLostPet.getEmail());
        } else {
            mEmail.setText("Email: Tap to view");
            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mEmail.setText("Email: " + mLostPet.getEmail());
                }
            });
        }

        // Phone
        if (mLostPet.getPhone().equals("")) {
            mPhone.setVisibility(View.GONE);
        } else {
            if (myLost) {
                mPhone.setText("Phone: " + mLostPet.getPhone());
            } else {
                mPhone.setText("Phone: Tap to view");
                mPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPhone.setText("Phone: " + mLostPet.getPhone());
                    }
                });
            }
        }

        // Message
        if (mLostPet.getMessage().equals("")) {
            mAdditionalInfo.setVisibility(View.GONE);
            mMessage.setVisibility(View.GONE);
        } else {
            mMessage.setText("Message: " + mLostPet.getMessage());
        }
    }

    private String getTimestampDifference(){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = mLostPet.getDate_posted();
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
            difference = "0";
        }
        return difference;
    }

    private void createPopupMenu() {
        PopupMenu pm = new PopupMenu(getActivity(), mEllipses);
        pm.getMenuInflater().inflate(R.menu.lostpet_menu, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_lostpet:
                        // Navigate to edit missing or found pet
                        if (mLostPet.getStatus().equals("missing")) {
                            EditMissingPetFragment fragment = new EditMissingPetFragment();
                            Bundle args = new Bundle();
                            args.putParcelable("editPet", mLostPet);
                            fragment.setArguments(args);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.main_activity_container, fragment);
                            transaction.addToBackStack("Edit"); // TODO: or (null) ????
                            transaction.commit();
                        } else {
                            EditFoundPetFragment fragment = new EditFoundPetFragment();
                            Bundle args = new Bundle();
                            args.putParcelable("editPet", mLostPet);
                            fragment.setArguments(args);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.add(R.id.main_activity_container, fragment);
                            transaction.addToBackStack("Edit"); // TODO: or (null) ????
                            transaction.commit();
                        }
                        return true;

                    case R.id.delete_lostpet:
                        String pet_id = mLostPet.getLost_pet_id();
                        new FirebaseDatabaseHelper().deleteLost(pet_id, new FirebaseDatabaseHelper.DataStatusLost() {
                            @Override
                            public void DataIsLoaded(ArrayList<LostPet> favorites, ArrayList<String> keys) {
                            }
                            @Override
                            public void DataIsInserted() {
                            }
                            @Override
                            public void DataIsUpdated() {
                            }
                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                        Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();

                        // Navigate back to all lost pets
                        getActivity().onBackPressed();
                        return true;
                }
                return true;
            }
        });
        pm.show();
    }

    private LostPet getLostPetFromBundle() {
        Log.d(TAG, "getLostPetFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // Find out if this is myLost or allLost
            myLost = bundle.getBoolean("isMyLost");
            return bundle.getParcelable("LOSTPET");
        } else {
            return null;
        }
    }
}