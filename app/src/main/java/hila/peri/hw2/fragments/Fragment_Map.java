package hila.peri.hw2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import hila.peri.hw2.R;
import hila.peri.hw2.logic.Record;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_Map extends Fragment {

    private MapView mapView;
    private double latitude = 0.500004, longitude = -0.100000;
    private GoogleMap googleMap;
    private String name = "End of the world";
    private String score = "Win for sure";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

//        SupportMapFragment supportMapFragment=(SupportMapFragment)
//                getChildFragmentManager().findFragmentById(R.id.topTen_MAP_view);
        mapView = (MapView) view.findViewById(R.id.topTen_MAP_view);
        mapView.onCreate(savedInstanceState);

        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                LatLng lating = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(lating).title(name).snippet(score));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(lating).zoom(5).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;
    }

    public void showPlayerLocation(Record record) {
        LatLng currLocation = new LatLng(record.getLat(), record.getLon());
        googleMap.addMarker(new MarkerOptions().position(currLocation).title(record.getName()).snippet("" + record.getScore()));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(currLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}