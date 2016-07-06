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


public class DialogFragmentNewWeight extends DialogFragment {

    public static final String EXTRA_WEIGHT = "com.kareemdarkazanli.android.snapfit.weight";
    public static final String EXTRA_REPS = "com.kareemdarkazanli.android.snapfit.reps";
    public static final String EXTRA_EXERCISE = "com.kareemdarkazanli.android.snapfit.exercise";
    public static final String EXTRA_NOTE = "com.kareemdarkazanli.android.snapfit.note";
    public static final String EXTRA_UNIT = "com.kareemdarkazanli.android.snapfit.unit";

    private EditText weightEditText;
    private EditText repsEditText;

    private ImageButton addRepsButton;
    private ImageButton subRepsButton;
    private ImageButton addWeightButton;
    private ImageButton subWeightButton;
    private Button weightButton;

    private EditText noteEditText;
    private ImageButton addNoteButton;

    private double weight;
    private int reps;
    private String note;
    private String unit;
    private boolean noteIsVisible;

    public static DialogFragmentNewWeight newInstance(String exercise, double weight, int reps)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_WEIGHT, weight);
        args.putSerializable(EXTRA_REPS, reps);
        args.putSerializable(EXTRA_EXERCISE, exercise);
        args.putSerializable(EXTRA_UNIT, "lb");

        DialogFragmentNewWeight fragment = new DialogFragmentNewWeight();
        fragment.setArguments(args);

        return fragment;
    }

    public static DialogFragmentNewWeight newInstance(String exercise, double weight, int reps, String unit)
    {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_WEIGHT, weight);
        args.putSerializable(EXTRA_REPS, reps);
        args.putSerializable(EXTRA_EXERCISE, exercise);
        args.putSerializable(EXTRA_UNIT, unit);

        DialogFragmentNewWeight fragment = new DialogFragmentNewWeight();
        fragment.setArguments(args);

        return fragment;
    }

    private void sendResult(int resultCode){
        if(getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_WEIGHT, weight);
        i.putExtra(EXTRA_REPS, reps);
        i.putExtra(EXTRA_NOTE, note);
        i.putExtra(EXTRA_UNIT, unit);
        closeKeyboard(getActivity(), noteEditText.getWindowToken());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_weight, null);
        unit = "lb";
        note = "";
        String e = getArguments().getString(EXTRA_EXERCISE);
        weight = getArguments().getDouble(EXTRA_WEIGHT);
        reps = getArguments().getInt(EXTRA_REPS);
        unit = getArguments().getString(EXTRA_UNIT);
        ////////set note
        addNoteButton = (ImageButton) v.findViewById(R.id.addCommentButton);
        noteEditText = (EditText) v.findViewById(R.id.noteEditText);
        addRepsButton = (ImageButton) v.findViewById(R.id.addRepsButton);
        subRepsButton = (ImageButton) v.findViewById(R.id.subRepsButton);
        addWeightButton = (ImageButton) v.findViewById(R.id.addWeightButton);
        subWeightButton = (ImageButton) v.findViewById(R.id.subWeightButton);
        weightButton = (Button)v.findViewById(R.id.weightButton);

        noteEditText.setVisibility(View.GONE);
        noteIsVisible = false;


        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note = noteEditText.getText().toString();
                if (noteEditText.getText().toString().length() > 0) {
                    addNoteButton.setImageResource(R.drawable.topic_filled);
                } else {
                    addNoteButton.setImageResource(R.drawable.speechbubblegray);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        weightButton.setText(unit);
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weightButton.getText().toString().equals("lb")){
                    weightButton.setText("kg");
                    unit = "kg";
                }
                else {
                    weightButton.setText("lb");
                    unit = "lb";
                }
            }
        });

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!noteIsVisible){
                    noteIsVisible = true;
                    noteEditText.setVisibility(View.VISIBLE);

                }else
                {
                    noteIsVisible = false;
                    noteEditText.setVisibility(View.GONE);
                }
            }
        });

        addRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reps++;
                repsEditText.setText(String.valueOf(reps));
            }
        });

        subRepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reps > 0){
                    reps--;
                    repsEditText.setText(String.valueOf(reps));
                }

            }
        });

        addWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight+=5.0;
                weightEditText.setText(String.valueOf(weight));
            }
        });

        subWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight >= 5.0){
                    weight-=5.0;
                    weightEditText.setText(String.valueOf(weight));

                }
                else {
                    weight = 0;
                    weightEditText.setText(String.valueOf(weight));
                }
            }
        });

        weightEditText = (EditText)v.findViewById(R.id.weightEditText);
        repsEditText = (EditText)v.findViewById(R.id.repsEditText);

        if(weight > 0){
            weightEditText.setText("" + weight);

        }
        if(reps > 0){
            repsEditText.setText("" + reps);

        }

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (weightEditText.getText().toString().trim().length() == 0) {
                    weight = 0;
                } else if(weightEditText.getText().toString().trim().equals(".")){
                    // distanceEditText.setText("0.");
                    weight = 0.0;
                }
                else {
                    weight = Double.parseDouble(weightEditText.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(repsEditText.getText().toString().trim().length() == 0){
                    reps = 0;
                }
                else {
                    reps = Integer.parseInt(repsEditText.getText().toString());
                }
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
