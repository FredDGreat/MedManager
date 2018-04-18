package com.starters.android.medmanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

import com.starters.android.medmanager.mDataBase.Constants;
import com.starters.android.medmanager.mDataBase.DBAdapter;
import com.starters.android.medmanager.mDataBase.DBAdapterRegAndLogin;
import com.starters.android.medmanager.mDataObject.DrugContact;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener{
    private AppCompatEditText mEmail,mPassword;
    private AppCompatTextView mSignUpBtn,mLoginBtn;
    private LinearLayout linkForgotPassword,linkSignInWithGoogle;
    //private GooglePlusSignInHelper gSignInHelper;
    private ProgressBar mProgressBar;
    //Database helper for registration and login
    DBAdapterRegAndLogin mDb;
    //boolean for email and password verification
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if no mView has focus:
        SharedPreferences mPref = getSharedPreferences("LOGIN_SESSION",MODE_PRIVATE);
        if (mPref.getBoolean("has_logged_in", false)) {
            Intent mIntent = new Intent(LoginActivity.this,HomeActvity.class);
            startActivity(mIntent);
            finish();
        }else
        {
            //create database object
            mDb = new DBAdapterRegAndLogin(this);
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            mEmail = (AppCompatEditText) findViewById(R.id.email);
            mPassword = (AppCompatEditText) findViewById(R.id.password);
            //
            mSignUpBtn = (AppCompatTextView) findViewById(R.id.signUpBtn);
            mLoginBtn = (AppCompatTextView) findViewById(R.id.signInBtn);
            //
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            //
            linkForgotPassword = (LinearLayout) findViewById(R.id.linkForgotPassword);
            linkSignInWithGoogle = (LinearLayout) findViewById(R.id.linkSignInWithGoogle);

            mEmail.setOnClickListener(this);
            mPassword.setOnClickListener(this);
            //------
            mSignUpBtn.setOnClickListener(this);
            mLoginBtn.setOnClickListener(this);
            //------
            linkForgotPassword.setOnClickListener(this);
            linkSignInWithGoogle.setOnClickListener(this);
        }
    }

    /**
     * validates the email and password before signing in
     */
    public void validateTextBox(){
        final String mEmailStr = mEmail.getText().toString();
        final String mPwdStr = mPassword.getText().toString();
        if(mEmailStr.trim().isEmpty() && mPwdStr.trim().isEmpty()){
            Toast.makeText(this, "Box empty!", Toast.LENGTH_SHORT).show();
        }else
        if(!mEmailStr.trim().isEmpty() && !mPwdStr.trim().isEmpty()){
            if(mPwdStr.trim().length()>=7){
                mProgressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        compareEmailAndPassword(mEmailStr,mPwdStr);
                    }
                },2000);
            }else{
                Toast.makeText(this, "Password too short... Not less than 8 characters", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Email/Password incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * draws all email and password data from database and compares it with user's typed email and password
     */
    public void compareEmailAndPassword(final String email,final String password)
    {
        //boolean toFirst = c.moveToFirst();
        //user_name = cursor.getString(cursor.getColumnIndex(Information.NAME));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                boolean mEmailMatch = false,mPassowrdMatch = false;
                mDb.openDB();
                Cursor c = mDb.retrieve();
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    if(email.equalsIgnoreCase(c.getString(c.getColumnIndex("email")))){
                        mEmailMatch = true;
                    }else{
                        mEmailMatch = false;
                    }
                    if(password.equalsIgnoreCase(c.getString(c.getColumnIndex("password")))){
                        mPassowrdMatch = true;
                    }else{
                        mPassowrdMatch = false;
                    }
                    c.moveToNext();
                }
                mDb.closeDB();
                mProgressBar.setVisibility(View.GONE);
                if(mEmailMatch && mPassowrdMatch){
                    SharedPreferences mPref = getSharedPreferences("LOGIN_SESSION",MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mPref.edit();
                    mEditor.putBoolean("has_logged_in",true);
                    mEditor.apply();
                    Intent mIntent = new Intent(LoginActivity.this,HomeActvity.class);
                    startActivity(mIntent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Email/Password incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
