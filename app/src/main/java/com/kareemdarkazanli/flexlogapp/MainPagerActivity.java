package com.kareemdarkazanli.flexlogapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;

public class MainPagerActivity extends FragmentPagerAdapter {

    //Map<Integer,Fragment> mPageReferenceMap = new HashMap<Integer,Fragment>();
    ArrayList<Fragment> pageReference = new ArrayList<Fragment>();
    private FragmentDay dayFragment;
    private FragmentHistory fragmentHistory;
    private FragmentCamera cameraFragment;
    private FragmentPhotos fragmentPhotos;
   // private FragmentPhoneCamera fragmentPhoneCamera;



    public MainPagerActivity(FragmentManager fm)
    {
        super(fm);
        dayFragment = new FragmentDay();
        fragmentHistory = new FragmentHistory();
        //.newInstance(LibraryDayWorkout.get().getCurrentExercise().toString());
        cameraFragment = new FragmentCamera();
        fragmentPhotos = new FragmentPhotos();
        //fragmentPhoneCamera = new FragmentPhoneCamera();



    }
    @Override
    public Fragment getItem(int position) {
       /* FragmentDay dayFragment = new FragmentDay();
        FragmentHistory historyFragment = new FragmentHistory()d;
                //.newInstance(LibraryDayWorkout.get().getCurrentExercise().toString());
        FragmentCamera cameraFragment = new FragmentCamera();
        FragmentPhotos fragmentPhotos = new FragmentPhotos();*/

       /* mPageReferenceMap.put(0, historyFragment);
        mPageReferenceMap.put(1, dayFragment);
        mPageReferenceMap.put(2, cameraFragment);
        mPageReferenceMap.put(3, fragmentPhotos);*/


        pageReference.add(fragmentHistory);
        pageReference.add(dayFragment);
        pageReference.add(cameraFragment);
       pageReference.add(fragmentPhotos);


        switch (position) {
            case 0:
                return fragmentHistory; //pageReference.get(0);
            case 1:
                return dayFragment;
            case 2:
               return cameraFragment;
            case 3:
                return fragmentPhotos;


            default:
                break;
        }

        return null;

    }

    @Override
    public int getCount() {
        return 4;
    }

    public void destroyItem(View container, int position, Object object) {
        super.destroyItem(container, position, object);
        pageReference.remove(position);
    }



    public Fragment getFragment(int key) {
        return pageReference.get(key);
    }
}
