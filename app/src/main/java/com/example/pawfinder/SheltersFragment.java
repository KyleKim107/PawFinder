package com.example.pawfinder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SheltersFragment extends Fragment {

    private SheltersViewModel sheltersViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sheltersViewModel =
                ViewModelProviders.of(this).get(SheltersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shelters, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng madison = new LatLng(43.0731, -89.4012);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(madison, 11.0f));
                LatLng shelter1 = new LatLng(42.995862, -89.524100);
                googleMap.addMarker(new MarkerOptions().position(shelter1).title("Angel's Wish Pet Adoption and Resource Center"));
                LatLng shelter2 = new LatLng(43.053467, -89.289811);
                googleMap.addMarker(new MarkerOptions().position(shelter2).title("Dane County Humane Society"));
                LatLng shelter3 = new LatLng(43.0830631, -89.308350);
                googleMap.addMarker(new MarkerOptions().position(shelter3).title("Shelter From the Storm"));
                LatLng shelter4 = new LatLng(43.036737, -89.390748);
                googleMap.addMarker(new MarkerOptions().position(shelter4).title("Madison Cat Project"));
                LatLng shelter5 = new LatLng(43.102949, -89.341310);
                googleMap.addMarker(new MarkerOptions().position(shelter5).title("Underdog Pet Rescue of Wisconsin"));
                LatLng shelter6 = new LatLng(43.108965, -89.301484);
                googleMap.addMarker(new MarkerOptions().position(shelter6).title("Diamond Dogs Rescue"));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14.0f));
                        marker.showInfoWindow();
                        return true;
                    }
                });
            }
        });
//        final TextView textView = root.findViewById(R.id.text_shelters);
//        sheltersViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}