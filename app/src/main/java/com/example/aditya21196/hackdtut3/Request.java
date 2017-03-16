package com.example.aditya21196.hackdtut3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.aditya21196.hackdtut3.MainActivity._state;
import static com.example.aditya21196.hackdtut3.MainActivity._tBlood;

public class Request extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    Button submit;
    Button bBlood;
    EditText etUnits;
    RadioGroup platelets;
    int plateletsChoice;
    String plateChoice;
    Spinner bloodGroupSpinner;

    String units;
    String bGrp;

    Firebase mRef;
    String UID;
    FirebaseUser user;
    String value;
    double lati=-1,lngi;
    int ct;
    boolean done = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        bBlood = (Button)findViewById(R.id.bBlood);
        bBlood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Request.this);
                dialog.setTitle("Request for Blood");
                dialog.setContentView(R.layout.r_blood);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width=WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();

                etUnits = (EditText) dialog.findViewById(R.id.etUnits);

                platelets = (RadioGroup) findViewById(R.id.plateletsRadioGroup);
                plateletsChoice = platelets.getCheckedRadioButtonId();
                switch (plateletsChoice) {
                    case R.id.yes:
                        //some code
                        plateChoice="True";
                        break;
                    case R.id.no:
                        //some code
                        plateChoice="False";
                        break;
                }

                Firebase childRef=mRef.child("Count");
                childRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        value = dataSnapshot.getValue(String.class);
                        ct=Integer.parseInt(value);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update db
                        _tBlood=true;
                        units=etUnits.getText().toString();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        UID=user.getUid();
                        bGrp=String.valueOf(bloodGroupSpinner.getSelectedItem());

                        mRef=new Firebase("https://hackdtut3.firebaseio.com/Users/"+_state+"/Blood/Donors");
                        if(lati!=-1){
                            String s=Double.toString(lati)+" "+ Double.toString(lngi);
                            ct++;
                            value=Integer.toString(ct);
                            addChild(value,s);
                            Firebase f=new Firebase("https://hackdtut3.firebaseio.com/Users/"+_state+"/Blood/Count");
                            f.setValue(value);
                        }else{

                            Toast.makeText(Request.this,"Please turn on GPS to add your request",Toast.LENGTH_LONG).show();

                        }



                    }
                });

                bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);


            }
        });

    }

    public void addChild(String key,String value){
        Firebase mRefChild = mRef.child(key);
        mRefChild.setValue(value);
    }

    @Override
    public void onLocationChanged(Location location) {
        lati=location.getLatitude();
        lngi=location.getLongitude();
        done=true;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
