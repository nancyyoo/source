package com.coffee.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.coffee.coffeebean.R;
import com.coffee.util.Logger;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityLocation extends Activity implements OnMapReadyCallback {
    private static final String TAG = "ActivityLocation";

    // 구글 지도 api
    private GoogleMap googleMap;
    private LatLng loc;
    private CameraPosition cp;
    private MarkerOptions marker;
    private  Marker m;
    private Double latitude;
    private Double hardness;
    private String title;
    @Override
    // Activity가 실행되면 딱한번만 호출 되는 메소드
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        Button btBack = (Button) findViewById(R.id.btn_prev);
        btBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        //좌표 위도/경도를 받아서 정확한 위치를 표기하기 위해 받아서 처리.
        Intent intent = getIntent();
        latitude = intent.getExtras().getDouble("latitude");
        hardness = intent.getExtras().getDouble("hardness");
        title = intent.getStringExtra("title");

        Logger.d(TAG, "title :" + title);
        Logger.d(TAG, "latitude : " + latitude);
        Logger.d(TAG, "hardness : " + hardness);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    // 레이아웃 호출
    @Override
    public void onMapReady(final GoogleMap map) {
        googleMap = map;

        loc = new LatLng(hardness, latitude);

//        loc = new LatLng(hardness, latitude);
        cp = new CameraPosition.Builder().target((loc)).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));

        marker = new MarkerOptions().position(loc).title(title).snippet("현재위치");
        googleMap.addMarker(marker);

        try {
            configureMap(googleMap);
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                AlertDialog.Builder alert = new AlertDialog.Builder(ActivityLocation.this);
                alert.setTitle("위치정보를 등록하시겠습니까?");
                alert.setIcon(R.drawable.ic_launcher);

                DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActivityLocation.this, "위치정보", Toast.LENGTH_LONG).show();
                    }
                };
                alert.setPositiveButton("확인", positiveClick);
                alert.setNegativeButton("취소", null);
                alert.show();
            }
        });
    }

    private void configureMap(GoogleMap map) throws GooglePlayServicesNotAvailableException {
        MapsInitializer.initialize(this);
    }
}