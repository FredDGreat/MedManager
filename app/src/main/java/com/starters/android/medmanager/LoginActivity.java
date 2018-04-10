package com.starters.android.medmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener{
    private AppCompatEditText mEmail,mPassword;
    private AppCompatTextView mSignUpBtn,mLoginBtn;
    private LinearLayout linkForgotPassword,linkSignInWithGoogle;
    private GooglePlusSignInHelper gSignInHelper;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);// Configure sign-in to request the user's ID, email address, and basic
        /*// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();*/
        // Build a GoogleSignInClient with the options specified by gso.
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        mEmail = (AppCompatEditText)findViewById(R.id.email);
        mPassword = (AppCompatEditText)findViewById(R.id.password);
        //
        mSignUpBtn = (AppCompatTextView)findViewById(R.id.signUpBtn);
        mLoginBtn = (AppCompatTextView)findViewById(R.id.signInBtn);
        //
        mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
        //
        linkForgotPassword = (LinearLayout)findViewById(R.id.linkForgotPassword);
        linkSignInWithGoogle = (LinearLayout)findViewById(R.id.linkSignInWithGoogle);

        mEmail.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        //------
        mSignUpBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        //------
        linkForgotPassword.setOnClickListener(this);
        linkSignInWithGoogle.setOnClickListener(this);
    }

    /**
     * validates the email and password before signing in
     */
    public void validateTextBox(){
        String mEmailStr = mEmail.getText().toString();
        String mPwdStr = mPassword.getText().toString();
        if(mEmailStr.trim().isEmpty() && mPwdStr.trim().isEmpty()){
            Toast.makeText(this, "Box empty!", Toast.LENGTH_SHORT).show();
        }else
        if(!mEmailStr.trim().isEmpty() && !mPwdStr.trim().isEmpty()){
            if(mPwdStr.trim().length()>7){
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        Intent mIntent = new Intent(LoginActivity.this,HomeActvity.class);
                        startActivity(mIntent);
                        finish();
                    }
                },2000);
            }else{
                Toast.makeText(this, "Password is too short... Not less than 8 characters", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Email/Password incorrect!", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * retrieves user's password when called
     */
    public void retrievePassword(){

    }
    /**
     * Calls the GoogleSignIn function
     */
    public void doGoogleSignIn(){

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if(v == mSignUpBtn){
            Intent mIntent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(mIntent);
        }
        if(v == mLoginBtn){
            validateTextBox();
        }
        if(v == linkForgotPassword){
            retrievePassword();
        }
        if(v == linkSignInWithGoogle){
            doGoogleSignIn();
        }
    }
}
