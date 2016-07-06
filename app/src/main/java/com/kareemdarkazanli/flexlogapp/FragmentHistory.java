package com.kareemdarkazanli.flexlogapp;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 12/30/15.
 */
public class FragmentHistory extends Fragment {
    private TextView historyTextView;
    private TextView workoutHistoryTextView;
    private LibraryDayWorkout userExercisesLibrary;
    private ArrayList<DayWorkout> userExercises;
    private ArrayList<UserExercise> userExercise;
    private DayWorkout day;
    private ListView historyListView;
    private BroadcastReceiver mMessageReceiver;
    private AdapterHistory mainAdapter;
    private UserExercisesAdapter userExercisesAdapter;
    private ImageButton historyBackButton;
    private ImageButton backToHistory;
    String currentExercise;
    private static final String DIALOG_EXERCISE = "exercise";
    private static final String DIALOG_HISTORY = "history";
    private static final int EDIT_A_EXERCISE = 4;
    private DialogFragment dialog;
    private UserExercise mUserExercise;



    //public static final String EXTRA_HISTORY = "com.kareemdarkazanli.android.snapfit.history";


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_A_EXERCISE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                if(mUserExercise.getType().equals("Weight and Reps")){
                    ((UserWeightExercise)mUserExercise).setWeight((Double) data.getSerializableExtra(DialogFragmentUpdateWeight.EXTRA_WEIGHT));
                    ((UserWeightExercise)mUserExercise).setReps((Integer) data.getSerializableExtra(DialogFragmentUpdateWeight.EXTRA_REPS));
                    (mUserExercise).setNote((String) data.getSerializableExtra(DialogFragmentUpdateWeight.EXTRA_NOTE));
                    (mUserExercise).setUnit((String) data.getSerializableExtra(DialogFragmentUpdateWeight.EXTRA_UNIT));

                }
                else {
                    ((UserCardioExercise) mUserExercise).setDistance((Double) data.getSerializableExtra(DialogFragmentUpdateCardio.EXTRA_DISTANCE));
                    ((UserCardioExercise) mUserExercise).setTime((Long) data.getSerializableExtra(DialogFragmentUpdateCardio.EXTRA_TIME));
                    (mUserExercise).setNote((String) data.getSerializableExtra(DialogFragmentUpdateCardio.EXTRA_NOTE));
                    (mUserExercise).setUnit((String) data.getSerializableExtra(DialogFragmentUpdateCardio.EXTRA_UNIT));
                }

            }
            userExercisesAdapter.notifyDataSetChanged();

        }    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.history_list, container, false);
        setHasOptionsMenu(false);
        historyBackButton = (ImageButton) v.findViewById(R.id.historyBackButton);
        historyListView = (ListView) v.findViewById(R.id.historyListView);
        backToHistory = (ImageButton) v.findViewById(R.id.backToHistoryImageButton);
        backToHistory.setVisibility(View.GONE);
        backToHistory.setImageResource(R.drawable.flexloglogov5);

        workoutHistoryTextView = (TextView) v.findViewById(R.id.workoutHistoryMessage);
        userExercisesLibrary = LibraryDayWorkout.get();
        userExercises = new ArrayList<DayWorkout>();
       for (int i = 1; i < userExercisesLibrary.getUserExercises().size(); i++){
            userExercises.add(userExercisesLibrary.getUserExercises().get(i));
        }
        //userExercises = userExercisesLibrary.getUserExercises();
        if(userExercises.size() > 0){
            workoutHistoryTextView.setVisibility(View.GONE);
        }
        else{
            workoutHistoryTextView.setVisibility(View.VISIBLE);
        }

        userExercise = new ArrayList<UserExercise>();
        mainAdapter = new AdapterHistory(userExercises, R.id.historyListView, getActivity());
        userExercisesAdapter = new UserExercisesAdapter(userExercise);
        //String e = getArguments().getString(EXTRA_HISTORY);
        //userExercisesLibrary = LibraryDayWorkout.get();
        historyTextView = (TextView) v.findViewById(R.id.historyTextView);
        historyTextView.setText("Workout History");
        historyListView.setAdapter(mainAdapter);
        backToHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(historyListView.getAdapter().equals(mainAdapter)){


                }
                else{
                    backToHistory.setImageResource(R.drawable.flexloglogov5);
                    backToHistory.setVisibility(View.GONE);
                    historyListView.setAdapter(mainAdapter);
                    historyTextView.setText("Workout History");
                    mainAdapter.notifyDataSetChanged();
                }
            }
        });
        historyBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("custom-event-name");
                // You can also include some extra data.
                intent.putExtra("message", "home");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                //DialogFragment dialog = new ImageFragment().newInstance(userExercisesLibrary.getPhoto().getFilename());


                //dialog.setTargetFragment(ExperimentCamera.this, 0);
                // dialog.show(fm, DIALOG_IMAGE);

                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //String path = getActivity().getFileStreamPath(userExercisesLibrary.getPhoto().getFilename()).getAbsolutePath();
                //ImageFragment.newInstance(path).show(fm, "hi");
                /*List<ParseFile> pFileList = (ArrayList<ParseFile>) ParseUser.getCurrentUser().get("photo");
                if (!pFileList.isEmpty()) {
                    ParseFile pFile = pFileList.get(0);
                    try{

                    }catch (Exception e){
                        byte[] bitmapdata = pFile.getData();  // here it throws error
                    }

                    bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                }*/
                //FragmentManager fm = getActivity().getSupportFragmentManager();
