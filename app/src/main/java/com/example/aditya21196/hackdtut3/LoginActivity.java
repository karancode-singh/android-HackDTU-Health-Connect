package com.example.aditya21196.hackdtut3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPwdField;

    private Button mSignUpBtn;
    private Button mLogInBtn;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField=(EditText)findViewById(R.id.etEmailL);
        mPwdField=(EditText)findViewById(R.id.etPwdL);
        mLogInBtn = (Button)findViewById(R.id.bLogin);
        mSignUpBtn = (Button)findViewById(R.id.bSignup);
        mAuth = FirebaseAuth.getInstance();

        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    startActivity(new Intent(LoginActivity.this,UserAreaActivity.class));
                } else {
                    // User is signed out
                }

            }
        };


    }




    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){

        String email = mEmailField.getText().toString();
        String pwd = mPwdField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)) {

            Toast.makeText(LoginActivity.this,"Text field empty",Toast.LENGTH_LONG).show();

        }else{


            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginActivity.this, "Sign In problem", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }

    private void startSignUp() {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}
