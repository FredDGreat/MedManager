package com.starters.android.medmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.fadeout_a_bit);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.fadein_a_bit,R.anim.slide_out_from_right);
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }
}
