package com.kareemdarkazanli.flexlogapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kareem_darkazanli on 12/22/15.
 */
public class UserWeightExercise extends UserExercise{

    private double weight;
    private int reps;

    private static final String JSON_NAME = "name";
    private static final String JSON_NOTE = "note";
    private static final String JSON_TYPE = "type";
    private static final String JSON_CATEGORY = "category";
    private static final String JSON_WEIGHT = "weight";
    private static final String JSON_REPS = "reps";
    private static final String JSON_UNIT = "unit";


    public UserWeightExercise(String n, String c, String t, String p, double w, int r, String u)
    {
        super(n,c,t,p,u);
        weight = w;
        reps = r;
    }


    public UserWeightExercise(String n, String c, String t, double w, int r)
    {
        super(n,c,t);
        weight = w;
        reps = r;
    }

    public UserWeightExercise()
    {
        super();
        weight = 0;
        reps = 0;

    }

    public UserWeightExercise(JSONObject json) throws JSONException {
        setName(json.getString(JSON_NAME));
        setType(json.getString(JSON_TYPE));
        setCategory(json.getString(JSON_CATEGORY));
        setNote(json.getString(JSON_NOTE));
        setUnit(json.getString(JSON_UNIT));
        //super.setNote(json.getString(JSON_NOTE));
        weight = json.getDouble(JSON_WEIGHT);
        reps = json.getInt(JSON_REPS);


    }

    @Override
    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, getName());
        json.put(JSON_CATEGORY, getCategory());
        json.put(JSON_TYPE, getType());
        json.put(JSON_NOTE, getNote());
        json.put(JSON_UNIT, getUnit());
        json.put(JSON_WEIGHT, weight);
        json.put(JSON_REPS, reps);


        return json;
    }


    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }



    public void setWeight(double weight) {
        this.weight = weight;
    }



    public void setReps(int reps) {
        this.reps = reps;
    }



    public String toString(){
        return reps+ " reps " + weight + " lbs " + getNote();
    }

}
