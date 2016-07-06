package com.kareemdarkazanli.flexlogapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 12/29/15.
 */
public class UserExercisesAdapter extends ArrayAdapter<UserExercise> {
    public UserExercisesAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public UserExercisesAdapter(Context context, int textViewResourceId, ArrayList<UserExercise> userExercises) {
        super(context, textViewResourceId, userExercises);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item_day, null);
        }

        UserExercise u = getItem(position);

        if (u != null) {
            TextView nameTextView = (TextView) v.findViewById(R.id.userExerciseNameTextView);
            TextView repsTextView = (TextView) v.findViewById(R.id.userExerciseRepsTextVie);
            TextView weightTextView = (TextView) v.findViewById(R.id.userExerciseWeightTextView);
            TextView noteTextView = (TextView) v.findViewById(R.id.userExerciseCommentTextView);

            if (nameTextView != null) {
                nameTextView.setText(u.getName());
            }

            if (repsTextView != null) {
                if(u.getType().equals("Tear muscle")){
                  // repsTextView.setText(((UserWeightExercise)u).getReps());
                }
                else if(u.getName().equals("")){
                    repsTextView.setText("");
                }
                else{
                    repsTextView.setText(String.valueOf(((UserCardioExercise) u).getDistance()));
                }
            }

            if (weightTextView != null) {
                if(u.getType().equals("Tear muscle")){
                    weightTextView.setText(String.valueOf(((UserWeightExercise)u).getWeight()));
                }
                else if(u.getName().equals("")){
                    weightTextView.setText("");
                }
                else{
                    weightTextView.setText(String.valueOf(((UserCardioExercise) u).getTime()));
                }
            }
        }


        return convertView;
    }

}
