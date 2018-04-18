package com.starters.android.medmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat.Builder;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starters.android.medmanager.mDataBase.DBAdapter;
import com.starters.android.medmanager.notification.NotificationPublisher;

import java.util.ArrayList;
import java.util.List;

public class HomeActvity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HomeActvity.class.getCanonicalName();
    private FragmentManager mfragmentManager;
    private Fragment mfragment = null;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    //cursor that will be used to perform search query
    Cursor cursor;
    //checks when logout button is clicked
    boolean isLogout;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_from_right,R.anim.fadeout_a_bit);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(HomeActvity.this,AddMedicationActivity.class);
                startActivity(mIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set profile email
        /*SharedPreferences mPref = getSharedPreferences("LOGIN_SESSION",MODE_PRIVATE);
        TextView mProfileName = (TextView)findViewById(R.id.profileName);
        mProfileName.setText(mPref.getString("email",null));*/
        //
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        createViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        createTabIcons();
    }
    private void createTabIcons() {
        ImageView tabOne = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setImageResource(R.mipmap.ic_home_white);
        mTabLayout.getTabAt(0).setCustomView(tabOne);

        ImageView tabTwo = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setImageResource(R.mipmap.ic_notifications_none_white);
        mTabLayout.getTabAt(1).setCustomView(tabTwo);

        ImageView tabThree = (ImageView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setImageResource(R.mipmap.ic_assessment_white);
        mTabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeFragment(), "");
        adapter.addFrag(new NextNotificationFragment(), "");
        adapter.addFrag(new CategoryFragment(), "");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onPageSelected(int pageNumber) {
                // Just define a callback method in your fragment and call it like this!
                //adapter.getItem(pageNumber).imVisible();
                if(pageNumber == 2){
                    //scheduleNotification(getNotification("Medication Alart!"),500);
                    //scheduleNotification();
                    /*Animation mAnim = AnimationUtils.loadAnimation(HomeActvity.this,R.anim.fadeout);
                    fab.startAnimation(mAnim);*/
                    fab.setVisibility(View.GONE);
                }else{
                    /*Animation mAnim = AnimationUtils.loadAnimation(HomeActvity.this,R.anim.fadein);
                    fab.startAnimation(mAnim);*/
                    fab.setVisibility(View.VISIBLE);
                }
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            //mFragmentTitleList.add(title);
        }

        /*@Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }*/
    }
    //SearchView command----
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        // Retrieve the SearchView and plug it into SearchManager
        //final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, "onQueryTextSubmit ");
                DBAdapter db=new DBAdapter(HomeActvity.this);
                db.openDB();
                cursor= db.getMedicationListByKeyword(s);
                if (cursor==null){
                    Toast.makeText(HomeActvity.this,"No records found!",Toast.LENGTH_LONG).show();
                    HomeFragment.getDrugContacts();
                }else{
                    //Toast.makeText(HomeActvity.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                }
                //customAdapter.swapCursor(cursor);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                DBAdapter db=new DBAdapter(HomeActvity.this);
                db.openDB();
                Log.d(TAG, "onQueryTextChange ");
                cursor= db.getMedicationListByKeyword(s);
                if (cursor!=null){
                    //check the methods below
                    if(s.trim().length()>0) {
                        HomeFragment.loadData(s);//loads searched data from database
                    }else{
                        HomeFragment.getDrugContacts();//refreshes the listView when no search is performed
                    }
                }
                return false;
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START) && !isLogout) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fadein_a_bit,R.anim.slide_out_from_right);
        }
    }
    public void scheduleNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Medication Alert");
        builder.setContentText("It's time to take your medication...");
        Intent notificationIntent = new Intent(this, HomeActvity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        builder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setOnlyAlertOnce(true);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation mView item clicks here.
        int id = item.getItemId();
        if(id == R.id.logout){
            SharedPreferences mPref = getSharedPreferences("LOGIN_SESSION",MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mPref.edit();
            mEditor.clear();
            mEditor.apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mIntent = new Intent(HomeActvity.this,LoginActivity.class);
                    startActivity(mIntent);
                    isLogout = true;
                    onBackPressed();
                }
            },500);
        }
        if (id == R.id.exit) {
            // Handle the camera action
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
