package com.example.kmj.sunrinton2018;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    final int PERMISSIONS_REQUEST = 100;
    Marker mMarker;
    private GoogleMap mMap;
    private GpsInfo gpsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gpsInfo = new GpsInfo(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "권한이 없습니다.", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        gpsInfo.stopUsingGPS();
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(10);

        NetworkHelper.getInstance().earth().enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
        public void setLocation () {
            boolean isSuccess = false;
            if (gpsInfo.isGetLocation()) {
                if (!(gpsInfo.getLatitude() == 0 && gpsInfo.getLongitude() == 0)) {
                    isSuccess = true;
                }
            }
            if (isSuccess) {
                mMarker = mMap.addMarker(new MarkerOptions()
                        .title("this is title")
                        .snippet("this is snippet")
                        .position(new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude()))
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsInfo.getLatitude(), gpsInfo.getLongitude()), 17.0f));
            } else {
                Toast.makeText(this, "자신의 위치 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onPointerCaptureChanged ( boolean hasCapture){

        }
    }