//                String path = getActivity().getFileStreamPath(LibraryDayWorkout.get().getPhoto().getFilename()).getAbsolutePath();
                //ImageFragment.newInstance("hi").show(fm,"hi");
            }
        });
        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (historyListView.getAdapter().equals(mainAdapter)){
                    backToHistory.setVisibility(View.VISIBLE);
                    backToHistory.setImageResource(R.drawable.leftarrow);
                    day = ((DayWorkout)historyListView.getItemAtPosition(position));
                    userExercise.clear();
                    for(int i = 0; i < day.getExercises().size(); i++){
                        userExercise.add(day.getExercises().get(i));
                    }
                    //userExercise = day.getExercises();
                    historyTextView.setText(day.getDateWithYear());
                    historyListView.setAdapter(userExercisesAdapter);
                    userExercisesAdapter.notifyDataSetChanged();

                }
            }
        });

        historyListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (historyListView.getAdapter().equals(userExercisesAdapter)){
                    mUserExercise = userExercise.get(position);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (mUserExercise.getType().equals("Weight and Reps")) {
                        dialog = new DialogFragmentUpdateWeight().newInstance(mUserExercise.getName(), mUserExercise.getType(), mUserExercise.getCategory(), mUserExercise.getNote(), ((UserWeightExercise) mUserExercise).getWeight(), ((UserWeightExercise) mUserExercise).getReps(), (mUserExercise).getUnit(), false);
                        dialog.setTargetFragment(FragmentHistory.this, EDIT_A_EXERCISE);
                        dialog.show(fm, DIALOG_EXERCISE);
                    } else {
                        if (!mUserExercise.getName().equals("")) {
                            Log.e("note:", mUserExercise.getUnit());
                            dialog = new DialogFragmentUpdateCardio().newInstance(mUserExercise.getName(), mUserExercise.getType(), mUserExercise.getCategory(), mUserExercise.getNote(), ((UserCardioExercise) mUserExercise).getDistance(), ((UserCardioExercise) mUserExercise).getTime(), (mUserExercise).getUnit(), false);
                            dialog.setTargetFragment(FragmentHistory.this, EDIT_A_EXERCISE);
                            dialog.show(fm, DIALOG_EXERCISE);
                        }

                    }
                }
                return true;
            }
        });
        //userExercisesLibrary = LibraryDayWorkout.get();
        //historyTextView.setText(e  + " History");

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("message");
                if(message.equals("updateHistory")){
                    mainAdapter.notifyDataSetChanged();
                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        return v;
    }

    private class UserExercisesAdapter extends ArrayAdapter<UserExercise> {

        public UserExercisesAdapter(ArrayList<UserExercise> u){
            super(getActivity(), 0, u);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_day, null);
            }

            UserExercise u = getItem(position);


            TextView nameTextView = (TextView) convertView.findViewById(R.id.userExerciseNameTextView);
            TextView repsTextView = (TextView) convertView.findViewById(R.id.userExerciseRepsTextVie);
            TextView weightTextView = (TextView) convertView.findViewById(R.id.userExerciseWeightTextView);
            TextView noteTextView = (TextView) convertView.findViewById(R.id.userExerciseCommentTextView);

            if(u.getName().equals("")){
                nameTextView.setVisibility(View.GONE);
                convertView.setBackgroundColor(0x00000000);
            }else{
                nameTextView.setVisibility(View.VISIBLE);
                convertView.setBackgroundColor(Color.WHITE);
                nameTextView.setText(u.getName());
                if(u.getNote().trim().length() > 0){
                    noteTextView.setTextColor(Color.parseColor("#595959"));
                    noteTextView.setText(u.getNote());
                }
                else{
                    noteTextView.setText("");
                }
            }



            if (u.getType().equals("Weight and Reps")) {
                repsTextView.setVisibility(View.VISIBLE);
                repsTextView.setText(String.valueOf(((UserWeightExercise) u).getReps()) + " reps");

            } else if (u.getName().equals("")) {
                repsTextView.setVisibility(View.GONE);
            } else {
                repsTextView.setVisibility(View.VISIBLE);
                repsTextView.setText(String.valueOf(((UserCardioExercise) u).getDistance()) + " " + u.getUnit());
            }


            if (u.getType().equals("Weight and Reps")) {
                weightTextView.setVisibility(View.VISIBLE);
                weightTextView.setText(String.valueOf(((UserWeightExercise) u).getWeight()) + " " + u.getUnit());
            } else if (u.getName().equals("")) {
                weightTextView.setVisibility(View.GONE);;
            } else {
                long temp = ((UserCardioExercise) u).getTime();
                int hr = (int)(temp/(60 * 60));
                String time = "";
                if(hr == 0){

                }
                else if(hr < 10){
                    time += "0"+String.valueOf(hr) +":";
                }
                else{
                    time += String.valueOf(hr) + ":";
                }
                temp = (int) (temp % (60 * 60));

                int min = (int)(temp/(60));

                if(min == 0 && hr == 0){

                }
                else if(min < 10){
                    time += "0"+String.valueOf(min) + ":";
                }
                else{
                    time += String.valueOf(min) + ":";
                }
                temp = (int) (temp % (60));
                int sec = (int)temp;
                if(hr == 0 && min == 0 && sec == 0){
                    time = "0s";
                }
                else if(hr == 0 && min == 0){
                    time = String.valueOf(sec) + "s";
                }
                else if(sec < 10){
                    time += "0"+String.valueOf(sec);
                }
                else{
                    time += String.valueOf(sec);
                }
                weightTextView.setVisibility(View.VISIBLE);
                weightTextView.setText(time);

            }





            return convertView;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
       // historyTextView.setText(currentExercise + " History");
    }

    /*public static FragmentHistory newInstance(String name)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_HISTORY, name);


        FragmentHistory fragment = new FragmentHistory();
        fragment.setArguments(args);

        return fragment;
    }*/
}
