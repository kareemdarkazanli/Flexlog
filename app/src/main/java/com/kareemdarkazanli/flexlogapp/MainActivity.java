package com.kareemdarkazanli.flexlogapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends FragmentActivity {

    private com.kareemdarkazanli.flexlogapp.CustomViewPager mainPager;
    private MainPagerActivity dayPagerActivity;
    private long userExercisesId;
    private BroadcastReceiver mMessageReceiver;


    Activity a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        super.onCreate(savedInstanceState);
        try{
            LibraryPhoto libraryPhoto = LibraryPhoto.get(this);


            //userExercisesLibrary = LibraryDayWorkout.get(userExercisesObjects, this);
            //userExercisesLibrary.saveDayWorkouts();
            LibraryDayWorkout userExercisesLibrary = LibraryDayWorkout.get(this);
            LibraryExercise exerciseLibrary = LibraryExercise.get(this);        //user.pinInBackground();
        }catch (Exception e){

        }


        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(displayImageOptions).build();
        ///////////////////////////
        ImageLoader.getInstance().init(imageLoaderConfiguration);

        setContentView(R.layout.activity_day);
        mainPager = (com.kareemdarkazanli.flexlogapp.CustomViewPager) findViewById(R.id.mainPager);



        //userExercisesId = getIntent().getLongExtra(FragmentDay.EXTRA_USER_EXERCISE_ID, 0);
        dayPagerActivity = new MainPagerActivity(getSupportFragmentManager());



        mainPager.setAdapter(dayPagerActivity);
        mainPager.setCurrentItem(1);
        mainPager.setScrollDurationFactor(1);

        mainPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
          mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("message");
                if(message.equals("history")){
                    mainPager.setCurrentItem(0);
                }else if(message.equals("home"))
                {
                    mainPager.setCurrentItem(1);

                }else if(message.equals("camera")){
                    mainPager.setCurrentItem(2);
                }
                else if(message.equals("gallery")){
                    mainPager.setCurrentItem(3);
                }
                else if(message.equals("freeze")){
                    mainPager.setPagingEnabled(false);
                }else if(message.equals("release")){
                    mainPager.setPagingEnabled(true);
                    mainPager.setEnabled(false);
                }
                else if(message.equals("newphoto")){
                    mainPager.setPagingEnabled(true);
                }

                Log.d("receiver", "Got message: " + message);
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
    }



    public void setCurrentItem(int index){
        mainPager.setCurrentItem(index);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "release");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        try{
            LibraryDayWorkout.get().saveDayWorkouts();
        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("message", "release");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        mainPager.setCurrentItem(1);
    }

    private long getCurrentDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(day < 10){
            day = Integer.parseInt("0"+day);
        }
        int month = calendar.get(Calendar.MONTH);
        if(month < 10){
            month = Integer.parseInt("0"+month);
        }
        int year = calendar.get(Calendar.YEAR);

        return Integer.parseInt(String.valueOf(year) + String.valueOf(month) + String.valueOf(day));
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

}
