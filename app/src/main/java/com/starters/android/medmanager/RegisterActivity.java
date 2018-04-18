package com.starters.android.medmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.starters.android.medmanager.mDataBase.DBAdapterRegAndLogin;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener{
    AppCompatEditText mFullName,mEmail,mPassword,mMobile;
    AppCompatTextView mSignUpBtn;
    LinearLayout havAnAccountBtn;
    String at = "@";
    boolean mValidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.fadeout_a_bit);
        setContentView(R.layout.activity_register);
        mFullName = (AppCompatEditText)findViewById(R.id.fullName);
        mEmail = (AppCompatEditText)findViewById(R.id.email);
        mPassword = (AppCompatEditText)findViewById(R.id.password);
        mMobile = (AppCompatEditText)findViewById(R.id.mobile);

        mSignUpBtn = (AppCompatTextView)findViewById(R.id.signUpBtn);
        havAnAccountBtn = (LinearLayout)findViewById(R.id.havAnAccountBtn);
        //
        mSignUpBtn.setOnClickListener(this);
        havAnAccountBtn.setOnClickListener(this);
    }

    /**
     * Saves registration credentials to database
     * @param fullName user's full name
     * @param email user's email
     * @param password user's password
     * @param mobile user's mobile number
     */
    private void save(String fullName,String email,String password,String mobile)
    {
        DBAdapterRegAndLogin db=new DBAdapterRegAndLogin(this);
        db.openDB();
        boolean saved=db.add(fullName,email,password,mobile);

        if(saved)
        {
            SharedPreferences mPref = getSharedPreferences("ACCOUNT_CREATED",MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mPref.edit();
            mEditor.putBoolean("account_created",true);
            mEditor.apply();
            Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show();
            onBackPressed();
        }else {
            Toast.makeText(this,"Unable To Create Account",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * get the data to be saved from text boxes before saving
     */
    private void preSave(){
        String fName = mFullName.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String mobile = mMobile.getText().toString();
        //check for real email
        int atIndex = email.indexOf(at);
        String fromAt = email.substring(atIndex,atIndex+2);
        if(!fName.isEmpty() && fName.contains(" ")){
            mValidate = true;
        }else{
            mValidate = false;
            Toast.makeText(this, "Full name is required!", Toast.LENGTH_SHORT).show();
        }
        if(!email.isEmpty() && email.contains(at) && !email.startsWith(at) && !email.endsWith(at)){
            mValidate = true;
        }else{
            mValidate = false;
            Toast.makeText(this, "Email Error!", Toast.LENGTH_SHORT).show();
        }
        if(!password.isEmpty() && password.trim().length()>=7){
            mValidate = true;
        }else{
            mValidate = false;
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
        }
        if(!mobile.isEmpty() && mobile.trim().length() == 11){
            mValidate = true;
        }else{
            mValidate = false;
            Toast.makeText(this, "Number Error!", Toast.LENGTH_SHORT).show();
        }
        if(mValidate){
            save(fName,email,password,mobile);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein_a_bit,R.anim.slide_out_from_right);
    }

    @Override
    public void onClick(View v) {
        if(v == mSignUpBtn){
            preSave();
        }
        if(v == havAnAccountBtn){
            onBackPressed();
        }
    }
}
