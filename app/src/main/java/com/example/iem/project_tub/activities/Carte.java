package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.ArretManager;
import com.example.iem.project_tub.models.Ligne;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Carte extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Arret> stopLigne;
    private Ligne ligne5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        stopLigne = ArretManager.getInstance(this).getArrets();


        //Log.d("Erreur :", String.valueOf(stopLigne5.get(0).getName()));
        //LatLng closStop = new LatLng(stops.getStopWithName("Clos").getLat(), stops.getStopWithName("Clos").getLong());

        //Log.d("Arret :", String.valueOf(stops.getStopWithName("Clos").getName()));
        //mMap.addMarker(new MarkerOptions().position(closStop).title("Marker in Clos"));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*try {
            KmlLayer layer = new KmlLayer(mMap, R.raw.ligne5, getApplicationContext());
            layer.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Add a marker in Sydney and move the camera
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                marker.showInfoWindow();
                return true;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent recherche = new Intent(Carte.this, Recherche.class);
                recherche.putExtra("Arret", marker.getTitle());
                startActivity(recherche);
            }
        });
        addMarkerstoMap(googleMap);



    }

    public void addMarkerstoMap(GoogleMap googleMap) {

        for(int i = 0; i < stopLigne.size(); ++i){

            LatLng closStop = new LatLng(stopLigne.get(i).getLat(), stopLigne.get(i).getLong());
            //Log.d("Erreur :" , String.valueOf(stopLigne.get(i).getNom()));
            //Log.d(String.valueOf(closStop), String.valueOf(stops.getStopWithId(i).getName()));
            mMap.addMarker(new MarkerOptions()
                    .position(closStop).title(stopLigne.get(i).getNom())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.newmarker)));

        }


    }

}
