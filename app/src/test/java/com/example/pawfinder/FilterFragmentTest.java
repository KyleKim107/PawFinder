package com.example.pawfinder;

import org.junit.Test;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static org.junit.Assert.*;

public class FilterFragmentTest {


    @Test
    public void newInstance() {

        FilterFragment filterFragment = new FilterFragment();
        Fragment fragment = filterFragment.newInstance();

        assertNotEquals("Fragments should not be equal", fragment, FilterFragment.newInstance());
    }
}