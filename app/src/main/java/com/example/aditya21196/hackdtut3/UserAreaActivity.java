package com.example.aditya21196.hackdtut3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;

import java.util.Map;

import static com.example.aditya21196.hackdtut3.MainActivity._Age;
import static com.example.aditya21196.hackdtut3.MainActivity._PhnNo;
import static com.example.aditya21196.hackdtut3.MainActivity._bloodChoice;
import static com.example.aditya21196.hackdtut3.MainActivity._email;
import static com.example.aditya21196.hackdtut3.MainActivity._name;
import static com.example.aditya21196.hackdtut3.MainActivity._rBool;
import static com.example.aditya21196.hackdtut3.MainActivity._sex;
import static com.example.aditya21196.hackdtut3.MainActivity._state;

public class UserAreaActivity extends AppCompatActivity {


    private Firebase mRef;
    String UID;

    View donate;
    View request;
    View profile;
    View logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        donate=(View)findViewById(R.id.bDonate);
        request=(View)findViewById(R.id.bRequest);
        profile=(View)findViewById(R.id.bUpdate);
        logout=(View)findViewById(R.id.bLogout2) ;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserAreaActivity.this,LoginActivity.class));
            }
        });

        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAreaActivity.this,MapActivity.class));
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAreaActivity.this,Request.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(_rBool){
            if (user != null) {
                //code to store stuff in db and use values here
                UID=user.getUid();
                mRef=new Firebase("https://hackdtut3.firebaseio.com/Users/"+UID);
                addChild("Name",_name);
                addChild("Mail",_email);
                addChild("Age",_Age);
                addChild("Phone",_PhnNo);
                addChild("Sex",_sex);
                addChild("Blood",_bloodChoice);
                addChild("State",_state);

            } else {
                // No user is signed in
            }
        }else{
            //stuff already exists, read global variables
            UID=user.getUid();
            mRef= new Firebase("https://hackdtut3.firebaseio.com/Users/"+UID);
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String,String> map = dataSnapshot.getValue(Map.class);

                    _Age=map.get("Age");
                    _bloodChoice=map.get("Blood");
                    _email=map.get("Mail");
                    _state=map.get("State");
                    _PhnNo=map.get("Phone");
                    _sex=map.get("Sex");
                    _name=map.get("Name");
                }





                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }






    }

    public void addChild(String key,String value){
        Firebase mRefChild = mRef.child(key);
        mRefChild.setValue(value);
    }
}
