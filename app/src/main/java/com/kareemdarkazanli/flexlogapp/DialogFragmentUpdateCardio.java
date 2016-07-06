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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * Created by kareem_darkazanli on 12/28/15.
 */
public class DialogFragmentUpdateCardio extends DialogFragment {

    private EditText distanceEditText;
    private EditText hrEditText;
    private EditText minEditText;
    private EditText secEditText;
    private EditText noteEditText;

    private ImageButton addNoteButton;
    private Button distanceButton;


    public static final String EXTRA_DISTANCE = "com.kareemdarkazanli.android.snapfit.distance";
    public static final String EXTRA_TIME = "com.kareemdarkazanli.android.snapfit.time";
    public static final String EXTRA_NAME = "com.kareemdarkazanli.android.snapfit.name";
    public static final String EXTRA_NOTE = "com.kareemdarkazanli.android.snapfit.note";
    public static final String EXTRA_TYPE = "com.kareemdarkazanli.android.snapfit.type";
    public static final String EXTRA_CATEGORY = "com.kareemdarkazanli.android.snapfit.category";
    public static final String EXTRA_UNIT = "com.kareemdarkazanli.android.snapfit.unit";



    private double distance;
    private long time;
    private String note;
    private String unit;
    private int hr;
    private int min;
    private int sec;

    private boolean noteIsVisible;

    public static DialogFragmentUpdateCardio newInstance(String name, String category, String type, String note, double distance, long time, String unit)
    {
        Bundle args = new Bundle();

        args.putSerializable(EXTRA_NOTE, note);
        args.putSerializable(EXTRA_TYPE, type);
        args.putSerializable(EXTRA_CATEGORY, category);
        args.putSerializable(EXTRA_DISTANCE, distance);
        args.putSerializable(EXTRA_TIME, time);
        args.putSerializable(EXTRA_NAME, name);
        args.putSerializable(EXTRA_UNIT, unit);

        DialogFragmentUpdateCardio fragment = new DialogFragmentUpdateCardio();
        fragment.setArguments(args);

        return fragment;
    }

    public static DialogFragmentUpdateCardio newInstance(String name, String category, String type, String note, double distance, long time, String unit, boolean b)
    {
        Bundle args = new Bundle();

        args.putSerializable(EXTRA_NOTE, note);
        args.putSerializable(EXTRA_TYPE, type);
        args.putSerializable(EXTRA_CATEGORY, category);
        args.putSerializable(EXTRA_DISTANCE, distance);
        args.putSerializable(EXTRA_TIME, time);
        args.putSerializable(EXTRA_NAME, name);
        args.putSerializable(EXTRA_UNIT, unit);
        args.putSerializable("boolean", b);

        DialogFragmentUpdateCardio fragment = new DialogFragmentUpdateCardio();
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
        final boolean b = getArguments().getBoolean("boolean");
        note = getArguments().getString(EXTRA_NOTE);
        unit = getArguments().getString(EXTRA_UNIT);




        distanceEditText = (EditText)v.findViewById(R.id.distanceEditText);
        hrEditText = (EditText)v.findViewById(R.id.hrEditText);
        minEditText = (EditText) v.findViewById(R.id.minEditText);
        secEditText = (EditText)v.findViewById(R.id.secEditText);
        distance = getArguments().getDouble(EXTRA_DISTANCE);
        distanceEditText.setText(String.valueOf(distance));
        time = getArguments().getLong(EXTRA_TIME);
        noteEditText = (EditText)v.findViewById(R.id.cardioNoteEditText);
        noteEditText.setText(note);

        addNoteButton = (ImageButton)v.findViewById(R.id.addCommentButton);
        distanceButton = (Button)v.findViewById(R.id.distanceButton);
        distanceButton.setText(unit);

        if(note.trim().length() > 0){
            addNoteButton.setImageResource(R.drawable.topic_filled);
        }

        long temp = time;

        hr = (int)(temp/(60 * 60));
        temp = (int) (temp % (60 * 60));
        min = (int)(temp/(60));
        temp = (int) (temp % (60));
        sec = (int)temp;

        hrEditText.setText(String.valueOf(hr));
        minEditText.setText(String.valueOf(min));
        secEditText.setText(String.valueOf(sec));


        note = getArguments().getString(EXTRA_NOTE);
        unit = getArguments().getString(EXTRA_UNIT);

        noteIsVisible = false;
        noteEditText.setVisibility(View.GONE);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!noteIsVisible) {
                    noteEditText.setVisibility(View.VISIBLE);
                    noteIsVisible = true;
                } else {
                    noteEditText.setVisibility(View.GONE);
                    noteIsVisible = false;

                }
            }
        });

        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distanceButton.getText().toString().equals("mi")) {
                    distanceButton.setText("km");
                    unit = "km";
                } else {
                    distanceButton.setText("mi");
                    unit = "mi";
                }
            }
        });

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
                   // distanceEditText.setText("0.");
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

        if(b){
            return new AlertDialog.Builder(getActivity()).setView(v).setTitle(e).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendResult(Activity.RESULT_OK);
                    getActivity().getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendResult(Activity.RESULT_CANCELED);
                    getActivity().getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }).create();
        }
        else{
            return new AlertDialog.Builder(getActivity()).setView(v).setTitle(e).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendResult(Activity.RESULT_OK);
                    getActivity().getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }).create();
        }

    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }
}
