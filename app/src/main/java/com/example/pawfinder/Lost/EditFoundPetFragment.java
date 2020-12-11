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
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
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

import com.bumptech.glide.Glide;
import com.example.pawfinder.Models.LostPet;
import com.example.pawfinder.R;
import com.example.pawfinder.Utils.FirebaseDatabaseHelper;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class EditFoundPetFragment extends Fragment {

    private static final String TAG = "EditMissingPetFragment";

    private FirebaseDatabaseHelper mFirebaseDatabaseHelper;

    private LostPet mLostPet;
    private Bitmap bitmap;
    private EditText mPetName, mAreaFound, mMessage, mEmail, mPhoneNumber;
    private TextView mDateFound, mChangePetPhoto;
    private ImageView mPetPhoto;
    private Spinner mPetType;
    private RelativeLayout mDateLayout;

    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public EditFoundPetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_found_pet, container, false);

        // Firebase
        mFirebaseDatabaseHelper = new FirebaseDatabaseHelper();

        mPetPhoto = root.findViewById(R.id.petphoto_found);
        mChangePetPhoto = root.findViewById(R.id.changepetphoto_found);
        mPetName = root.findViewById(R.id.petname_found);
        mPetType = root.findViewById(R.id.pettypespinner_found);
        mDateFound = root.findViewById(R.id.date_found);
        mAreaFound = root.findViewById(R.id.area_found);
        mMessage = root.findViewById(R.id.message_found);
        mEmail = root.findViewById(R.id.email_found);
        mPhoneNumber = root.findViewById(R.id.phonenumber_found);
        mDateLayout = root.findViewById(R.id.relLayout3_found);

        try {
            mLostPet = getLostPetFromBundle();
            setupWidgets();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: lost pet was null from bundle " + e.getMessage());
        }

        ImageView backArrow_editMissingPet = root.findViewById(R.id.backArrow_editFoundPet);
        backArrow_editMissingPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating back to View Lost Pet Fragment");
                // Navigate back to all lost pets
                requireActivity().onBackPressed();
            }
        });

        ImageView editMissingPetCheckmark = root.findViewById(R.id.editFoundPetCheckmark);
        editMissingPetCheckmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String petName_text = mPetName.getText().toString().trim();
                final String petType_text = mPetType.getSelectedItem().toString().trim();
                final String dateMissing_text = mDateFound.getText().toString().trim();
                final String areaMissing_text = mAreaFound.getText().toString().trim();
                final String message_text = mMessage.getText().toString().trim();
                final String email_text = mEmail.getText().toString().trim();
                final String phoneNumber_text = mPhoneNumber.getText().toString().trim();

                if (areaMissing_text.isEmpty()) {
                    mAreaFound.setError("Area missing required.");
                    mAreaFound.requestFocus();
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
                if (petName_text.isEmpty()) {
                    mLostPet.setPet_name("Unknown");
                } else {
                    mLostPet.setPet_name(petName_text);
                }
                mLostPet.setPet_type(petType_text);
                mLostPet.setDate_missing(dateMissing_text);
                mLostPet.setArea_missing(areaMissing_text);
                mLostPet.setMessage(message_text);
                mLostPet.setEmail(email_text);
                mLostPet.setPhone(phoneNumber_text);

                Log.d(TAG, "onClick: uploading to Firebase Database + Storage");
                mFirebaseDatabaseHelper.updateLostPet(mLostPet, bitmap);

                // Navigate back to my lost pets
                requireActivity().onBackPressed();
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
        setCalendar();

        return root;
    }

    private void setupWidgets() {
        // Image
        Glide.with(requireActivity())
                .asBitmap()
                .load(mLostPet.getImage_path())
                .into(mPetPhoto);

        // Area
        if (!mLostPet.getPet_name().equals("Unknown")) {
            mPetName.setText(mLostPet.getPet_name());
        }

        // Type is selected in setTypeSpinner()

        // Date
        mDateFound.setText( mLostPet.getDate_missing());
        mDateFound.setTextColor(Color.BLACK);

        // Area
        mAreaFound.setText(mLostPet.getArea_missing());

        // Email
        mEmail.setText(mLostPet.getEmail());

        // Phone
        mPhoneNumber.setText(mLostPet.getPhone());

        // Message
        mMessage.setText(mLostPet.getMessage());
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

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
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
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
        Cursor cursor = requireActivity().getContentResolver().query(contentURI, filePathColumn, null, null, null);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            if (Build.VERSION.SDK_INT >= 29) {
                try (ParcelFileDescriptor pfd = requireActivity().getContentResolver().openFileDescriptor(contentURI, "r")) {
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
                        requireActivity(),
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
                mDateFound.setText(date);
                mDateFound.setTextColor(Color.BLACK);
                mDateFound.setError(null);
            }
        };
    }

    private void setTypeSpinner() {
        String [] values = {"Pet Type", "Dog", "Cat", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter(this.requireActivity(), R.layout.spinner_item, values) {
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
        mPetType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1, int arg2, long arg3) {
                TextView selectedText = (TextView) adapter.getChildAt(0);
                if (selectedText != null) {
                    selectedText.setTextColor(Color.BLACK);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        int typeSelection = 1;
        if (mLostPet.getPet_type().equals("Cat")) {
            typeSelection = 2;
        } else if (mLostPet.getPet_type().equals("Other")) {
            typeSelection = 3;
        }
        mPetType.setSelection(typeSelection);
    }

    private LostPet getLostPetFromBundle() {
        Log.d(TAG, "getLostPetFromBundle: arguments " + getArguments());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // Find out if this is myLost or allLost
            return bundle.getParcelable("editPet");
        } else {
            return null;
        }
    }
}