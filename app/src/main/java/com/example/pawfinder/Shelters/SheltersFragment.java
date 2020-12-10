package com.example.pawfinder.Shelters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pawfinder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SheltersFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_shelters, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = root.findViewById(R.id.google_map);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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
}