package com.starters.android.medmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.starters.android.medmanager.mDataBase.DBAdapter;
import com.starters.android.medmanager.mDataObject.DrugContact;
import com.starters.android.medmanager.mListView.CustomAdapter;
import com.starters.android.medmanager.mListView.MyViewHolder;
import com.starters.android.medmanager.notification.NotificationPublisher;

import java.util.ArrayList;
import android.os.Handler;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    //home listView
    static ListView sHomeListView;
    static ArrayList<DrugContact> sDrugContacts =new ArrayList<DrugContact>();
    static CustomAdapter sAdapter;
    static DBAdapter sDb;
    final Boolean forUpdate=true;
    EditText nameEditText;
    View mView,mViewHolder;
    AppCompatTextView saveBtn,retrieveBtn;
    LayoutInflater mInflater;
    LinearLayout mContainer;
    MyViewHolder holder;
    static TextView sDataInfo;
    public HomeFragment() {
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        sHomeListView = (ListView) mView. findViewById(R.id.framentHomeListId);
        sDataInfo = (TextView) mView. findViewById(R.id.databaseDataInfo);
        sAdapter = new CustomAdapter(getActivity(), sDrugContacts);
        sDb = new DBAdapter(getContext());
        //sAdapter = new CustomAdapter(getActivity(),sDrugContacts);
        return mView;
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
        DBAdapter db=new DBAdapter(getContext());
        db.openDB();
        boolean saved=db.add(name,desc,interval,date);

        if(saved)
        {
            /*//nameEditText.setText("");
            getDrugContacts();*/
        }else {
            Toast.makeText(getContext(),"Unable To Save",Toast.LENGTH_SHORT).show();
        }
    }
    private void displayDialog(Boolean forUpdate)
    {
        Dialog d=new Dialog(getContext());
        d.setTitle("MEDICATION DATA");
        d.setContentView(R.layout.dialog_layout);

        nameEditText= (EditText) d.findViewById(R.id.medName);
        saveBtn= (AppCompatTextView) d.findViewById(R.id.saveBtn);
        retrieveBtn= (AppCompatTextView) d.findViewById(R.id.retrieveBtn);

        if(!forUpdate)
        {
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    save(nameEditText.getText().toString(),"","","");
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDrugContacts();
                }
            });
        }else {

            //SET SELECTED TEXT
            nameEditText.setText(sAdapter.getSelectedItemName());

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(nameEditText.getText().toString(),"desc","mInterval","mStartDate");
                }
            });
            retrieveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDrugContacts();
                }
            });
        }

        d.show();

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences mPref = getContext().getSharedPreferences("MEDICATION_ADDED",MODE_PRIVATE);
        if(mPref.getBoolean("medication_added",false)){
            getDrugContacts();
        }
    }
    /**
     * RETRIEVE OR gets the medication data from database
     */
    public static void getDrugContacts()
    {
        //boolean toFirst = c.moveToFirst();
        //user_name = cursor.getString(cursor.getColumnIndex(Information.NAME));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sDrugContacts.clear();
                sDb.openDB();
                Cursor c=sDb.retrieve();
                DrugContact drugContact =null;
                //boolean cToFirst = c.moveToFirst();
                while (c.moveToNext()) {
                    int id = c.getInt(0);
                    String name = c.getString(1);
                    String desc = c.getString(2);
                    String interval = c.getString(3);
                    String date = c.getString(4);

                    drugContact = new DrugContact();
                    drugContact.setId(id);
                    drugContact.setMedName(name);
                    drugContact.setDesc(desc);
                    drugContact.setInterval(interval);
                    drugContact.setStartNEndDate(date);
                    sDrugContacts.add(drugContact);
                }
                sDb.closeDB();
                if(sDrugContacts.size() > 0){
                    sDataInfo.setVisibility(View.GONE);
                }else{
                    sDataInfo.setVisibility(View.VISIBLE);
                    sDataInfo.setText("No medical\\ndata found\\ncreate one below");
                }
                sHomeListView.setAdapter(sAdapter);
            }
        });

    }

    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(getContext(), NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getContext());
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_notifications_dark);
        return builder.build();
    }
    /**
     * RETRIEVE OR gets the medication data from database by
     */
    public static void getDrugContactsByKeywords(final String search)
    {
        //boolean toFirst = c.moveToFirst();
        //user_name = cursor.getString(cursor.getColumnIndex(Information.NAME));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sDrugContacts.clear();
                sDb.openDB();
                Cursor c=sDb.getMedicationListByKeyword(search);
                DrugContact drugContact =null;
                //boolean cToFirst = c.moveToFirst();
                while (c.moveToNext())
                {
                    int id=c.getInt(0);
                    String name=c.getString(1);
                    String desc=c.getString(2);
                    String interval=c.getString(3);
                    String date =c.getString(4);

                    drugContact =new DrugContact();
                    drugContact.setId(id);
                    drugContact.setMedName(name);
                    drugContact.setDesc(desc);
                    drugContact.setInterval(interval);
                    drugContact.setStartNEndDate(date);
                    sDrugContacts.add(drugContact);
                }

                sDb.closeDB();
                if(sDrugContacts.size() > 0){
                    sDataInfo.setVisibility(View.GONE);
                }else{
                    sDataInfo.setVisibility(View.VISIBLE);
                    sDataInfo.setText("No Match found!");
                }
                sHomeListView.setAdapter(sAdapter);
            }
        });

    }

    /**
     * This method triggers @getDrugContactsByKeywords method
     * @param newText the string to search from database
     */
    public static void loadData(String newText)

    {
        getDrugContactsByKeywords(newText);

    }
    /**
     * UPDATE OR EDIT the medication data
     * @param newName medication name
     * @param newDesc medication description
     * @param newInterval the frequency or mInterval which the medication should be taken. eg(3 times a day).
     * @param newDate the starting and ending mStartDate for the medication
     */
    private void update(String newName,String newDesc,String newInterval,String newDate)
    {
        //GET ID OF SPACECRAFT
        int id= sAdapter.getSelectedItemID();

        //UPDATE IN DB
        DBAdapter db=new DBAdapter(getContext());
        db.openDB();
        boolean updated=db.update(newName,newDesc,newInterval,newDate,id);
        db.closeDB();

        if(updated)
        {
            nameEditText.setText(newName);
            getDrugContacts();
        }else {
            Toast.makeText(getContext(),"Unable To Update",Toast.LENGTH_SHORT).show();
        }

    }

    private void delete()
    {
        //GET ID
        int id= sAdapter.getSelectedItemID();

        //DELETE FROM DB
        DBAdapter db=new DBAdapter(getContext());
        db.openDB();
        boolean deleted=db.delete(id);
        db.closeDB();

        if(deleted)
        {
            Toast.makeText(getContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
            getDrugContacts();
        }else {
            Toast.makeText(getContext(),"Unable To Delete",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title=item.getTitle();
        /*if(title=="NEW")
        {
            displayDialog(!forUpdate);

        }else  if(title=="EDIT")
        {
            displayDialog(forUpdate);

        }*/
        if(title=="DELETE")
        {
            delete();
        }

        return super.onContextItemSelected(item);
    }
}
