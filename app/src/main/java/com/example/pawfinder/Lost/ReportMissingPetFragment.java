package com.example.pawfinder.Lost;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pawfinder.MainActivity;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;
import com.example.pawfinder.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ReportMissingPetFragment extends Fragment {

    private static final String TAG = "ReportMisingPetFragment";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabaseHelper mFirebaseDatabaseHelper;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private int lostPetCount = 0;
    private String imgUrl;
    private Bitmap bitmap;

    private EditText mPetName, mAreaMissing, mMessage, mEmail, mPhoneNumber;
    private TextView mDateMissing, mChangePetPhoto;
    private ImageView mPetPhoto;
    private Spinner mPetType, mPetGender;
    private RelativeLayout mDateLayout;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public ReportMissingPetFragment() {
        // Required empty public constructor
    }

    public static ReportMissingPetFragment newInstance() {
        ReportMissingPetFragment fragment = new ReportMissingPetFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_report_missing_pet, container, false);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mFirebaseDatabaseHelper = new FirebaseDatabaseHelper();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lostPetCount = mFirebaseDatabaseHelper.getLostPetCount(dataSnapshot);
                Log.d(TAG, "LOST PET COUNT = " + lostPetCount);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mPetPhoto = root.findViewById(R.id.petphoto_missing);
        mChangePetPhoto = root.findViewById(R.id.changepetphoto_missing);
        mPetName = root.findViewById(R.id.petname_missing);
        mPetType = root.findViewById(R.id.pettypespinner_missing);
        mPetGender = root.findViewById(R.id.genderspinner_missing);
        mDateMissing = root.findViewById(R.id.date_missing);
        mAreaMissing = root.findViewById(R.id.area_missing);
        mMessage = root.findViewById(R.id.message_missing);
        mEmail = root.findViewById(R.id.email_missing);
        mPhoneNumber = root.findViewById(R.id.phonenumber_missing);
        mDateLayout = root.findViewById(R.id.relLayout4);

        // Set email
        mEmail.setText(user.getEmail());

        // Set up back arrow for navigating back to Report Activity
        ImageView backArrow_reportMissingPet = root.findViewById(R.id.backArrow_reportMissingPet);
        backArrow_reportMissingPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to Report Activity");
                getActivity().onBackPressed();
            }
        });

        // Set up check mark for uploading to database and returning to Lost Fragment
        ImageView reportMissingPetCheckmark = root.findViewById(R.id.reportMissingPetCheckmark);
        reportMissingPetCheckmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String petName_text = mPetName.getText().toString().trim();
                final String petType_text = mPetType.getSelectedItem().toString().trim();
                final String petGender_text = mPetGender.getSelectedItem().toString().trim();
                final String dateMissing_text = mDateMissing.getText().toString().trim();
                final String areaMissing_text = mAreaMissing.getText().toString().trim();
                final String message_text = mMessage.getText().toString().trim();
                final String email_text = mEmail.getText().toString().trim();
                final String phoneNumber_text = mPhoneNumber.getText().toString().trim();

                if(imgUrl != null && imgUrl.equals("https://tameme.ru/static/img/catalog/default_pet.jpg")) {
                    mChangePetPhoto.setError("");
                    return;
                } else {
                    mChangePetPhoto.setError(null);
                }
                if (petName_text.isEmpty()) {
                    mPetName.setError("Pet's name required.");
                    mPetName.requestFocus();
                    return;
                }
                if (petType_text.equals("Pet Type")) {
                    TextView errorText = (TextView)mPetType.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Pet type required.");
                    mPetType.requestFocus();
                    return;
                }
                if (petGender_text.equals("Pet's Gender")) {
                    TextView errorText = (TextView)mPetGender.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Pet's gender required.");
                    mPetGender.requestFocus();
                    return;
                }
                if (dateMissing_text.equals("Select the date the pet went missing")) {
                    mDateMissing.setError("");
                    mDateMissing.setTextColor(Color.RED);
                    mDateMissing.setText("Date missing required.");
                    mDateMissing.requestFocus();
                    return;
                } else {
                    mDateMissing.setError(null);
                }
                if (areaMissing_text.isEmpty()) {
                    mAreaMissing.setError("Area missing required.");
                    mAreaMissing.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email_text).matches()) {
                    mEmail.setError("Enter a valid email address.");
                    mEmail.requestFocus();
                    return;
                }
                if (!phoneNumber_text.isEmpty()) {
                    if (!Patterns.PHONE.matcher(phoneNumber_text).matches()) {
                        mPhoneNumber.setError("Not a valid phone number.");
                        mPhoneNumber.requestFocus();
                        return;
                    }
                }

                Log.d(TAG, "onClick: uploading to Firebase Database + Storage");
                mFirebaseDatabaseHelper.uploadNewLostPet("missing", lostPetCount, petName_text,
                        petType_text, petGender_text, dateMissing_text, areaMissing_text,
                        message_text, email_text, phoneNumber_text, bitmap);

                Log.d(TAG, "onClick: navigating back to Lost Fragment");
                getActivity().finish();
            }
        });

        // Set change photo on click
        mChangePetPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(getActivity());
            }
        });

        setTypeSpinner();
        setGenderSpinner();
        setCalendar();

        initImageLoader();
        setPetImage();

        return root;
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    askCameraPermissions();
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent gallery = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void askCameraPermissions() {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(getActivity(), "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE){
            bitmap = (Bitmap) data.getExtras().get("data");
            imgUrl = null;
            if(resultCode == Activity.RESULT_OK){
                mPetPhoto.setImageBitmap(bitmap);
                mChangePetPhoto.setError(null);
            }
        }
        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                mPetPhoto.setImageURI(contentUri);
                try {
                    getRealPathFromURI(contentUri);
                    imgUrl = null;
                    mChangePetPhoto.setError(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void getRealPathFromURI(Uri contentURI) {
        String thePath;
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (Build.VERSION.SDK_INT >= 29) {
                try (ParcelFileDescriptor pfd = getActivity().getContentResolver().openFileDescriptor(contentURI, "r")) {
                    if (pfd != null) {
                        bitmap = BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
                    }
                } catch (IOException ex) {
                }
            } else {
                thePath = cursor.getString(columnIndex);
                File imageFile = new File(thePath);
                if (imageFile.exists()) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    try {
                        bitmap = BitmapFactory.decodeFile(thePath, options);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        cursor.close();
    }

    private void setCalendar() {
        mDateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        R.style.DialogTheme,
                        mDateSetListener,
                        year, month, day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                mDateMissing.setText(date);
                mDateMissing.setTextColor(Color.BLACK);
                mDateMissing.setError(null);
            }
        };
    }

    private void setTypeSpinner() {
        String [] values = {"Pet Type", "Dog", "Cat", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), R.layout.spinner_item, values) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.LTGRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        mPetType.setAdapter(adapter);
        final boolean[] isSpinnerTouched = {false};
        mPetType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched[0] = true;
                return false;
            }
        });
        mPetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2, long arg3) {
                if (!isSpinnerTouched[0]) return;
                TextView selectedText = (TextView) adapter.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.BLACK);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setGenderSpinner() {
        String [] values = {"Pet's Gender", "Male","Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this.getActivity(), R.layout.spinner_item, values) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                if (position == 0) {
                    textview.setTextColor(Color.LTGRAY);
                } else {
                    textview.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        mPetGender.setAdapter(adapter);
        final boolean[] isSpinnerTouched = {false};
        mPetGender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched[0] = true;
                return false;
            }
        });
        mPetGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2, long arg3) {
                if (!isSpinnerTouched[0]) return;
                TextView selectedText = (TextView) adapter.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.BLACK);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setPetImage() {
        Log.d(TAG, "setPetImage: Setting pet image.");
        imgUrl = "https://tameme.ru/static/img/catalog/default_pet.jpg";
        UniversalImageLoader.setImage(imgUrl, mPetPhoto, null, "");
    }
}