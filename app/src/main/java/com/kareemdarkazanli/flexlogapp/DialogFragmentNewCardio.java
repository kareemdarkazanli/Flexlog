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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * Created by kareem_darkazanli on 12/28/15.
 */
public class DialogFragmentNewCardio extends DialogFragment {

    public static final String EXTRA_DISTANCE = "com.kareemdarkazanli.android.snapfit.distance";
    public static final String EXTRA_TIME = "com.kareemdarkazanli.android.snapfit.time";
    public static final String EXTRA_NAME = "com.kareemdarkazanli.android.snapfit.name";
    public static final String EXTRA_NOTE = "com.kareemdarkazanli.android.snapfit.note";
    public static final String EXTRA_UNIT = "com.kareemdarkazanli.android.snapfit.unit";


    private ImageButton addNoteButton;
    private Button distanceButton;


    private EditText distanceEditText;
    private EditText hrEditText;
    private EditText minEditText;
    private EditText secEditText;
    private EditText noteEditText;


    private double distance;
    private int hr;
    private int min;
    private int sec;
    private long time;
    private String note;
    private String unit;

    private boolean noteIsVisible;

    public static DialogFragmentNewCardio newInstance(String name, double distance, long time)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DISTANCE, distance);
        args.putSerializable(EXTRA_TIME, time);
        args.putSerializable(EXTRA_NAME, name);
        DialogFragmentNewCardio fragment = new DialogFragmentNewCardio();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DISTANCE, distance);
        i.putExtra(EXTRA_TIME, time);
        i.putExtra(EXTRA_NOTE, note);
        i.putExtra(EXTRA_UNIT, unit);
        closeKeyboard(getActivity(), noteEditText.getWindowToken());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_cardio, null);
        String e = getArguments().getString(EXTRA_NAME);

        unit = "mi";
        note = "";

        hr = 0;
        min = 0;
        sec = 0;

        noteIsVisible = false;
        distanceButton = (Button)v.findViewById(R.id.distanceButton);
        distanceEditText = (EditText)v.findViewById(R.id.distanceEditText);
        hrEditText = (EditText)v.findViewById(R.id.hrEditText);
        minEditText = (EditText)v.findViewById(R.id.minEditText);
        secEditText = (EditText)v.findViewById(R.id.secEditText);
        noteEditText = (EditText)v.findViewById(R.id.cardioNoteEditText);
        addNoteButton = (ImageButton)v.findViewById(R.id.addCommentButton);

        noteEditText.setVisibility(View.GONE);

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note = noteEditText.getText().toString();
                if(noteEditText.getText().toString().length() > 0){
                    addNoteButton.setImageResource(R.drawable.topic_filled);
                }
                else{
                    addNoteButton.setImageResource(R.drawable.speechbubblegray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(distanceButton.getText().toString().equals("mi")){
                    distanceButton.setText("km");
                    unit = "km";
                }
                else {
                    distanceButton.setText("mi");
                    unit = "mi";
                }
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!noteIsVisible){
                    noteIsVisible = true;
                    noteEditText.setVisibility(View.VISIBLE);
                }else{
                    noteIsVisible = false;
                    noteEditText.setVisibility(View.GONE);
                }
            }
        });


        //getArguments().putSerializable(EXTRA_WEIGHT, weight);
        distanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (distanceEditText.getText().toString().trim().length() == 0) {
                    distance = 0;
                }
                else if(distanceEditText.getText().toString().trim().equals(".")){
                    //distanceEditText.setText("0.");
                    distance = 0.0;

                }
                else {
                    distance = Double.parseDouble(distanceEditText.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hrEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (hrEditText.getText().toString().trim().length() == 0) {
                    hr = 0;
                } else {
                    hr = Integer.parseInt(hrEditText.getText().toString());
                }

                time = hr * 60 * 60 + min * 60 + sec;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        minEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (minEditText.getText().toString().trim().length() == 0) {
                    min = 0;
                } else {
                    min = Integer.parseInt(minEditText.getText().toString());
                }

                time = hr * 60 * 60 + min * 60 + sec;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        secEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (secEditText.getText().toString().trim().length() == 0) {
                    sec = 0;
                } else {
                    sec = Integer.parseInt(secEditText.getText().toString());
                }

                time = hr * 60 * 60 + min * 60 + sec;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(e).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,int which){
                        sendResult(Activity.RESULT_OK);
                    }
                }

        ).

                setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
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
