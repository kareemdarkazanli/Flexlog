package com.kareemdarkazanli.flexlogapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 4/24/16.
 */
public class CatTypeAdapter extends ArrayAdapter<String> {

    private TextView nameTextView;
    private ImageView iconImageView;

    public CatTypeAdapter(ArrayList<String> u, int textViewResourceId, Context c){
        super(c, textViewResourceId, u);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.activity_cattype,parent, false);

        String name = getItem(position);


        nameTextView = (TextView) convertView.findViewById(R.id.cattypeTextView);
        iconImageView = (ImageView) convertView.findViewById(R.id.cattypeImageView);

        if (name.equals("Abs")){
            iconImageView.setImageResource(R.drawable.abscolor);
        }
        else if(name.equals("Back")){
            iconImageView.setImageResource(R.drawable.backcolor);
        }
        else if(name.equals("Biceps")){
            iconImageView.setImageResource(R.drawable.armscolor);
        }
        else if(name.equals("Chest")){
            iconImageView.setImageResource(R.drawable.chestcolor);
        }
        else if(name.equals("Legs")){
            iconImageView.setImageResource(R.drawable.legcolor);
        }
        else if(name.equals("Shoulder")){
            iconImageView.setImageResource(R.drawable.shoulderscolor);
        }
        else if(name.equals("Triceps")){
            iconImageView.setImageResource(R.drawable.armscolor);
        }
        else if(name.equals("Cardio")){
            iconImageView.setImageResource(R.drawable.cardiocolor);
        }
        else if(name.equals("Weight and Reps")){
            iconImageView.setImageResource(R.drawable.dumbbellcolor);
        }
        else if(name.equals("Distance and Time")){
            iconImageView.setImageResource(R.drawable.cardiocolor);
        }

        nameTextView.setText(name);


        return convertView;    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        convertView = inflater.inflate(R.layout.activity_cattype,parent, false);

        String name = getItem(position);


        nameTextView = (TextView) convertView.findViewById(R.id.cattypeTextView);
        iconImageView = (ImageView) convertView.findViewById(R.id.cattypeImageView);

        if (name.equals("Abs")){
            iconImageView.setImageResource(R.drawable.abscolor);
        }
        else if(name.equals("Back")){
            iconImageView.setImageResource(R.drawable.backcolor);
        }
        else if(name.equals("Biceps")){
            iconImageView.setImageResource(R.drawable.armscolor);
        }
        else if(name.equals("Chest")){
            iconImageView.setImageResource(R.drawable.chestcolor);
        }
        else if(name.equals("Legs")){
            iconImageView.setImageResource(R.drawable.legcolor);
        }
        else if(name.equals("Shoulder")){
            iconImageView.setImageResource(R.drawable.shoulderscolor);
        }
        else if(name.equals("Triceps")){
            iconImageView.setImageResource(R.drawable.armscolor);
        }
        else if(name.equals("Cardio")){
             iconImageView.setImageResource(R.drawable.cardiocolor);
        }
        else if(name.equals("Weight and Reps")){
            iconImageView.setImageResource(R.drawable.dumbbellcolor);
        }
        else if(name.equals("Distance and Time")){
            iconImageView.setImageResource(R.drawable.cardiocolor);
        }

        nameTextView.setText(name);


        return convertView;
    }
}
