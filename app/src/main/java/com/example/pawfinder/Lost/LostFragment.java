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
}