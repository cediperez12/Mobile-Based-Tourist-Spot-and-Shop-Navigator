package com.coecs.mobilebasedtouristspotandshopnavigator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.util.Date;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference database;
    private DatabaseReference databaseImages;
    private StorageReference storageReference;

    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private TextInputLayout tilConfirmPassword;
    private TextInputLayout tilFirstName;
    private TextInputLayout tilLastName;

    private CircleImageView civ_profile_photo;
    private MaterialButton btnSignup;
    private TextView txtvLogin;

    private Uri fetchedImageProfile;

    private RegisterTask rt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("USERS");
        databaseImages = FirebaseDatabase.getInstance().getReference("IMAGES");
        storageReference = FirebaseStorage.getInstance().getReference();

        tilEmail = findViewById(R.id.til_reg_email);
        tilPassword = findViewById(R.id.til_reg_password);
        tilConfirmPassword = findViewById(R.id.til_reg_confirm_password);
        tilFirstName = findViewById(R.id.til_first_name_register);
        tilLastName = findViewById(R.id.til_last_name_register);

        civ_profile_photo = findViewById(R.id.civ_profile_photo_register);

        btnSignup = findViewById(R.id.btn_signup_register);
        txtvLogin = findViewById(R.id.txtv_login_register);

        //Error First Error Layer

        tilEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if(s.toString().isEmpty())
                        throw new Exception("Please enter your email.");

                    if(isEmailValid(s.toString()))
                        throw new Exception("Please enter a valid email.");

                    tilEmail.setErrorEnabled(false);
                }catch (Exception ex){
                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError(ex.getMessage());

                    ex.printStackTrace();
                }
            }
        });

        tilPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if(s.toString().isEmpty())
                        throw new Exception("Please enter your password");

                    if(s.toString().length() <= 8){
                        throw new Exception("Password must be greater than 8 characters");
                    }

                    if(!s.toString().equals(tilConfirmPassword.getEditText().getText().toString())){
                        tilConfirmPassword.setErrorEnabled(true);
                        tilConfirmPassword.setError("Please confirm your password.");
                    }else{
                        tilConfirmPassword.setErrorEnabled(false);
                    }

                    tilPassword.setErrorEnabled(false);
                }catch (Exception ex){
                    tilPassword.setErrorEnabled(true);
                    tilPassword.setError(ex.getMessage());
                }
            }
        });

        tilConfirmPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if(s.toString().isEmpty())
                        throw new Exception("You have to confirm your password");

                    if(!s.toString().trim().equals(tilPassword.getEditText().getText().toString().trim()))
                        throw new Exception("Your password is not matched.");

                    tilConfirmPassword.setErrorEnabled(false);
                }catch (Exception ex){
                    tilConfirmPassword.setErrorEnabled(true);
                    tilConfirmPassword.setError(ex.getMessage());
                }
            }
        });

        tilFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if(s.toString().isEmpty())
                        throw new Exception("Please enter your First Name");

                    if(s.toString().length() > 18)
                        throw new Exception("Your name is too long.");

                    tilFirstName.setErrorEnabled(false);
                }catch (Exception ex){
                    tilFirstName.setErrorEnabled(true);
                    tilFirstName.setError(ex.getMessage());
                }
            }
        });

        tilLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if(s.toString().isEmpty())
                        throw new Exception("Please enter your Last Name");

                    if(s.toString().length() > 18)
                        throw new Exception("Your last name is too long.");

                    tilLastName.setErrorEnabled(false);
                }catch (Exception ex){
                    tilLastName.setErrorEnabled(true);
                    tilLastName.setError(ex.getMessage());
                }
            }
        });
    }

    private boolean isEmailValid(String email){
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return !email.matches(regex);
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

    public void onClickGetPicture(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                fetchedImageProfile = resultUri;

                civ_profile_photo.setImageURI(resultUri);
            }else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                result.getError().printStackTrace();
            }
        }
    }

    public void onClickSignUp(View view){
        try{
            String strEmail = tilEmail.getEditText().getText().toString().trim();
            String strPassword = tilPassword.getEditText().getText().toString().trim();
            String strConfirmPassword = tilConfirmPassword.getEditText().getText().toString().trim();

            String strFirstName = tilFirstName.getEditText().getText().toString().trim();
            String strLastName = tilLastName.getEditText().getText().toString().trim();

            if(fetchedImageProfile == null){
                throw new Exception("Please add your profile photo first.");
            }

            if(tilEmail.isErrorEnabled()){
                throw new Exception(tilEmail.getError().toString());
            }

            if(tilPassword.isErrorEnabled()){
                throw new Exception(tilPassword.getError().toString());
            }

            if(tilConfirmPassword.isErrorEnabled()){
                throw new Exception(tilConfirmPassword.getError().toString());
            }

            if(tilFirstName.isErrorEnabled()){
                throw new Exception(tilFirstName.getError().toString());
            }

            if(tilLastName.isErrorEnabled()){
                throw new Exception(tilLastName.getError().toString());
            }

            //Collect Data in the model
            UserInformation newInfo = new UserInformation(strFirstName,strLastName,null,strEmail,strPassword);

            //Back-end here
            rt = new RegisterTask(newInfo,fetchedImageProfile);
            rt.execute();

        }catch (Exception ex){
            createErrorMessageWithOkayButton("Error",ex.getMessage());
        }
    }

    public void onClickRegisterLogin(View view){
        try{
            Intent intent = new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private class RegisterTask extends AsyncTask<Void,Void,String>{

        private ProgressDialog progressDialog;

        private UserInformation newUserInformation;
        private Uri profilePhoto;

        public RegisterTask(UserInformation userInformation,Uri profilePhoto){
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating an account...");

            this.newUserInformation = userInformation;
            this.profilePhoto = profilePhoto;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            btnSignup.setEnabled(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            //Creates an account.
            auth.createUserWithEmailAndPassword(newUserInformation.getEmailAddress(),newUserInformation.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(final AuthResult authResult) {
                    //Created the account.
                    //Save a data about the image file

                    final FirebaseUser newUser = authResult.getUser();

                    final ImageInformation newImageInfo = new ImageInformation(newUser.getUid(),new Date().getTime(),null);
                    final String imageKey = databaseImages.push().getKey();

                    progressDialog.setMessage("Uploading your profile picture...");

                    final String photoLink = "images/" + newUser.getUid() + "/" + imageKey + ".png";
                    UploadTask uploadTask = storageReference.child(photoLink).putFile(profilePhoto);

                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setMessage("Uploading please wait: " + taskSnapshot.getBytesTransferred() + " / " +taskSnapshot.getTotalByteCount() );
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                newImageInfo.setPhotoLink(photoLink);
                                databaseImages.child(imageKey).setValue(newImageInfo);

                                newUserInformation.setProfilePhotoPath(photoLink);

                                progressDialog.setMessage("Creating your account...");
                                database.child(newUser.getUid()).setValue(newUserInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();

                                        AlertDialog displayDialog = new AlertDialog.Builder(Register.this)
                                                .setTitle("Administrator")
                                                .setMessage("You successfully created your account. Congrats! You have to enter your account again to login.")
                                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        auth.signOut();

                                                        Intent intent = new Intent(Register.this,Login.class);
                                                        startActivity(intent);
                                                        Register.this.finish();
                                                    }
                                                })
                                                .setCancelable(false)
                                                .create();

                                        displayDialog.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        createErrorMessageWithOkayButton("Error",e.getMessage());
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    createErrorMessageWithOkayButton("Error",e.getMessage());
                    progressDialog.dismiss();
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            btnSignup.setEnabled(true);
        }
    }
}
