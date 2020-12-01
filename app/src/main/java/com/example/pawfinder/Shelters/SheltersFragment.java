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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SheltersFragment extends Fragment implements OnMapReadyCallback {

    private SheltersViewModel sheltersViewModel;

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    String mMessage;
    OkHttpClient client = new OkHttpClient();
    String token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sheltersViewModel =
                ViewModelProviders.of(this).get(SheltersViewModel.class);
        mView = inflater.inflate(R.layout.fragment_shelters, container, false);


        try {
//            URL url = new URL("https://api.petfinder.com/v2/oauth2/token");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("grant_type", "client_credentials");
//            connection.setRequestProperty("client_id", "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ");
//            connection.setRequestProperty("client_secret", "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb");//전송방식
//            connection.setDoOutput(true);       //데이터를 쓸 지 설정
//            connection.setDoInput(true);        //데이터를 읽어올지 설정
//            connection.setRequestProperty("Content-Type","application/json");
//            connection.setRequestProperty("Accept","application/json");
//
//            InputStream is = connection.getInputStream();
//            StringBuilder sb = new StringBuilder();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
//            String result;
//            while((result = br.readLine())!=null){
//                sb.append(result+"\n");
//            }
//
//            result = sb.toString();
                        HttpUrl httpUrl = new HttpUrl.Builder()
                    .scheme("http")
                    .host("api.petfinder.com")
                    .addPathSegment("v2/oauth2/token")
                    .build();

            RequestBody body = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", "Ru1hdjxh6Sa8uF7Ubconob19BRan9ZquO2VKeDAeWagiqAVziQ")
                    .add("client_secret", "zW9bRfZLJRHyupME3Z7qs0pgWqq9EFDF2vYnSSBb")
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.petfinder.com/v2/oauth2/token")
                    .post(body)
                    .build();
            client.newCall(request).enqueue(callbackAfterRequest);



        }catch(Exception e){

        }

        return mView;
    }
    private Callback callbackAfterRequest = new Callback(){

        @Override
        public void onFailure(Call call, IOException e) {
            String mMessage = e.getMessage().toString();
            Log.w("failure Response", mMessage);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            mMessage = response.body().string();
            Log.i("RESPONSE-1", mMessage);
            try{
                JSONObject obj = new JSONObject(mMessage);

            token = obj.getString("access_token");
            }catch (Exception e){

            }
            Log.w("777" ,token );

        }
    };


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = mView.findViewById(R.id.google_map);
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

