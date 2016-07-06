package com.kareemdarkazanli.flexlogapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kareem_darkazanli on 12/22/15.
 */
public class UserCardioExercise extends UserExercise{


    private static final String JSON_NAME = "name";
    private static final String JSON_NOTE = "note";
    private static final String JSON_UNIT = "unit";
    private static final String JSON_TYPE = "type";
    private static final String JSON_CATEGORY = "category";
    private static final String JSON_DISTANCE = "distance";
    private static final String JSON_TIME = "time";

    private double distance = 0;
    private long time = 0;

    public UserCardioExercise(String n, String c, String t, String p, double d, long s, String u)
    {
        super(n,c,t,p,u);
        distance = d;
        time = s;

    }

    public UserCardioExercise(String n, String c, String t, double d, long s)
    {
        super(n, c, t);
        //setNote("");
        distance = d;
        time = s;

    }

    public UserCardioExercise(JSONObject json) throws JSONException {
        setName(json.getString(JSON_NAME));
        setType(json.getString(JSON_TYPE));
        setCategory(json.getString(JSON_CATEGORY));
        setNote(json.getString(JSON_NOTE));
        //setNote(json.getString(JSON_NOTE));
        //setNote(json.getString(JSON_NOTE));
        setUnit(json.getString(JSON_UNIT));
        distance = json.getDouble(JSON_DISTANCE);
        time = json.getLong(JSON_TIME);

    }

    @Override
    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, getName());
        json.put(JSON_CATEGORY, getCategory());
        json.put(JSON_TYPE, getType());
        json.put(JSON_NOTE, getNote());
        json.put(JSON_UNIT, getUnit());
        json.put(JSON_DISTANCE, distance);
        json.put(JSON_TIME, time);


        return json;
    }

    public UserCardioExercise(){
        super();
        distance = 0;
        time = 0;
    }

    public double getDistance() {
        return distance;
    }

    public long getTime() {
        return time;
    }



    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String toString(){
        long temp = getTime();
        int hr = (int)(temp/(60 * 60));
        String time = "";
        if(hr == 0){

        }
        else if(hr < 10){
            time += "0"+String.valueOf(hr) +":";
        }
        else{
            time += String.valueOf(hr) + ":";
        }
        temp = (int) (temp % (60 * 60));

        int min = (int)(temp/(60));

        if(min == 0 && hr == 0){

        }
        else if(min < 10){
            time += "0"+String.valueOf(min) + ":";
        }
        else{
            time += String.valueOf(min) + ":";
        }
        temp = (int) (temp % (60));
        int sec = (int)temp;
        if(hr == 0 && min == 0 && sec == 0){
            time = "0s";
        }
        else if(hr == 0 && min == 0){
            time = String.valueOf(sec) + "s";
        }
        else if(sec < 10){
            time += "0"+String.valueOf(sec);
        }
        else{
            time += String.valueOf(sec);
        }
        return String.valueOf(distance) + " " + getUnit() + " " + time + " " + getNote();
    }
}
