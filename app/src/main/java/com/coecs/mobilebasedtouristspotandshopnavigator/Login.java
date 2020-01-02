package com.coecs.mobilebasedtouristspotandshopnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private DatabaseReference reference;

    private TextInputLayout textInputLayout_email;
    private TextInputLayout textInputLayout_password;
    private Button btnLogin;
    private Button btnRegister;

    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginTask != null){
            loginTask.cancel(true);
        }
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference();

        textInputLayout_email = findViewById(R.id.textinputlayout_email_text);
        textInputLayout_password = findViewById(R.id.textinputlayout_password);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.sign_up_button);
    }

    //Login Function
    public void onClickLogin(View view){
        try{
            String textEmail = textInputLayout_email.getEditText().getText().toString().trim();
            String textPassword = textInputLayout_password.getEditText().getText().toString().trim();

            if(textEmail.isEmpty()){
                textInputLayout_email.setErrorEnabled(true);
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
                textInputLayout_password.setErrorEnabled(true);
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

            loginTask = new LoginTask(textEmail,textPassword);
            loginTask.execute();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void onClickRegister(View view){
        try{
            Intent intent = new Intent(getApplicationContext(),Register.class);
            startActivity(intent);
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void createErrorMessageWithOkayButton(String title,String message){
        AlertDialog errorDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Okay",null)
                .setCancelable(false)
                .setIcon(android.R.drawable.stat_sys_warning)
                .create();

        errorDialog.show();
    }

    private class LoginTask extends AsyncTask<Void,Void,String>{
        private String email;
        private String password;

        private ProgressDialog progressDialog;

        public LoginTask(String email,String password){
            this.email = email;
            this.password = password;

            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnLogin.setEnabled(false);
            /*
            UI thread changes here. Before the Thread runs.
             */
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);

                auth.signInWithEmailAndPassword(email,password).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        createErrorMessageWithOkayButton("Error Occured while Logging in.",e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            btnLogin.setEnabled(true);
            /*
            UI thread changes here. After the Thread runs.
             */
        }
    }
}
