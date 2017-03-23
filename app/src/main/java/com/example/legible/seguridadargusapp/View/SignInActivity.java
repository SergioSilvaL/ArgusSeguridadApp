package com.example.legible.seguridadargusapp.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.legible.seguridadargusapp.FirebaseExceptionConstants;
import com.example.legible.seguridadargusapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignInActivity extends AppCompatActivity{

    private String email;
    private String password;
    private EditText inputEmail, inputPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check to see if user is alreaedy loggedIn
        checkUserAuth();

        //CheckInternetAuthentication
        checkIntenetAuthentication();

        //Set view
        setView();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    private void checkIntenetAuthentication() {
        //Check if thier is an Internet Connection


        //User No Internet Authentication


        //Thier is Internet
        //No Probelem LogIn Regulary

        //Thier is no Internet

        // User is already logged In

        //--> User Logs in directly but with no Internet Status

        //User wasnt logged In

        //--> User won't be able to log In untill thier is an Internet Connection
    }
    private void setView(){
        //set the view
        setContentView(R.layout.activity_sign_in);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonLogin = (Button) findViewById(R.id.btn_login);
    }
    private boolean isValidEmail(String email){

        if (!isValidEmailFormat(email)){
            //inputEmail.setError(getString(R.string.invalid_format_email));
        }
        if (TextUtils.isEmpty(email)) {
            //inputEmail.setError(getString(R.string.empty_email));
        }

        return  !TextUtils.isEmpty(email) && isValidEmailFormat(email);
    }
    private boolean isValidPassword(String password){

        if (TextUtils.isEmpty(password)) {
            //inputEmail.setError(getString(R.string.empty_email));
        }

        if (!isValidPasswordLength(password)){
            //inputPassword.setError(getString(R.string.invalid_format_password));
        }
        return !TextUtils.isEmpty(password) && isValidPasswordLength(password);
    }
    private boolean isValidEmailFormat(String email){
        return email.contains("@");
    }
    private boolean isValidPasswordLength(String password){
        return password.length()>=6;
    }
    private void checkUserAuth(){
        // Log In User Directly if already Signed In

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
            finish();
        }
    }
    private void authenticateUser(String email, String password){

        //authenticate user

        progressBar.setVisibility(View.VISIBLE);


        auth.signInWithEmailAndPassword(email, password)


                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);

                        String exception = FirebaseExceptionConstants.getFirebaseExceptionConstants(
                                (((FirebaseAuthException) e).getErrorCode()));

                        String inputErrorField = FirebaseExceptionConstants.getFirebaseExceptionConstantsErrorInputField(
                                (((FirebaseAuthException) e).getErrorCode()));

                        if (inputErrorField.equals("EmailField")){
                            inputEmail.setError(exception);
                            return;
                        }
                        if (inputErrorField.equals("PasswordField")){
                            inputPassword.setError(exception);
                            return;
                        }

                    }
                });
    }
    private void signIn(){

        // Get the email from user input
        email = inputEmail.getText().toString().toLowerCase().trim();

        // Get the password from user input
        password = inputPassword.getText().toString();

        if(isValidEmail(email) && isValidPassword(password)){
            authenticateUser(email,password);
        }
    }

}
