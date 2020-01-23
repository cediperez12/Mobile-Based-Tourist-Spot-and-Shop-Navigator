package com.coecs.mobilebasedtouristspotandshopnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    private FirebaseAuth auth;

    private FirebaseUser currentUser;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        try{
            Thread.sleep(5000);

            if(currentUser == null){
                toLogin();
            }else{
                toDashboard();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void toLogin(){
        intent = new Intent();
        intent.setClass(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    private void toDashboard(){
        intent = new Intent();
        intent.setClass(getApplicationContext(),Dashboard_Nav_Drawer.class);
        startActivity(intent);
        finish();
    }
}
