package com.coecs.mobilebasedtouristspotandshopnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private DatabaseReference reference;

    private TextInputLayout textInputLayout_email;
    private TextInputLayout textInputLayout_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        textInputLayout_email = findViewById(R.id.textinputlayout_email_text);
        textInputLayout_password = findViewById(R.id.textinputlayout_password);
    }

    //Login Function
    public void onClickLogin(View view){
        try{
            String textEmail = textInputLayout_email.getEditText().getText().toString().trim();
            String textPassword = textInputLayout_password.getEditText().getText().toString().trim();

            if(textEmail.isEmpty()){
                textInputLayout_email.setError("Please enter your email first.");
                textInputLayout_email.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().isEmpty()){
                            textInputLayout_email.setErrorEnabled(false);
                        }else{
                            textInputLayout_email.setErrorEnabled(true);
                        }
                    }
                });

                throw new Exception("Email Not Found");
            }

            if(textPassword.isEmpty()){
                textInputLayout_password.setError("Password cannot be empty!");
                textInputLayout_password.getEditText().addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().isEmpty()){
                            textInputLayout_password.setErrorEnabled(false);
                        }else{
                            textInputLayout_password.setErrorEnabled(true);
                        }
                    }
                });

                throw new Exception("Password not Found");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void onClickRegister(View view){

    }

    private class LoginTask extends AsyncTask<Void,Void,String>{
        String email;
        String password;

        public LoginTask(String email,String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
