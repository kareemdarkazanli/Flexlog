package com.kareemdarkazanli.flexlogapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kareem_darkazanli on 12/22/15.
 */
public class Exercise {

    private String exerciseName;
    private String category;
    private String type;

    private static final String JSON_NAME = "name";
    private static final String JSON_CATEGORY = "category";
    private static final String JSON_TYPE = "type";

    public Exercise(String e, String m, String t)
    {
        exerciseName = e;
        category = m;
        type = t;
    }

    public Exercise()
    {
        exerciseName = "";
        category = "";
        type = "";
    }

    public Exercise(JSONObject json) throws JSONException{
        exerciseName = json.getString(JSON_NAME);
        category = json.getString(JSON_CATEGORY);
        type = json.getString(JSON_TYPE);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, exerciseName);
        json.put(JSON_CATEGORY, category);
        json.put(JSON_TYPE, type);

        return json;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getMuscleGroup() {
        return category;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.category = muscleGroup;
    }

    public String toString()
    {
        return exerciseName;
    }

    public String getType(){
        return  type;
    }

    public void setType(String t){
        type = t;
    }
}
