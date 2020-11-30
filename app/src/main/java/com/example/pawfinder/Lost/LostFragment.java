package com.example.pawfinder.Lost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.pawfinder.R;
import com.example.pawfinder.Utils.PagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class LostFragment extends Fragment {

    private static final String TAG = "LostFragment";

    ViewPager pager;
    TabLayout mTabLayout;
    TabItem mAllLostPetsItem, mMyLostPetsItem;
    PagerAdapter adapter;
    FloatingActionButton mReportPet;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lost, container, false);
        Log.d("TEST", "LOST FRAGMENT : ON CREATE VIEW");
        pager = root.findViewById(R.id.viewPager);
        mTabLayout = root.findViewById(R.id.tablayout);
        mAllLostPetsItem = root.findViewById(R.id.allLostPetsItem);
        mMyLostPetsItem = root.findViewById(R.id.myLostPetsItem);
        mReportPet = root.findViewById(R.id.reportPetFloatingButton);

        mReportPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: navigating to ReportLostPet activity.");
                Intent intent = new Intent(getActivity(), ReportPetActivity.class);
                startActivity(intent);
            }
        });

        adapter = new PagerAdapter(requireActivity().getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTabLayout.getTabCount());
        pager.setAdapter(adapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        return root;
    }

//    private void paginate() {
//        List<Pet> old = adapter.getItems();
//        List<Pet> current = new ArrayList<>(createSpots());
//        CardStackCallback callback = new CardStackCallback(old, current);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//        adapter.setItems(current);
//        result.dispatchUpdatesTo(adapter);
//    }

//    // For buttons later
//    public void myLostPets() {
//        List<Pet> old = adapter.getItems();
//        List<Pet> current = new ArrayList<>(createSpot());
//        CardStackCallback callback = new CardStackCallback(old, current);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//        adapter.setItems(current);
//        result.dispatchUpdatesTo(adapter);
//    }

//    public void allLostPets() {
//        List<Pet> old = adapter.getItems();
//        List<Pet> current = new ArrayList<>(createSpots());
//        CardStackCallback callback = new CardStackCallback(old, current);
//        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
//        adapter.setItems(current);
//        result.dispatchUpdatesTo(adapter);
//    }

//    // Dummy all lost pet data
//    // Using fields incorrectly right now, but it still works
//    private List<Pet> createSpots() {
//        List<Pet> items = new ArrayList<>();
////        items.add(new Pet("https://images.unsplash.com/photo-1570018143038-6f4c428f6e3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=698&q=80", "Lucy", "(262)-363-2727", "Last Seen: Downtown"));
////        items.add(new Pet("https://images.unsplash.com/photo-1593991341138-9a9db56a8bf6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80", "Fred", "(343)-353-4562", "Last Seen: The Park"));
////        items.add(new Pet("https://images.unsplash.com/photo-1598739871560-29dfcd95b823?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80", "Marv", "(646)-545-7894", "Last Seen: East Dublin Rd."));
//        return items;
//    }

//    // Dummy my lost pet data
//    private List<Pet> createSpot() {
//        List<Pet> items = new ArrayList<>();
////        items.add(new Pet("https://images.unsplash.com/photo-1570824105192-a7bb72b73141?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=767&q=80", "Jenny", "(608)-867-5309", "1600 Apple Grove"));
//        return items;
//    }

}