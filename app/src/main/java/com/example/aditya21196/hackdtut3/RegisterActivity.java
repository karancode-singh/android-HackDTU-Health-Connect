package com.example.aditya21196.hackdtut3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.aditya21196.hackdtut3.MainActivity._Age;
import static com.example.aditya21196.hackdtut3.MainActivity._PhnNo;
import static com.example.aditya21196.hackdtut3.MainActivity._bloodChoice;
import static com.example.aditya21196.hackdtut3.MainActivity._email;
import static com.example.aditya21196.hackdtut3.MainActivity._name;
import static com.example.aditya21196.hackdtut3.MainActivity._rBool;
import static com.example.aditya21196.hackdtut3.MainActivity._sex;
import static com.example.aditya21196.hackdtut3.MainActivity._state;

public class RegisterActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmailR;
    EditText etPwdR;
    EditText etAge;
    EditText etPhnNo;
    Button bRegister;

    String password;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;


    int sexChoice;

    Spinner bloodGroupSpinner;

    AutoCompleteTextView acView;
    String[] states = {
            "Andhra Pradesh",
            "Arunachal Pradesh",
            "Assam",
            "Bihar",
            "Chhattisgarh",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal",
            "Andaman and Nicobar Islands",
            "Chandigarh",
            "Dadra and Nagar Haveli",
            "Daman and Diu",
            "Lakshadweep",
            "Delhi",
            "Puducherry",
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText)findViewById(R.id.etName);
        etEmailR = (EditText)findViewById(R.id.etMail);
        etPwdR = (EditText)findViewById(R.id.etPwdR);
        etAge = (EditText)findViewById(R.id.etAge);
        etPhnNo = (EditText)findViewById(R.id.etPhoneNo);

        mAuth = FirebaseAuth.getInstance();

        RadioGroup sexRadioGroup = (RadioGroup) findViewById(R.id.sexRadioGroup);
        sexChoice = sexRadioGroup.getCheckedRadioButtonId();


        bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        bRegister = (Button)findViewById(R.id.bRegister);
        acView=(AutoCompleteTextView)findViewById(R.id.aCState);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,states);
        acView.setThreshold(1);
        acView.setAdapter(adapter);
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }





    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void registerUser() {
        _rBool=true;
        _name=etName.getText().toString();
        _email = etEmailR.getText().toString();
        _PhnNo = etPhnNo.getText().toString();
        password = etPwdR.getText().toString();
        _Age=etAge.getText().toString();
        _state = acView.getText().toString();
        _bloodChoice = String.valueOf(bloodGroupSpinner.getSelectedItem());
        switch (sexChoice) {
            case R.id.male:
                //some code
                _sex="male";
                break;
            case R.id.female:
                //some code
                _sex="female";
                break;
        }





        mAuth.createUserWithEmailAndPassword(_email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration failed!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
