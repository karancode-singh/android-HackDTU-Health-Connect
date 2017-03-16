package com.example.aditya21196.hackdtut3;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.snapshot.DoubleNode;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import static com.example.aditya21196.hackdtut3.MainActivity._state;
import static com.example.aditya21196.hackdtut3.MainActivity._tBlood;

public class MapActivity extends AppCompatActivity {

    public GoogleMap map;
    Firebase mRef,cRef;
    String value;
    int ct;
    double dlat,dlng;
    Map<String,String> hmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        switch (_state) {
            case "Andhra Pradesh":
                //code
                gotoLocation(15.9129,79.7400,10);
                break;
            case "Arunachal Pradesh":
                //code
                gotoLocation(28.2180,94.7278,10);
                break;
            case "Assam":

                gotoLocation(26.2006,92.9376,10);
                break;
            case "Bihar":


                gotoLocation(25.0961,85.3131,10);        //code
                break;
            case "Chhattisgarh":

                gotoLocation(21.2787,81.8661,10);        //code
                break;
            case "Goa":

                gotoLocation(15.2993,74.1240,10);          //code
                break;
            case "Gujarat":

                gotoLocation(22.2587,71.1924,10);        //code
                break;
            case "Haryana":

                gotoLocation(29.0588,76.0856,10);         //code
                break;
            case "Himachal Pradesh":

                gotoLocation(31.1048,77.1734,10);        //code
                break;
            case "Jammu and Kashmir":

                gotoLocation(33.7782,76.5762,10);        //code
                break;
            case "Jharkhand":
                gotoLocation(23.6102,85.2799,10);
                //code
                break;
            case "Karnataka":

                gotoLocation(15.3173,75.7139,10);          //code
                break;
            case "Kerala":

                gotoLocation(10.8505,76.2711,10);         //code
                break;
            case "Madhya Pradesh":

                gotoLocation(22.9734,78.6569,10);        //code
                break;
            case "Maharashtra":

                gotoLocation(19.7515,75.7139,10);         //code
                break;
            case "Manipur":

                gotoLocation(24.6637,93.9063,10);           //code
                break;
            case "Meghalaya":
                gotoLocation(25.4670,91.3662,10);
                //code
                break;
            case "Mizoram":

                gotoLocation(23.1645,92.9376,10);        //code
                break;
            case "Nagaland":

                gotoLocation(26.1584,94.5624,10);           //code
                break;
            case "Odisha":

                gotoLocation(20.9517,85.0985,10);         //code
                break;
            case "Punjab":

                gotoLocation(31.1471,75.3412,10);        //code
                break;
            case "Rajasthan":

                gotoLocation(27.0238,74.2179,10);        //code
                break;
            case "Sikkim":

                gotoLocation(27.5330,88.5122,10);        //code
                break;
            case "Tamil Nadu":

                gotoLocation(11.1271,78.6569,10);         //code
                break;
            case "Telangana":

                gotoLocation(18.1124,79.0193,10);        //code
                break;
            case "Tripura":

                gotoLocation(23.9408,91.9882,10);         //code
                break;
            case "Uttar Pradesh":
                gotoLocation(26.8467,80.9462,10);
                //code
                break;
            case "Uttarakhand":

                gotoLocation(30.0668,79.0193,10);         //code
                break;
            case "West Bengal":

                gotoLocation(22.9868,87.8550,10);         //code
                break;
            case "Andaman and Nicobar Islands":

                gotoLocation(11.7401,92.6586,10);         //code
                break;
            case "Chandigarh":

                gotoLocation(30.7333,76.7794,10);         //code
                break;
            case "Dadra and Nagar Haveli":

                gotoLocation(20.1809,73.0169,10);         //code
                break;
            case "Daman and Diu":

                gotoLocation(20.4283,72.8397,10);         //code
                break;
            case "Lakshadweep":

                gotoLocation(10.360,72.360,10);         //code
                break;
            case "Delhi":

                gotoLocation(28.7041,77.1025,10);         //code
                break;
            case "Puducherry":

                gotoLocation(11.550,79.490,10);          //code
                break;
        }

        if(_tBlood){

            //retreive db and show markers
            cRef = new Firebase("https://hackdtut3.firebaseio.com/Users/"+_state+"/Blood/Count");
            cRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    value = dataSnapshot.getValue(String.class);
                    ct=Integer.parseInt(value);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mRef=new Firebase("https://hackdtut3.firebaseio.com/Users/"+_state+"/Blood/Donors");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hmap=dataSnapshot.getValue(Map.class);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            Marker mArray[] = new Marker[ct];
            String s="";
            for(int i=1;i<=ct;i++){
                s=hmap.get("i");
                String[] str= s.split(" ");
                dlat = Double.parseDouble(str[0]);
                dlng = Double.parseDouble(str[1]);
                MarkerOptions options = new MarkerOptions()
                        .title("your destination")
                        .position(new LatLng(dlat,dlng));
                mArray[i-1] = map.addMarker(options);
            }
        }


    }

    private void gotoLocation(double lat, double lng, int Zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, Zoom);
        map.animateCamera(update);
    }
}
