package com.example.pawfinder.Lost;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pawfinder.MainActivity;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ViewLostPetFragment extends Fragment {

    private final String TAG = "ViewLostPetFragment";

    private LostPet mLostPet;
    private CircleImageView mPetImage;
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

        // Toolbar
        mEllipses = root.findViewById(R.id.viewLostPetEllipses);
        mBackArrow = root.findViewById(R.id.backArrow_viewLostPet);

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LostFragment fragment = new LostFragment();
                Bundle args = new Bundle();
                args.putBoolean("isMyLost", myLost);
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_activity_container, fragment);
                transaction.addToBackStack("Lost"); // TODO: or (null) ????
                transaction.commit();
            }
        });

        try {
            mLostPet = getLostPetFromBundle();
            Glide.with(getActivity())
                    .asBitmap()
                    .load(mLostPet.getImage_path())
                    .into(mPetImage);
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: lost pet was null from bundle " + e.getMessage());
        }

        setupWidgets();

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