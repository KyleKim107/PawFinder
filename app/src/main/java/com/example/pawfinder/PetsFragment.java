package com.example.pawfinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class PetsFragment extends Fragment {

    private static final String TAG = "PetsFragment";
    View root;
    private ArrayList<Pet> mPets = new ArrayList<>();

    CardStackView mCardStackView;

    public static PetsFragment newInstance() {
        PetsFragment fragment = new PetsFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);

        mCardStackView = (CardStackView) root.findViewById(R.id.card_stack_view);

        new FirebaseDatabaseHelper("pets", "").readPets(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<Pet> pets, ArrayList<String> keys) {
                new CardStackConfig().setConfig(mCardStackView, getActivity(), pets, keys);
                mPets = pets;
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

        return root;
    }
}