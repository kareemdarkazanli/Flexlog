package com.kareemdarkazanli.flexlogapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 1/21/16.
 */
public class DialogFragmentHistoryListView extends DialogFragment {

    private ArrayList<UserExercise> allUserExercises;
    private ListView listView;
    private ArrayAdapter<UserExercise> adapter;


    public static DialogFragmentHistoryListView newInstance(String exercise)
    {
        Bundle args = new Bundle();
        args.putSerializable("exercise", exercise);
        DialogFragmentHistoryListView fragment = new DialogFragmentHistoryListView();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;
        Intent i = new Intent();
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.listview_dialog_fragment, null);
        String e = getArguments().getString("exercise");
        allUserExercises = LibraryAllUserExercises.get().getAllUserExercises();
        Log.e("DialogFragmen", ""+allUserExercises.size());
        ArrayList<UserExercise> specificUserExercises = new ArrayList<UserExercise>();
        for (int i = 0; i < allUserExercises.size(); i++){
            if(allUserExercises.get(i).getName().equals(e)){
                specificUserExercises.add(allUserExercises.get(i));
            }
        }
        ArrayList<UserExercise> printUserExercises = printUserExercises(specificUserExercises);
        listView = (ListView)v.findViewById(R.id.listView);
        adapter = new UserExercisesAdapter(printUserExercises);//new ArrayAdapter<UserExercise>(getActivity(), android.R.layout.simple_list_item_1, printUserExercises);

                //new UserExercisesAdapter(printUserExercises);//
        listView.setAdapter(adapter);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(e + " History").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                }

        ).create();
    }


    private ArrayList<UserExercise> printUserExercises(ArrayList<UserExercise> userExercises){
        ArrayList<UserExercise> newUserExercises = new ArrayList<UserExercise>();
        UserExercise e = new UserCardioExercise();
        int count = 0;

        if(userExercises.size() > 0){
            e.setDate(userExercises.get(0).getDate());
            Log.e("Date:", e.getDate() + "");
            newUserExercises.add(e);
            count++;
        }
        for(int i = 0; i < userExercises.size(); i++){
            if(userExercises.get(i).getDate() != e.getDate()){
                e = new UserCardioExercise();
                e.setDate(userExercises.get(i).getDate());
                newUserExercises.add(e);
                newUserExercises.add(userExercises.get(i));
                count+=2;
            }
            else{
                //count++;
                newUserExercises.add(count ,userExercises.get(i));
                count++;

            }
        }
        /*for(int i = 0; i < userExercises.size(); i++){
            if(userExercises.get(i).getDate() != e.getDate()){
                newUserExercises.add(0, e);
                newUserExercises.add(0, userExercises.get(i));
                e = new UserCardioExercise();
                e.setDate(userExercises.get(i).getDate());
                count = 1;
            }
            else{
                //count++;
                newUserExercises.add(count ,userExercises.get(i));
                count++;

            }
        }

        newUserExercises.add(0,e);*/
        return newUserExercises;
    }


    private class UserExercisesAdapter extends ArrayAdapter<UserExercise>{

        public UserExercisesAdapter(ArrayList<UserExercise> u){
            super(getActivity(), 0, u);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_history_dialog_fragment, null);
            }

            UserExercise u = getItem(position);


            //TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextViewHistory);
            TextView repsTextView = (TextView) convertView.findViewById(R.id.repsTextViewHistory);
            TextView weightTextView = (TextView) convertView.findViewById(R.id.weightTextViewHistory);
            TextView noteTextView = (TextView) convertView.findViewById(R.id.noteTextViewHistory);

            if(u.getName().equals("")) {
                //dateTextView.setVisibility(View.VISIBLE);
                //dateTextView.setText(u.getDate() + "");
                weightTextView.setPaintFlags(weightTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                weightTextView.setVisibility(View.VISIBLE);
                weightTextView.setText(getDate(u.getDate()));
                repsTextView.setVisibility(View.GONE);
                noteTextView.setVisibility(View.GONE);
            }
            else if (u.getType().equals("Weight and Reps")) {
                //dateTextView.setVisibility(View.GONE);
                weightTextView.setPaintFlags(0);
                repsTextView.setVisibility(View.VISIBLE);
                repsTextView.setText(String.valueOf(((UserWeightExercise) u).getReps()) + " reps");
                weightTextView.setVisibility(View.VISIBLE);
                weightTextView.setText(String.valueOf(((UserWeightExercise) u).getWeight()) + " " + u.getUnit());
                if(!u.getNote().equals("")){
                    noteTextView.setVisibility(View.VISIBLE);
                    noteTextView.setText(u.getNote());
                    noteTextView.setTextColor(Color.parseColor("#595959"));
                }
                else{
                    noteTextView.setVisibility(View.GONE);
                }
            } else {
                //dateTextView.setVisibility(View.GONE);
                weightTextView.setPaintFlags(0);

                repsTextView.setVisibility(View.VISIBLE);
                repsTextView.setText(String.valueOf(((UserCardioExercise) u).getDistance()) + " " + u.getUnit());
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
                if(!u.getNote().equals("")){
                    noteTextView.setVisibility(View.VISIBLE);
                    noteTextView.setText(u.getNote());
                    noteTextView.setTextColor(Color.parseColor("#999999"));
                }
                else{
                    noteTextView.setVisibility(View.GONE);
                }
            }


            return convertView;
        }
    }

    private String getDate(long l){
        String date = ""+ (Integer.parseInt(String.valueOf(l).substring(4, 6)) + 1) + "/" + String.valueOf(l).substring(6,8) + "/" + String.valueOf(l).substring(0,4);
        return date;
    }
}
