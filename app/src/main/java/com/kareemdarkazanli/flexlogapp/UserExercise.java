package com.kareemdarkazanli.flexlogapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kareem_darkazanli on 12/28/15.
 */
public abstract class UserExercise {

    private String name;
    private String category;
    private String type;
    private String note;
    private String unit;
    private long date;

    public UserExercise(){
        name = "";
        category = "";
        type = "";
        unit = "";
        note = "";

    }

    public UserExercise(String n, String c, String t, String p, String u){
        name = n;
        category = c;
        type = t;
        note = p;
        unit = u;
    }

    public UserExercise(String n, String c, String t){
        name = n;
        category = c;
        type = t;
    }

    public String getUnit(){
        return unit;
    }

    public void setUnit(String s){
        unit = s;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String c){
        category = c;
    }

    public String getType(){
        return type;
    }

    public void setType(String t){
        type = t;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(long d){
        date = d;
    }

    public long getDate(){
        return date;
    }

    public abstract JSONObject toJSON() throws JSONException;


}
