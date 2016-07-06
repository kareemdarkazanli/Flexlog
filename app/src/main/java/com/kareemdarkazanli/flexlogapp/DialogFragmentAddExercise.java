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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class DialogFragmentAddExercise extends DialogFragment {

    public static final String EXTRA_NAME = "com.kareemdarkazanli.android.snapfit.name";
    public static final String EXTRA_CATEGORY = "com.kareemdarkazanli.android.snapfit.category";
    public static final String EXTRA_TYPE = "com.kareemdarkazanli.android.snapfit.type";

    private Spinner categorySpinner;
    private Spinner typeSpinner;
    private EditText nameEditText;

    private String name = "";
    private String category = "";
    private String type = "";

    private ArrayList<String> categoryArrayList;
    private ArrayList<String> typeArrayList;

    public static DialogFragmentAddExercise newInstance()
    {
        Bundle args = new Bundle();
        DialogFragmentAddExercise fragment = new DialogFragmentAddExercise();
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
        closeKeyboard(getActivity(), nameEditText.getWindowToken());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.activity_exercise_dialog_fragment_add, null);

        categorySpinner = (Spinner) v.findViewById(R.id.spinnerCategory);
        typeSpinner = (Spinner)v.findViewById(R.id.spinnerType);
        nameEditText = (EditText) v.findViewById(R.id.nameEditText);

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

        CatTypeAdapter categoryAdapter = new CatTypeAdapter(categoryArrayList, R.layout.activity_cattype, getActivity());
        CatTypeAdapter typeAdapter = new CatTypeAdapter(typeArrayList, R.layout.activity_cattype, getActivity());

        /*ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categoryArrayList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, typeArrayList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        categorySpinner.setAdapter(categoryAdapter);
        typeSpinner.setAdapter(typeAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categoryArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = typeArrayList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = nameEditText.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("New Exercise").setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,int which){
                        sendResult(Activity.RESULT_OK);
                    }
                }

        ).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_CANCELED);
            }
        }).create();
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}
