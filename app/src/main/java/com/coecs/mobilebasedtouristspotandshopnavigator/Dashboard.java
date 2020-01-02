package com.coecs.mobilebasedtouristspotandshopnavigator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private DashboardFragment dashboardFragment;
    private UserProfileFragment userProfileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        currentUser = auth.getCurrentUser();

        databaseReference = database.getReference();

        //Checks if there is a user logged in to the App
        if(currentUser == null){
            toLogin();
        }


    }

    //Navigate to Login
    private void toLogin(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 2:
                    return new UserProfileFragment();

                case 1:


                case 0:
                    return new DashboardFragment();

            }

            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
