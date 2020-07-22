package com.example.mercadoabierto2_mvvm.view.activitiy;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.example.mercadoabierto2_mvvm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    Double v1 = -5.3477;
    Double v = -83.9487;
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    @BindView(R.id.constraintMap)
    ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        v = bundle.getDouble(LATITUDE);
        v1 = bundle.getDouble(LONGITUDE);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.map = googleMap;
        /*
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else {
           map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setMyLocationEnabled(true);*/
        float zoomLevel = 10.0f;
        LatLng place = new LatLng(v, v1);
        map.addMarker(new MarkerOptions().position(place).title("Location"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel));

    }
}
