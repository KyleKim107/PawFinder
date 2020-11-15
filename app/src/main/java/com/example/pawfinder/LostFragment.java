package com.example.pawfinder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.util.ArrayList;
import java.util.List;

public class LostFragment extends Fragment {

    private static final String TAG = LostFragment.class.getSimpleName();
    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lost, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        CardStackView cardStackView = root.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);

                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() - 5){
                    paginate();
                }

            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", name: " + tv.getText());
            }
        });
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(createSpots());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
    }

    private void paginate() {
        List<Pet> old = adapter.getItems();
        List<Pet> current = new ArrayList<>(createSpots());
        CardStackCallback callback = new CardStackCallback(old, current);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(current);
        result.dispatchUpdatesTo(adapter);
    }

    // For buttons later
    public void myLostPets() {
        List<Pet> old = adapter.getItems();
        List<Pet> current = new ArrayList<>(createSpot());
        CardStackCallback callback = new CardStackCallback(old, current);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(current);
        result.dispatchUpdatesTo(adapter);
    }

    public void allLostPets() {
        List<Pet> old = adapter.getItems();
        List<Pet> current = new ArrayList<>(createSpots());
        CardStackCallback callback = new CardStackCallback(old, current);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setItems(current);
        result.dispatchUpdatesTo(adapter);
    }

    // Dummy all lost pet data
    // Using fields incorrectly right now, but it still works
    private List<Pet> createSpots() {
        List<Pet> items = new ArrayList<>();
//        items.add(new Pet("https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80", "Lucy", "(262)-363-2727", "Last Seen: Downtown"));
//        items.add(new Pet("https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80", "Fred", "(343)-353-4562", "Last Seen: The Park"));
//        items.add(new Pet("https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80", "Marv", "(646)-545-7894", "Last Seen: East Dublin Rd."));
        return items;
    }

    // Dummy my lost pet data
    private List<Pet> createSpot() {
        List<Pet> items = new ArrayList<>();
//        items.add(new Pet("https://images.unsplash.com/photo-1570824105192-a7bb72b73141?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=767&q=80", "Jenny", "(608)-867-5309", "1600 Apple Grove"));
        return items;
    }

}