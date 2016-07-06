package com.kareemdarkazanli.flexlogapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FragmentDay extends Fragment {
    private ImageButton addExerciseButton;
    private static final String DIALOG_EXERCISE = "exercise";
    private static final String DIALOG_HISTORY = "history";

    private static final int REQUEST_EXERCISE = 0;
    private static final int UPDATE_EXERCISE = 1;
    private static final int ADD_EXERCISE = 2;
    private static final int UPDATE_A_EXERCISE = 3;
    private UserExercise mUserExercise;
    private ListView mUserExercisesListView;
    private DayWorkout mUserExercises;
    private LibraryExercise mExercises;
    private Exercise mExercise;

    private ArrayAdapter<UserExercise> userExercisesAdapter;
    private ArrayAdapter<Exercise> exerciseAdapter;
    private ArrayAdapter<Exercise> searchAdapter;


    private LibraryDayWorkout userExercisesLibrary;
    private ImageButton searchImageButton;
    private ImageButton homeImageButton;
    //private ImageButton clearImageButton;
    private ImageButton historyImageButton;
    private ImageButton cameraImageButton;
    private EditText searchEditText;
    private EditText statusEditText;
    private boolean searchOnScreen;
    public boolean limitSearchOnScreen;
    private int dayOfTheWeek;
    private int dayOfTheMonth;
    private int month;
    private int year;
    private long currentDate;
    private DialogFragment dialog;
    ArrayList<Exercise> searchResults;
    private TextView dayStatusTextView;
    public static final String EXTRA_USER_EXERCISE_ID = "com.kareemdarkazanli.android.flexlog.exerciseid";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_EXERCISE){
            if(resultCode == Activity.RESULT_OK) {
                if(mExercise.getType().equals("Weight and Reps")){

                    double weight = (Double) data.getSerializableExtra(DialogFragmentNewWeight.EXTRA_WEIGHT);
                    int reps = (Integer) data.getSerializableExtra(DialogFragmentNewWeight.EXTRA_REPS);
                    String note = (String) data.getSerializableExtra(DialogFragmentNewWeight.EXTRA_NOTE);
                    String unit = (String) data.getSerializableExtra(DialogFragmentNewWeight.EXTRA_UNIT);

                    mUserExercise = new UserWeightExercise(mExercise.getExerciseName(), mExercise.getMuscleGroup(), mExercise.getType(), note, weight, reps, unit);
                    mUserExercise.setDate(getCurrentDate());
                    try{
                        mUserExercises.addExercise(mUserExercise);
                        int count = 0;
                        boolean added = false;
                        for(int i = 0; i < LibraryAllUserExercises.get().getAllUserExercises().size(); i++){
                            if(LibraryAllUserExercises.get().getAllUserExercises().get(i).getDate() == mUserExercise.getDate()){
                                //count ++;
                                LibraryAllUserExercises.get().getAllUserExercises().add(i, mUserExercise);
                                Log.e("FragmentDay", "Exercise Added");
                                added = true;
                                break;
                            }

                        }
                        if(!added){
                            LibraryAllUserExercises.get().getAllUserExercises().add(0, mUserExercise);
                        }
                    }catch (Exception e){

                    }


                }
                else{
                    double distance = (Double) data.getSerializableExtra(DialogFragmentNewCardio.EXTRA_DISTANCE);
                    long time = (Long) data.getSerializableExtra(DialogFragmentNewCardio.EXTRA_TIME);
                    String note = (String) data.getSerializableExtra(DialogFragmentNewCardio.EXTRA_NOTE);
                    String unit = (String) data.getSerializableExtra(DialogFragmentNewCardio.EXTRA_UNIT);

                    mUserExercise = new UserCardioExercise(mExercise.getExerciseName(), mExercise.getMuscleGroup(), mExercise.getType(), note, distance, time, unit);
                    mUserExercise.setDate(getCurrentDate());

                    try{
                        mUserExercises.addExercise(mUserExercise);
                        int count = 0;
                        boolean added = false;
                        for(int i = 0; i < LibraryAllUserExercises.get().getAllUserExercises().size(); i++){
                            if(LibraryAllUserExercises.get().getAllUserExercises().get(i).getDate() == mUserExercise.getDate()){
                                //count ++;
                                LibraryAllUserExercises.get().getAllUserExercises().add(i, mUserExercise);
                                Log.e("FragmentDay", "Exercise Added");
                                added = true;
                                break;
                            }

                        }

                        if(!added){
                            LibraryAllUserExercises.get().getAllUserExercises().add(0, mUserExercise);
                        }

                        //LibraryAllUserExercises.get().getAllUserExercises().add(count, mUserExercise);

                    }catch (Exception e){

                    }


                }
                if(mUserExercises.getExercises().size() > 0){
                    mUserExercisesListView.setVisibility(View.VISIBLE);

                    dayStatusTextView.setVisibility(View.GONE);
                }
                else{
                    mUserExercisesListView.setVisibility(View.GONE);
                    dayStatusTextView.setVisibility(View.VISIBLE);
                    dayStatusTextView.setText("Your exercise list is empty. Click + to add an exercise.");

                }
                mUserExercisesListView.setAdapter(userExercisesAdapter);
                userExercisesAdapter.notifyDataSetChanged();
            }

        }
        if(requestCode == UPDATE_EXERCISE)
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

            }else if (resultCode == Activity.RESULT_CANCELED){
                try{
                    mUserExercises.removeExercise(mUserExercise);
                    LibraryAllUserExercises.get().getAllUserExercises().remove(mUserExercise);

                }catch (Exception e){

                }


            }
            if(mUserExercises.getExercises().size() > 0){
                mUserExercisesListView.setVisibility(View.VISIBLE);

                dayStatusTextView.setVisibility(View.GONE);
            }
            else{
                mUserExercisesListView.setVisibility(View.GONE);
                dayStatusTextView.setVisibility(View.VISIBLE);
                dayStatusTextView.setText("");

            }
            mUserExercisesListView.setAdapter(userExercisesAdapter);
            userExercisesAdapter.notifyDataSetChanged();
        }
        if(requestCode == ADD_EXERCISE){
            if(resultCode == Activity.RESULT_OK)
            {
                boolean addExercise = true;
                mExercise = new Exercise((String)data.getSerializableExtra(DialogFragmentAddExercise.EXTRA_NAME),
                        (String)data.getSerializableExtra(DialogFragmentAddExercise.EXTRA_CATEGORY),
                        (String)data.getSerializableExtra(DialogFragmentAddExercise.EXTRA_TYPE));
                for(Exercise e: LibraryExercise.get().getExercises()){
                    if(e.getExerciseName().equals(mExercise.getExerciseName().trim())){
                        addExercise = false;
                    }
                }

                    if(mExercise.getExerciseName().trim().length() > 0 && addExercise){

                        mExercises.addExercise(mExercise);
                        Intent intent = new Intent("custom-event-name");
                        intent.putExtra("message", "update");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                        setUserExerciseViewLayout();

                        statusEditText.setText(mExercise.toString());

                        if(mExercise.getType().equals("Weight and Reps")){
                            addExerciseButton.setImageResource(R.drawable.dumbbelladd);
                        }else{
                            addExerciseButton.setImageResource(R.drawable.heartadd);
                        }
                        addExerciseButton.setVisibility(View.VISIBLE);
                        searchOnScreen = true;

                        if(mUserExercises.getExercises().size() > 0 || LibraryDayWorkout.get().getUserExercises().size() > 1){
                            FragmentManager fm = getActivity().getSupportFragmentManager();

                            if(mExercise.getType().equals("Weight and Reps")){
                                dialog = new DialogFragmentNewWeight().newInstance(mExercise.toString(), 0.0, 0);

                            }else{
                                dialog= new DialogFragmentNewCardio().newInstance(mExercise.toString(), 0.0, 0);

                            }
                            dialog.setTargetFragment(FragmentDay.this, REQUEST_EXERCISE);
                            dialog.show(fm, DIALOG_EXERCISE);
                        }

                        if(mUserExercises.getExercises().size() > 0){
                            dayStatusTextView.setVisibility(View.GONE);
                            mUserExercisesListView.setVisibility(View.VISIBLE);

                        }
                        else{
                            mUserExercisesListView.setVisibility(View.GONE);
                            dayStatusTextView.setVisibility(View.VISIBLE);
                            dayStatusTextView.setText("");
                        }
                        mUserExercisesListView.setAdapter(userExercisesAdapter);
                        userExercisesAdapter.notifyDataSetChanged();
                        Intent intent1 = new Intent("custom-event-name");
                        // You can also include some extra data.
                        intent1.putExtra("message", "release");
                        //intent.putExtra("message", "release");
                        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent1);
                    }
                    else if(!addExercise){
                        Toast.makeText(getActivity(), mExercise.getExerciseName() + " already exists!",
                                Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getActivity(), "Please enter a name for your exercise!",
                                Toast.LENGTH_LONG).show();
                    }




            }


        }

        if(requestCode == UPDATE_A_EXERCISE){
            if(resultCode == Activity.RESULT_OK)
            {
                Exercise exercise = mExercises.getExercise((String)data.getSerializableExtra(DialogFragmentUpdateExercise.EXTRA_NAME));
                exercise.setMuscleGroup((String)data.getSerializableExtra(DialogFragmentUpdateExercise.EXTRA_CATEGORY));
                exercise.setType((String) data.getSerializableExtra(DialogFragmentUpdateExercise.EXTRA_TYPE));
                if(mUserExercisesListView.getAdapter().equals(searchAdapter)){
                    for(int i = 0; i< searchResults.size(); i++){
                        if(searchResults.get(i).getExerciseName().equals(exercise.getExerciseName())){
                            searchResults.get(i).setMuscleGroup(exercise.getMuscleGroup());
                            searchResults.get(i).setType(exercise.getType());
                        }
                    }
                    searchAdapter.notifyDataSetChanged();
                }
                else if(mUserExercisesListView.getAdapter().equals(exerciseAdapter)){
                    exerciseAdapter.notifyDataSetChanged();
                }

                Intent intent = new Intent("custom-event-name");
                intent.putExtra("message", "updateHistory");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

            }
            else if(resultCode == Activity.RESULT_CANCELED){
                Exercise exercise = mExercises.getExercise((String)data.getSerializableExtra(DialogFragmentUpdateExercise.EXTRA_NAME));
                if(mUserExercisesListView.getAdapter().equals(searchAdapter)){
                    mExercises.getExercises().remove(exercise);
                    for(int i = 0; i< searchResults.size(); i++){
                        if(searchResults.get(i).getExerciseName().equals(exercise.getExerciseName())){
                            searchResults.remove(searchResults.get(i));
                        }
                    }
                    if(searchResults.size() > 0){
                        dayStatusTextView.setVisibility(View.GONE);
                        mUserExercisesListView.setVisibility(View.VISIBLE);
                        mUserExercisesListView.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                    }
                    else{
                        mUserExercisesListView.setVisibility(View.GONE);
                        dayStatusTextView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setText("No exercises found. Add a NEW exercise.");
                    }
                }
                else if(mUserExercisesListView.getAdapter().equals(exerciseAdapter)) {
                    mExercises.getExercises().remove(exercise);
                    if(mExercises.getExercises().size() > 0){
                        dayStatusTextView.setVisibility(View.GONE);
                        mUserExercisesListView.setVisibility(View.VISIBLE);

                    }
                    else{
                        mUserExercisesListView.setVisibility(View.GONE);
                        dayStatusTextView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setText("No exercises found. Add a NEW exercise.");
                    }
                    mUserExercisesListView.setAdapter(exerciseAdapter);
                    exerciseAdapter.notifyDataSetChanged();
                }
                try {

                } catch (Exception e) {

                }
            }

        }

        try{
            LibraryDayWorkout.get().saveDayWorkouts();
            LibraryExercise.get().saveExercises();
            LibraryPhoto.get().savePhotos();
            Log.e("Saved:", "Saved successfully");
        }catch(Exception e){

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        GregorianCalendar today = new GregorianCalendar();


        if(today.get(Calendar.DAY_OF_WEEK) != dayOfTheWeek || dayOfTheMonth != today.get(Calendar.DAY_OF_MONTH) || month != today.get(Calendar.MONTH) || year != today.get(Calendar.YEAR)){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else{
            setUserExerciseViewLayout();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        setUserExerciseViewLayout();

    }

    @Override
    public void onStop() {
        super.onStop();
        setUserExerciseViewLayout();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_day_fragment, container, false);

        userExercisesLibrary = LibraryDayWorkout.get();
        userExercisesLibrary.getUserExercises().size();

        for(int i = 0; i < userExercisesLibrary.getUserExercises().size(); i++){
            if(userExercisesLibrary.getUserExercises().get(i).getExercises().size() == 0){
                userExercisesLibrary.getUserExercises().remove(i);
            }
        }


        dayStatusTextView = (TextView)v.findViewById(R.id.dayMessage);
        long userExerciseId = getActivity().getIntent().getLongExtra(EXTRA_USER_EXERCISE_ID, 0);
        if(userExerciseId == 0){
            if(userExercisesLibrary.hasUserExercises(getCurrentDate())){
                mUserExercises = userExercisesLibrary.getUserExercises(getCurrentDate());
                Log.e("myApp", "user has exercise");
                Log.e("myApp", "E: "+ String.valueOf(mUserExercises.getExercises().size()));

            }
            else{
                mUserExercises = new DayWorkout();
                userExercisesLibrary.addUserExercises(mUserExercises);
            }
        }
        else{
            mUserExercises = userExercisesLibrary.getUserExercises(userExerciseId);
        }

        try{
            mExercises = LibraryExercise.get(getActivity());
        }catch (Exception e){

        }
        mExercises.getExercises();
        mExercise = new Exercise();


        searchResults = new ArrayList<Exercise>();

        addExerciseButton = (ImageButton) v.findViewById(R.id.addExerciseButton);
        searchEditText = (EditText) v.findViewById(R.id.searchEditText);
        searchImageButton = (ImageButton) v.findViewById(R.id.searchImageButton);
        cameraImageButton = (ImageButton)v.findViewById(R.id.cameraImageButton);
        historyImageButton = (ImageButton)v.findViewById(R.id.historyImageButton);
        statusEditText = (EditText)v.findViewById(R.id.statusEditText);


        addExerciseButton.setVisibility(View.GONE);
        searchOnScreen = true;


        GregorianCalendar today = new GregorianCalendar();
        dayOfTheWeek = today.get(Calendar.DAY_OF_WEEK);
        dayOfTheMonth = today.get(Calendar.DAY_OF_MONTH);
        month = today.get(Calendar.MONTH);
        year = today.get(Calendar.YEAR);

        mUserExercisesListView = (ListView)v.findViewById(R.id.userExercisesListView);
        userExercisesAdapter = new UserExercisesAdapter(mUserExercises.getExercises());
        exerciseAdapter = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_1, mExercises.getExercises());

        searchAdapter = new ArrayAdapter<Exercise>(getActivity(), android.R.layout.simple_list_item_1, searchResults);

        setUserExerciseViewLayout();
        statusEditText.setHint(mUserExercises.date);
        statusEditText.setEnabled(false);


        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                if (mExercise.getType().equals("Weight and Reps")) {
                    double weight = 0.0;
                    int reps = 0;
                    String unit = "lb";
                    for (int i = mUserExercises.getExercises().size() - 1; i >= 0; i--) {
                        if (mUserExercises.getExercises().get(i).getName().equals(mExercise.getExerciseName())) {
                            weight = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getWeight();
                            reps = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getReps();
                            unit = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getUnit();
                        }
                    }
                    dialog = new DialogFragmentNewWeight().newInstance(mExercise.toString(), weight, reps, unit);
                } else {
                    dialog = new DialogFragmentNewCardio().newInstance(mExercise.toString(), 0.0, 0);
                }

                dialog.setTargetFragment(FragmentDay.this, REQUEST_EXERCISE);
                dialog.show(fm, DIALOG_EXERCISE);
            }
        });

        mUserExercisesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mUserExercisesListView.getAdapter().equals(userExercisesAdapter)) {
                    if(!mUserExercises.getExercise(position).getName().equals("")) {
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        dialog = new DialogFragmentHistoryListView().newInstance(mUserExercises.getExercise(position).getName());
                        dialog.show(fm, DIALOG_HISTORY);
                    }
                } else if (mUserExercisesListView.getAdapter().equals(exerciseAdapter)) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    dialog = new DialogFragmentUpdateExercise().newInstance(mExercises.getExercise(position).getExerciseName(), mExercises.getExercise(position).getMuscleGroup(), mExercises.getExercise(position).getType());
                    dialog.setTargetFragment(FragmentDay.this, UPDATE_A_EXERCISE);
                    dialog.show(fm, DIALOG_HISTORY);
                } else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    dialog = new DialogFragmentUpdateExercise().newInstance(searchResults.get(position).getExerciseName(), searchResults.get(position).getMuscleGroup(), searchResults.get(position).getType());
                    dialog.setTargetFragment(FragmentDay.this, UPDATE_A_EXERCISE);
                    dialog.show(fm, DIALOG_HISTORY);
                }
                return true;
            }
        });

        mUserExercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mUserExercisesListView.getAdapter().equals(exerciseAdapter)) {
                    if(mExercise.getExerciseName().equals(((Exercise) mUserExercisesListView.getItemAtPosition(position)).getExerciseName())){
                        Toast.makeText(getActivity(), mExercise.getExerciseName() + " is already out!",
                                Toast.LENGTH_LONG).show();
                    }
                    mExercise = (Exercise) mUserExercisesListView.getItemAtPosition(position);

                    setUserExerciseViewLayout();

                    if (mExercise.getType().equals("Weight and Reps")) {
                        addExerciseButton.setImageResource(R.drawable.dumbbelladd);
                    } else if (mExercise.getType().equals("Distance and Time")) {
                        if (!mExercise.getExerciseName().equals("")) {
                            addExerciseButton.setImageResource(R.drawable.heartadd);
                        }
                    }

                    addExerciseButton.setVisibility(View.VISIBLE);
                    searchOnScreen = true;
                    closeKeyboard(getActivity(), searchEditText.getWindowToken());

                    if(mUserExercises.getExercises().size() > 0 || LibraryDayWorkout.get().getUserExercises().size() > 1){
                        FragmentManager fm = getActivity().getSupportFragmentManager();

                    if (mExercise.getType().equals("Weight and Reps")) {
                        double weight = 0.0;
                        int reps = 0;
                        for (int i = mUserExercises.getExercises().size() - 1; i >= 0; i--) {
                            if (mUserExercises.getExercises().get(i).getName().equals(mExercise.getExerciseName())) {
                                weight = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getWeight();
                                reps = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getReps();
                            }
                        }
                        dialog = new DialogFragmentNewWeight().newInstance(mExercise.toString(), weight, reps);
                        dialog.setTargetFragment(FragmentDay.this, REQUEST_EXERCISE);
                        dialog.show(fm, DIALOG_EXERCISE);
                    } else {
                        dialog = new DialogFragmentNewCardio().newInstance(mExercise.toString(), 0.0, 0);
                        dialog.setTargetFragment(FragmentDay.this, REQUEST_EXERCISE);
                        dialog.show(fm, DIALOG_EXERCISE);

                    }
                    }
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                } else if (mUserExercisesListView.getAdapter().equals(userExercisesAdapter)) {
                    mUserExercise = mUserExercises.getExercise(position);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    if (mUserExercise.getType().equals("Weight and Reps")) {
                        dialog = new DialogFragmentUpdateWeight().newInstance(mUserExercise.getName(), mUserExercise.getType(), mUserExercise.getCategory(), mUserExercise.getNote(), ((UserWeightExercise) mUserExercise).getWeight(), ((UserWeightExercise) mUserExercise).getReps(), (mUserExercise).getUnit(), true);
                        dialog.setTargetFragment(FragmentDay.this, UPDATE_EXERCISE);
                        dialog.show(fm, DIALOG_EXERCISE);
                    } else {
                        if (!mUserExercise.getName().equals("")) {
                            Log.e("note:", mUserExercise.getUnit());
                            dialog = new DialogFragmentUpdateCardio().newInstance(mUserExercise.getName(), mUserExercise.getType(), mUserExercise.getCategory(), mUserExercise.getNote(), ((UserCardioExercise) mUserExercise).getDistance(), ((UserCardioExercise) mUserExercise).getTime(), (mUserExercise).getUnit(), true);
                            dialog.setTargetFragment(FragmentDay.this, UPDATE_EXERCISE);
                            dialog.show(fm, DIALOG_EXERCISE);
                        }

                    }

                } else if (mUserExercisesListView.getAdapter().equals(searchAdapter)) {
                    if(mExercise.getExerciseName().equals(((Exercise) mUserExercisesListView.getItemAtPosition(position)).getExerciseName())){
                        Toast.makeText(getActivity(), mExercise.getExerciseName() + " is already out!",
                                Toast.LENGTH_LONG).show();
                    }
                    mExercise = (Exercise) mUserExercisesListView.getItemAtPosition(position);

                    setUserExerciseViewLayout();
                    if (mExercise.getType().equals("Weight and Reps")) {
                        addExerciseButton.setImageResource(R.drawable.dumbbelladd);
                    } else {
                        addExerciseButton.setImageResource(R.drawable.heartadd);
                    }
                    addExerciseButton.setVisibility(View.VISIBLE);
                    searchOnScreen = true;
                    closeKeyboard(getActivity(), searchEditText.getWindowToken());
                    if(mUserExercises.getExercises().size() > 0 || LibraryDayWorkout.get().getUserExercises().size() > 1){
                        FragmentManager fm = getActivity().getSupportFragmentManager();

                    if (mExercise.getType().equals("Weight and Reps")) {
                        double weight = 0.0;
                        int reps = 0;
                        for (int i = mUserExercises.getExercises().size() - 1; i >= 0; i--) {
                            if (mUserExercises.getExercises().get(i).getName().equals(mExercise.getExerciseName())) {
                                weight = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getWeight();
                                reps = ((UserWeightExercise) (mUserExercises.getExercises().get(i))).getReps();
                            }
                        }
                        dialog = new DialogFragmentNewWeight().newInstance(mExercise.toString(), weight, reps);

                    } else {
                        dialog = new DialogFragmentNewCardio().newInstance(mExercise.toString(), 0.0, 0);

                    }
                    dialog.setTargetFragment(FragmentDay.this, REQUEST_EXERCISE);
                    dialog.show(fm, DIALOG_EXERCISE);
                    }

                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                }
                if (statusEditText.getText().toString().trim().length() > 0) {
                    if (mExercise.getType().equals("Weight and Reps")) {
                        addExerciseButton.setImageResource(R.drawable.dumbbelladd);
                        dayStatusTextView.setText("");
                    } else {
                        addExerciseButton.setImageResource(R.drawable.heartadd);
                        dayStatusTextView.setText("");
                    }
                    addExerciseButton.setVisibility(View.VISIBLE);
                } else {
                    addExerciseButton.setVisibility(View.GONE);
                }
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchEditText.getText().toString().trim().length() > 0) {
                    searchImageButton.setVisibility(View.VISIBLE);
                    searchOnScreen = false;
                    if (!mUserExercisesListView.getAdapter().equals(searchAdapter)) {
                        mUserExercisesListView.setAdapter(searchAdapter);
                        Log.e("Exercise", "Switch");
                    }

                    ArrayList<Exercise> allExercises = mExercises.getExercises();
                    searchResults.clear();
                    for (int i = 0; i < allExercises.size(); i++) {
                        if (allExercises.get(i).getExerciseName().toLowerCase().contains(searchEditText.getText().toString().toLowerCase().trim())) {
                            searchResults.add(new Exercise(allExercises.get(i).getExerciseName(), allExercises.get(i).getMuscleGroup(), allExercises.get(i).getType()));
                            Log.e("Exercise", allExercises.get(i).toString());
                        }
                    }
                    if(searchResults.size() > 0){
                        dayStatusTextView.setVisibility(View.GONE);
                        mUserExercisesListView.setVisibility(View.VISIBLE);
                    }
                    else{
                        mUserExercisesListView.setVisibility(View.GONE);
                        dayStatusTextView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setText("No exercises found. Add a NEW exercise.");
                    }
                    mUserExercisesListView.setAdapter(searchAdapter);
                    searchAdapter.notifyDataSetChanged();

                } else {
                    searchImageButton.setVisibility(View.GONE);
                    if(mExercises.getExercises().size() > 0){
                        dayStatusTextView.setVisibility(View.GONE);
                        mUserExercisesListView.setVisibility(View.VISIBLE);
                    }
                    else{
                        mUserExercisesListView.setVisibility(View.GONE);
                        dayStatusTextView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setText("No exercises found. Add a NEW exercise.");
                    }

                    mUserExercisesListView.setAdapter(exerciseAdapter);
                    exerciseAdapter.notifyDataSetChanged();
                }

                userExercisesAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchOnScreen) {
                    Intent intent = new Intent("custom-event-name");
                    intent.putExtra("message", "camera");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                } else {

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DialogFragmentAddExercise dialog = new DialogFragmentAddExercise();
                    dialog.setTargetFragment(FragmentDay.this, ADD_EXERCISE);
                    dialog.show(fm, DIALOG_EXERCISE);
                }
            }
        });


        historyImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchOnScreen) {
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "history");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                } else {
                    mExercise = new Exercise();
                    setUserExerciseViewLayout();
                    searchOnScreen = true;

                    closeKeyboard(getActivity(), searchEditText.getWindowToken());
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                }
            }
        });

        searchImageButton = (ImageButton) v.findViewById(R.id.searchImageButton);
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchOnScreen) {
                    statusEditText.setVisibility(View.GONE);
                    searchEditText.setVisibility(View.VISIBLE);
                    cameraImageButton.setImageResource(R.drawable.newimagebuttonv2);
                    searchImageButton.setImageResource(R.drawable.delete_sign);
                    searchImageButton.setVisibility(View.GONE);

                    searchEditText.setHint("Search for an exercise");
                    searchEditText.setText("");
                    searchEditText.setHintTextColor(Color.WHITE);
                    historyImageButton.setImageResource(R.drawable.leftv2);
                    mUserExercisesListView.setAdapter(exerciseAdapter);
                    exerciseAdapter.notifyDataSetChanged();
                    if(mExercises.getExercises().size() > 0){
                        mUserExercisesListView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setVisibility(View.GONE);
                    }
                    else{
                        mUserExercisesListView.setVisibility(View.GONE);
                        dayStatusTextView.setVisibility(View.VISIBLE);
                        dayStatusTextView.setText("No exercises found. Add a NEW exercise.");

                    }


                    addExerciseButton.setVisibility(View.GONE);
                    try{
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }catch (Exception e){

                    }
                    searchOnScreen = false;
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "freeze");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                } else {
                    searchEditText.setText("");
                }

            }
        });

        setHasOptionsMenu(false);


        return v;
    }


    private void setUserExerciseViewLayout(){
        historyImageButton.setVisibility(View.VISIBLE);
        historyImageButton.setImageResource(R.drawable.listhistoryv2);
        searchEditText.setVisibility(View.GONE);
        statusEditText.setVisibility(View.VISIBLE);
        statusEditText.setText(mExercise.toString());
        searchImageButton.setVisibility(View.VISIBLE);
        searchImageButton.setImageResource(R.drawable.plus);
        cameraImageButton.setVisibility(View.VISIBLE);
        cameraImageButton.setImageResource(R.drawable.camerav2);
        mUserExercisesListView.setAdapter(userExercisesAdapter);
        userExercisesAdapter.notifyDataSetChanged();

        if(mUserExercises.getExercises().size() > 0){
            dayStatusTextView.setVisibility(View.GONE);
            mUserExercisesListView.setVisibility(View.VISIBLE);
        }
        else{
            mUserExercisesListView.setVisibility(View.GONE);
            dayStatusTextView.setVisibility(View.VISIBLE);
            dayStatusTextView.setText("Click + to add an exercise.");
        }
        searchOnScreen = true;
    }

    private void setSearchExerciseViewLayout(){
        historyImageButton.setVisibility(View.VISIBLE);
        historyImageButton.setImageResource(R.drawable.leftv2);
        searchEditText.setVisibility(View.VISIBLE);
        statusEditText.setVisibility(View.GONE);
        searchImageButton.setVisibility(View.GONE);
        cameraImageButton.setVisibility(View.VISIBLE);
        cameraImageButton.setImageResource(R.drawable.newimagebuttonv2);
        mUserExercisesListView.setAdapter(exerciseAdapter);
        exerciseAdapter.notifyDataSetChanged();
        searchOnScreen = false;

    }



    private String dateFormatOne(int dayOfWeek, int dayOfMonth, int month){

        String date = "";
        if(dayOfWeek == 1){
            date += "Sun, ";
        }
        else if(dayOfWeek == 2){
            date += "Mon, ";
        }
        else if(dayOfWeek == 3){
            date += "Tue, ";
        }
        else if(dayOfWeek == 4){
            date += "Wed, ";
        }
        else if(dayOfWeek == 5){
            date += "Thu, ";
        }
        else if(dayOfWeek == 6){
            date += "Fri, ";
        }
        else if(dayOfWeek == 7){
            date += "Sat, ";
        }

        if(month == 0){
            date+= "Jan ";
        }
        else if(month == 1){
            date+= "Feb ";
        }
        else if(month == 2){
            date+= "Mar ";
        }
        else if(month == 3){
            date+= "Apr ";
        }
        else if(month == 4){
            date+= "May ";
        }
        else if(month == 5){
            date+= "June ";
        }
        else if(month == 6){
            date+= "July ";
        }
        else if(month == 7){
            date+= "Aug ";
        }
        else if(month == 8){
            date+= "Sep ";
        }
        else if(month == 9){
            date+= "Oct ";
        }
        else if(month == 10){
            date+= "Nov ";
        }
        else if(month == 11){
            date+= "Dec ";
        }
        date+= dayOfMonth;
        return date;
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    private class UserExercisesAdapter extends ArrayAdapter<UserExercise>{

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
            ImageView noteImageView = (ImageView)convertView.findViewById(R.id.imageViewNote);



            if(u.getName().equals("")){
                nameTextView.setVisibility(View.GONE);
                noteImageView.setVisibility(View.GONE);
                convertView.setBackgroundColor(0x00000000);
            }else{
                nameTextView.setVisibility(View.VISIBLE);
                convertView.setBackgroundColor(Color.WHITE);
                nameTextView.setText(u.getName());
                if(u.getNote().trim().length()>0){
                    noteImageView.setVisibility(View.VISIBLE);
                    noteImageView.setImageResource(R.drawable.topic_filled);
                }
                else{
                    noteImageView.setVisibility(View.INVISIBLE);
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
    private long getCurrentDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayString = String.valueOf(day);
        if(day < 10){
            dayString = "0"+dayString;
        }
        int month = calendar.get(Calendar.MONTH);
        String monthString = String.valueOf(month);
        if(month < 10){
            monthString = "0"+ monthString;
            Log.e("Month string: ", monthString);
        }
        int year = calendar.get(Calendar.YEAR);

        return Long.parseLong(String.valueOf(year) + monthString + dayString);
    }

    private ArrayList<UserExercise> userExercisesWithSpaces(ArrayList<UserExercise> e) {
        ArrayList<UserExercise> userExercises = new ArrayList<UserExercise>();

        if (e.size() > 0) {
            boolean isSame = true;
            userExercises.add(e.get(0));
            String current = e.get(0).getName();

            for (int i = 1; i < e.size(); i++) {
                if (e.get(i).getName().equals(current)) {
                    userExercises.add(e.get(i));
                } else {
                    current = e.get(i).getName();
                    UserExercise space = new UserCardioExercise();
                    userExercises.add(space);
                    userExercises.add(e.get(i));
                }
            }
        }
            return userExercises;

        }



}
