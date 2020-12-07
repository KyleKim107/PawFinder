package com.example.pawfinder.Pets;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pawfinder.Lost.EditFoundPetFragment;
import com.example.pawfinder.Lost.EditMissingPetFragment;
import com.example.pawfinder.Lost.LostFragment;
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
import java.util.TimeZone;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ViewPetFragment extends Fragment {

    private final String TAG = "ViewPetFragment";

    private Pet mPet;
    private ImageView mPetImage, mBackArrow;
    private TextView mPetName, mPetPosted, mPetGender, mPetType, mLastSeen, mPetArea, mPetDate,
                        mEmail, mPhone, mAdditionalInfo, mMessage;
    private Boolean myLost;

    public ViewPetFragment() {
        // Required empty public constructor
        super();
        setArguments(new Bundle());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_pet, container, false);

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
            mPet = getPetFromBundle();
            setupWidgets();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: lost pet was null from bundle " + e.getMessage());
        }

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
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int windowWidth = size.x;
        int windowHeight = size.y;
        mPetImage.getLayoutParams().height = windowHeight / 2;
        mPetImage.requestLayout();

        Glide.with(getActivity())
                .asBitmap()
                .load("https://firebasestorage.googleapis.com/v0/b/pawfinder-b450a.appspot.com/o/photos%2Fusers%2FxuiXEyZqg7S2vmJW06j4UH0GMGW2%2F-MNtflfOrnaCu7Ne5ERL?alt=media&token=df626f73-6b20-4dc1-b111-08eadb40c9f1")
                .into(mPetImage);

//        // Name
//        mPetName.setText(mPet.getPet_name());
//
//        // Date posted
//        String timestampDiff = getTimestampDifference();
//        if(!timestampDiff.equals("0")){
//            if(timestampDiff.equals("1")) {
//                mPetPosted.setText(timestampDiff + " DAY AGO");
//            } else {
//                mPetPosted.setText(timestampDiff + " DAYS AGO");
//            }
//        } else{
//            mPetPosted.setText("TODAY");
//        }
//
//        // Gender
//        if (mPet.getPet_gender().equals("")) {
//            mPetGender.setVisibility(View.GONE);
//        } else {
//            mPetGender.setText("Gender: " + mPet.getPet_gender());
//        }
//
//        // Type
//        mPetType.setText("Type: " + mPet.getPet_type());
//
//        // Last Seen / Recently Found
//        if (mPet.getStatus().equals("missing")) {
//            mLastSeen.setText("LAST SEEN");
//        } else {
//            mLastSeen.setText("RECENTLY FOUND");
//        }
//
//        // Area
//        mPetArea.setText("Area: " + mPet.getArea_missing());
//
//        // Date
//        mPetDate.setText("Date: " + mPet.getDate_missing());
//
//        // Email
//        if (myLost) {
//            mEmail.setText("Email: " + mPet.getEmail());
//        } else {
//            mEmail.setText("Email: Tap to view");
//            mEmail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mEmail.setText("Email: " + mPet.getEmail());
//                }
//            });
//        }
//
//        // Phone
//        if (mPet.getPhone().equals("")) {
//            mPhone.setVisibility(View.GONE);
//        } else {
//            if (myLost) {
//                mPhone.setText("Phone: " + mPet.getPhone());
//            } else {
//                mPhone.setText("Phone: Tap to view");
//                mPhone.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        mPhone.setText("Phone: " + mPet.getPhone());
//                    }
//                });
//            }
//        }
//
//        // Message
//        if (mPet.getMessage().equals("")) {
//            mAdditionalInfo.setVisibility(View.GONE);
//            mMessage.setVisibility(View.GONE);
//        } else {
//            mMessage.setText("Message: " + mPet.getMessage());
//        }
    }

//    private String getTimestampDifference(){
//        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");
//
//        String difference = "";
//        Calendar c = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
//        sdf.setTimeZone(TimeZone.getTimeZone("US/Central"));
//        Date today = c.getTime();
//        sdf.format(today);
//        Date timestamp;
//        final String photoTimestamp = mPet.getDate_posted();
//        try{
//            timestamp = sdf.parse(photoTimestamp);
//            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
//        }catch (ParseException e){
//            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage());
//            difference = "0";
//        }
//        return difference;
//    }

    private Pet getPetFromBundle() {
        Log.d(TAG, "getLostPetFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("PET");
        } else {
            return null;
        }
    }
}