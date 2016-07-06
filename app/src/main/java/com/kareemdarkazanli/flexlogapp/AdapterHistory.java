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
 * Created by kareem_darkazanli on 12/31/15.
 */
public class AdapterHistory extends ArrayAdapter<DayWorkout> {

    ImageView imageViewOne;
    ImageView imageViewTwo;
    ImageView imageViewThree;
    ImageView imageViewFour;
    ImageView imageViewFive;
    ImageView imageViewSix;
    ImageView imageViewSeven;
    ImageView imageViewEight;

    boolean imageViewOneIsTaken = false;
    boolean imageViewTwoIsTaken = false;
    boolean imageViewThreeIsTaken = false;
    boolean imageViewFourIsTaken = false;
    boolean imageViewFiveIsTaken = false;
    boolean imageViewSixIsTaken = false;
    boolean imageViewSevenIsTaken = false;
    boolean imageViewEightIsTaken = false;


    public AdapterHistory(ArrayList<DayWorkout> u, int textViewResourceId, Context c){
        super(c, textViewResourceId, u);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.list_item_main, parent, false);


        DayWorkout u = getItem(position);


        imageViewOne = (ImageView) convertView.findViewById(R.id.imageViewOne);
        imageViewTwo = (ImageView) convertView.findViewById(R.id.imageViewTwo);
        imageViewThree = (ImageView) convertView.findViewById(R.id.imageViewThree);
        imageViewFour = (ImageView) convertView.findViewById(R.id.imageViewFour);
        imageViewFive = (ImageView) convertView.findViewById(R.id.imageViewFive);
        imageViewSix = (ImageView) convertView.findViewById(R.id.imageViewSix);
        imageViewSeven = (ImageView) convertView.findViewById(R.id.imageViewSeven);
        imageViewEight = (ImageView) convertView.findViewById(R.id.imageViewEight);

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextVieww);

        /*HashMap<String, Integer> icons = new HashMap<>();
        icons.put("Abs", R.drawable.abs);
        icons.put("Back,")*/

        dateTextView.setText(u.date);

        boolean absAdded = false;
        boolean backAdded = false;
        boolean bicepsAdded = false;
        boolean chestAdded = false;
        boolean legsAdded = false;
        boolean shoulderAdded = false;
        boolean tricepsAdded = false;
        boolean cardioAdded = false;



        ArrayList<String> categories = new ArrayList<String>();


        for(int i = 0; i < u.getExercises().size(); i++){
            if(u.getExercises().get(i).getCategory().equals("Abs")){
                if(!absAdded){
                    //categories.add("Abs");
                    addToImageView(R.drawable.abscolor);
                    absAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Back")){
                if(!backAdded){
                    //categories.add("Back");
                    addToImageView(R.drawable.backcolor);
                    backAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Biceps")){
                if(!bicepsAdded){
                    //categories.add("Biceps");
                    addToImageView(R.drawable.armscolor);
                    bicepsAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Chest")){
                if(!chestAdded){
                    addToImageView(R.drawable.chestcolor);
                    //categories.add("Chest");
                    chestAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Legs")){
                if(!legsAdded){
                    addToImageView(R.drawable.legcolor);
                    //categories.add("Legs");
                    legsAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Shoulder")){
                if(!shoulderAdded){
                    addToImageView(R.drawable.shoulderscolor);
                    //categories.add("Shoulder");
                    shoulderAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Triceps")){
                if(!tricepsAdded){
                    addToImageView(R.drawable.armscolor);
                    //categories.add("Triceps");
                    tricepsAdded = true;
                }
            }
            else if(u.getExercises().get(i).getCategory().equals("Cardio")){
                if(!cardioAdded){
                    addToImageView(R.drawable.cardiocolor);
                    //categories.add("Cardio");
                    cardioAdded = true;
                }
            }

        }


        imageViewOneIsTaken = false;
        imageViewTwoIsTaken = false;
        imageViewThreeIsTaken = false;
        imageViewFourIsTaken = false;
        imageViewFiveIsTaken = false;
        imageViewSixIsTaken = false;
        imageViewSevenIsTaken = false;
        imageViewEightIsTaken = false;

        return convertView;
    }

    private void addToImageView(Integer drawable){
        if(!imageViewOneIsTaken){
            imageViewOne.setImageResource(drawable);
            imageViewOneIsTaken = true;
        }
        else if(!imageViewTwoIsTaken){
            imageViewTwo.setImageResource(drawable);
            imageViewTwoIsTaken = true;
        }
        else if(!imageViewThreeIsTaken){
            imageViewThree.setImageResource(drawable);
            imageViewThreeIsTaken = true;
        }
        else if(!imageViewFourIsTaken){
            imageViewFour.setImageResource(drawable);
            imageViewFourIsTaken = true;
        }
        else if(!imageViewFiveIsTaken){
            imageViewFive.setImageResource(drawable);
            imageViewFiveIsTaken = true;
        }
        else if(!imageViewSixIsTaken){
            imageViewSix.setImageResource(drawable);
            imageViewSixIsTaken = true;
        }
        else if(!imageViewSevenIsTaken){
            imageViewSeven.setImageResource(drawable);
            imageViewSevenIsTaken = true;
        }
        else if(!imageViewEightIsTaken){
            imageViewEight.setImageResource(drawable);
            imageViewEightIsTaken = true;
        }

    }
}
