package com.starters.android.medmanager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.starters.android.medmanager.mDataBase.Constants;
import com.starters.android.medmanager.mDataBase.DBAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMedicationActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    //medicine name and description textViews
    AppCompatEditText mName,mDesc;
    //the add button
    AppCompatTextView mAddBtn;
    //start and end mStartDate buttons
    TextView mStartDateBtn,mEndDateBtn;
    int mInterval,mMornSelector,mAfterSelector,mEvenSelector;
    //--------
    Context context = this;
    Calendar mStartCalendar = Calendar.getInstance();
    Calendar mEndCalendar = Calendar.getInstance();
    String dateFormat = "EEE,dd.MMM.yy";
    DatePickerDialog.OnDateSetListener mStartDate,mEndDate;
    SimpleDateFormat mStartSimpleDF = new SimpleDateFormat(dateFormat, Locale.getDefault());
    SimpleDateFormat mEndSimpleDF = new SimpleDateFormat(dateFormat, Locale.getDefault());
    Spinner mMorningSpinner,mAfternoonSpinner,mEveningSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.fadeout_a_bit);
        setContentView(R.layout.activity_add_medication);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //--------Initialize the name and description of the medication
        mName = (AppCompatEditText)findViewById(R.id.medName);
        mDesc = (AppCompatEditText)findViewById(R.id.medDesc);
        //--------Initializing spinner buttons for frequency settings
        loadAndSyncSpinner();
        //--------Initialize starting and ending mStartDate
        startAndEndDateSettings();
    }

    /**
     * Loads and assigns onItemSelectedListener to Spinners
     */
    private void loadAndSyncSpinner() {
        mMorningSpinner = (Spinner)findViewById(R.id.morningSpin);
        mAfternoonSpinner = (Spinner)findViewById(R.id.afternoonSpin);
        mEveningSpinner = (Spinner)findViewById(R.id.eveningSpin);
        mMorningSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String morningSpinSelector = mMorningSpinner.getSelectedItem().toString();
                if (!morningSpinSelector.equals("0")) {
                    mMornSelector = 1;
                } else {
                    mMornSelector = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mAfternoonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String afternoonSpinSelector = mAfternoonSpinner.getSelectedItem().toString();
                if (!afternoonSpinSelector.equals("0")) {
                    mAfterSelector = 1;
                } else {
                    mAfterSelector = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mEveningSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String eveningSpinSelector = mEveningSpinner.getSelectedItem().toString();
                if (!eveningSpinSelector.equals("0")) {
                    mEvenSelector = 1;
                } else {
                    mEvenSelector = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Medication starting and ending date settings
     */
    private void startAndEndDateSettings(){
        mStartDateBtn = (TextView)findViewById(R.id.startDateBtn);
        mEndDateBtn = (TextView)findViewById(R.id.endDateBtn);

        //--------Add button
        mAddBtn = (AppCompatTextView)findViewById(R.id.addBtn);

        //contacting the onclick listener
        mStartDateBtn.setOnClickListener(this);
        mEndDateBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
        //
        mStartDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                mStartCalendar.set(Calendar.YEAR, year);
                mStartCalendar.set(Calendar.MONTH, monthOfYear);
                mStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mStartDateBtn.setText(mStartSimpleDF.format(mStartCalendar.getTime()));
            }

        };
        mEndDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                mEndCalendar.set(Calendar.YEAR, year);
                mEndCalendar.set(Calendar.MONTH, monthOfYear);
                mEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //updateDate();
                mEndDateBtn.setText(mEndSimpleDF.format(mEndCalendar.getTime()));
            }

        };
    }
    /**
     * This method saves medication data to database
     * @param name medication name
     * @param desc medication description
     * @param interval the frequency or mInterval which the medication should be taken. eg(3 times a day).
     * @param date the starting and ending mStartDate for the medication
     */
    private void save(String name,String desc,String interval,String date)
    {
        Date now = new Date();
        DateFormat sdf;
        //sdf = new SimpleDateFormat("EEE dd MMM yyyy 'Time:'hh:mm a ");
        sdf = new SimpleDateFormat("MMM");
        String mCurrentMonth = sdf.format(now);
        DBAdapter db=new DBAdapter(this);
        db.openDB();
        boolean saved=db.add(name,desc,interval,date);
        //Toast.makeText(this, mCurrentMonth, Toast.LENGTH_SHORT).show();
        if(saved)
        { 
            SharedPreferences mPref = getSharedPreferences("MEDICATION_ADDED",MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mPref.edit();
            mEditor.putBoolean("medication_added",true);
            mEditor.apply();
            //save monthly intake count in SharedPreferences storage using the current month
            SharedPreferences mPref4Category = getSharedPreferences("MONTHLY_INTAKE",MODE_PRIVATE);
            SharedPreferences.Editor mEditor4Category = mPref4Category.edit();
            //if no counter has been created, create one
            if(!mPref4Category.getBoolean("isMonthlyCounter",false)) {
                mEditor4Category.putBoolean("isMonthlyCounter",true);
                mEditor4Category.putInt(mCurrentMonth+Constants.MONTHLY_COUNTER, 1);
                mEditor4Category.apply();
            }else{
                //load the counter stored, increment it and store it back to SharedPreferences storage
                int mCounter = mPref4Category.getInt(mCurrentMonth+ Constants.MONTHLY_COUNTER,0);
                mCounter += 1;
                mEditor4Category.putInt(mCurrentMonth+Constants.MONTHLY_COUNTER,mCounter);
                mEditor4Category.apply();
            }
            onBackPressed();
        }else {
            Toast.makeText(this,"Unable To Save",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * get the data to be saved from text boxes before saving
     */
    private void preSave(){
        String name = mName.getText().toString();
        String desc = mDesc.getText().toString();
        SharedPreferences mPref = getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPref.edit();
        String morningSpin = null;
        String afternoonSpin = null;
        String eveningSpin = null;
        String startDate = mStartDateBtn.getText().toString();
        String endDate = mEndDateBtn.getText().toString();
        String morningSpinSelector = mMorningSpinner.getSelectedItem().toString();
        String afternoonSpinSelector = mAfternoonSpinner.getSelectedItem().toString();
        String eveningSpinSelector = mEveningSpinner.getSelectedItem().toString();
        if(mMornSelector == 1){
            morningSpin = mMorningSpinner.getSelectedItem().toString();
            mEditor.putString("morning",morningSpin);
            mEditor.apply();
        }
        if(mAfterSelector == 1){
            afternoonSpin = " - "+mAfternoonSpinner.getSelectedItem().toString();
            mEditor.putString("afternoon",afternoonSpin);
            mEditor.apply();
        }
        if(mEvenSelector == 1){
            eveningSpin = " - "+mEveningSpinner.getSelectedItem().toString();
            mEditor.putString("evening",eveningSpin);
            mEditor.apply();
        }
        if(morningSpinSelector.equals("0") && afternoonSpinSelector.equals("0") && eveningSpinSelector.equals("0")){
            Toast.makeText(this, "No mInterval selected", Toast.LENGTH_SHORT).show();
        }else{
            mInterval = (mMornSelector+mAfterSelector+mEvenSelector);
            String date = (startDate+" - "+endDate);
            //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
            save(name,desc,""+ mInterval,date);
        }
    }
    @Override
    public void onClick(View v) {
        if(v == mStartDateBtn){
            new DatePickerDialog(context, mStartDate, mStartCalendar
                    .get(Calendar.YEAR), mStartCalendar.get(Calendar.MONTH),
                    mStartCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(v == mEndDateBtn){
            new DatePickerDialog(context, mEndDate, mEndCalendar
                    .get(Calendar.YEAR), mEndCalendar.get(Calendar.MONTH),
                    mEndCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(v == mAddBtn){
            preSave();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein_a_bit,R.anim.slide_out_from_right);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
















