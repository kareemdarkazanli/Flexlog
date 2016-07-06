package com.kareemdarkazanli.flexlogapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 1/23/16.
 */
public class DialogFragmentUpdateExercise extends DialogFragment {

    public static final String EXTRA_NAME = "com.kareemdarkazanli.android.snapfit.name";
    public static final String EXTRA_CATEGORY = "com.kareemdarkazanli.android.snapfit.category";
    public static final String EXTRA_TYPE = "com.kareemdarkazanli.android.snapfit.type";

    private Spinner categorySpinner;
    private Spinner typeSpinner;
    private EditText nameEditText;

    private ImageView categoryImageView;
    private ImageView typeImageView;
    private TextView categoryEditText;
    private TextView typeEditText;

    private String name = "";
    private String category = "";
    private String type = "";

    private ArrayList<String> categoryArrayList;
    private ArrayList<String> typeArrayList;

    private TextView nameTextView;

    public static DialogFragmentUpdateExercise newInstance(String exercise, String category, String type)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NAME, exercise);
        args.putSerializable(EXTRA_CATEGORY, category);
        args.putSerializable(EXTRA_TYPE, type);
        DialogFragmentUpdateExercise fragment = new DialogFragmentUpdateExercise();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_NAME, name);
        i.putExtra(EXTRA_CATEGORY, category);
        i.putExtra(EXTRA_TYPE, type);
        closeKeyboard(getActivity(), categoryEditText.getWindowToken());

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.activity_exercise_dialog_fragment_update, null);

        typeEditText = (TextView) v.findViewById(R.id.typeEditText);
        categoryEditText = (TextView) v.findViewById(R.id.categoryEditText);
        categoryImageView = (ImageView)v.findViewById(R.id.categoryImageView);
        typeImageView = (ImageView)v.findViewById(R.id.typeImageView);


        name = getArguments().getString(EXTRA_NAME);
        category = getArguments().getString(EXTRA_CATEGORY);
        type = getArguments().getString(EXTRA_TYPE);
        Log.e("Type: ", type);


        if (category.equals("Abs")){
            categoryImageView.setImageResource(R.drawable.abscolor);
        }
        else if(category.equals("Back")){
            categoryImageView.setImageResource(R.drawable.backcolor);
        }
        else if(category.equals("Biceps")){
            categoryImageView.setImageResource(R.drawable.armscolor);
        }
        else if(category.equals("Chest")){
            categoryImageView.setImageResource(R.drawable.chestcolor);
        }
        else if(category.equals("Legs")){
            categoryImageView.setImageResource(R.drawable.legcolor);
        }
        else if(category.equals("Shoulder")){
            categoryImageView.setImageResource(R.drawable.shoulderscolor);
        }
        else if(category.equals("Triceps")){
            categoryImageView.setImageResource(R.drawable.armscolor);
        }
        else{
            categoryImageView.setImageResource(R.drawable.cardiocolor);
        }

        if(type.equals("Weight and Reps")){
            typeImageView.setImageResource(R.drawable.dumbbellcolor);
        }
        else{
            typeImageView.setImageResource(R.drawable.cardiocolor);
        }

        categoryEditText.setText(category);
        typeEditText.setText(type);

        categoryArrayList = new ArrayList<String>();
        categoryArrayList.add("Abs");
        categoryArrayList.add("Back");
        categoryArrayList.add("Biceps");
        categoryArrayList.add("Chest");
        categoryArrayList.add("Legs");
        categoryArrayList.add("Shoulder");
        categoryArrayList.add("Triceps");
        categoryArrayList.add("Cardio");


        typeArrayList = new ArrayList<String>();
        typeArrayList.add("Weight and Reps");
        typeArrayList.add("Distance and Time");



        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(name).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < LibraryAllUserExercises.get().getAllUserExercises().size(); i++) {
                            if (LibraryAllUserExercises.get().getAllUserExercises().get(i).getName().equals(name)) {
                                LibraryAllUserExercises.get().getAllUserExercises().get(i).setCategory(category);
                            }
                        }
                        sendResult(Activity.RESULT_OK);
                    }
                }

        ).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_CANCELED);
            }
        }).create();
    }


    private int getIndex(ArrayList<String> arrayList, String s){
        int index = 0;
        for (int i = 0; i < arrayList.size(); i++){
            if(arrayList.get(i).equals(s)){
                return i;
            }
        }
        return index;
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}

