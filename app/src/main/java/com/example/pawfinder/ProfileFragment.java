package com.example.pawfinder;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    View root;

    private static final String TAG = "ProfileFragment";

    //vars
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAges = new ArrayList<>();
    private ArrayList<String> mGenders = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    private FirebaseAuth mAuth;
    TextView name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);
//        final TextView textView = root.findViewById(R.id.text_profile);
//        profileViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        name = (TextView) root.findViewById(R.id.userName);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        name.setText(user.getDisplayName());

        initImageBitmaps();

        return root;
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80");
        mNames.add("Lucky");
        mAges.add("3 years");
        mGenders.add("Male");

        mImageUrls.add("https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80");
        mNames.add("Pumpkin");
        mAges.add("1 month");
        mGenders.add("Female");

        mImageUrls.add("https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80");
        mNames.add("Chase");
        mAges.add("5 years");
        mGenders.add("Male");

        mImageUrls.add("https://images.unsplash.com/photo-1542296935124-75ae8a4e6329?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
        mNames.add("Sweetie");
        mAges.add("3 years");
        mGenders.add("Female");

        mImageUrls.add("https://images.unsplash.com/photo-1570824105192-a7bb72b73141?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=767&q=80");
        mNames.add("Honey");
        mAges.add("2 years");
        mGenders.add("Female");

        mImageUrls.add("https://images.unsplash.com/photo-1593991393705-552675fe6f6d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80");
        mNames.add("Camper");
        mAges.add("4 years");
        mGenders.add("Male");

        mImageUrls.add("https://images.unsplash.com/photo-1605125731324-fe8ef7d199df?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1418&q=80");
        mNames.add("Grover");
        mAges.add("2 years");
        mGenders.add("Male");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        recyclerView = root.findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(getActivity(), mImageUrls, mNames, mAges, mGenders);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void deletePet(String dImage, String dName, String dAge, String dGender) {
        mImageUrls.remove(dImage);
        mNames.remove(dName);
        mAges.remove(dAge);
        mGenders.remove(dGender);
        adapter.notifyDataSetChanged();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String deleteImage = mImageUrls.get(viewHolder.getAdapterPosition());
            String deleteName = mNames.get(viewHolder.getAdapterPosition());
            String deleteAge = mAges.get(viewHolder.getAdapterPosition());
            String deleteGender = mGenders.get(viewHolder.getAdapterPosition());
            deletePet(deleteImage, deleteName, deleteAge, deleteGender);
        }

        @Override
        public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((RecyclerViewAdapter.ViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final View foregroundView = ((RecyclerViewAdapter.ViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((RecyclerViewAdapter.ViewHolder) viewHolder).viewForeground;

            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
        }
    };
}