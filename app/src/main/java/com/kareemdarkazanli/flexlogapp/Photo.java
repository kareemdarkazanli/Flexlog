package com.kareemdarkazanli.flexlogapp;

import android.graphics.drawable.BitmapDrawable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by kareem_darkazanli on 1/1/16.
 */
public class Photo {


    private static final String JSON_DATE = "date";
    private static final String JSON_FILENAME = "filename";
    private static final String JSON_ISSELFIE = "isselfie";
    private static final String JSON_NOTE = "note";
    private static final String JSON_DAY = "day";


    private byte[] data;
    private String fileName;
    private BitmapDrawable image;
    private boolean isSelfie = false;
    private String note;
    private int day;
    private long date;



    public void setFilename(String s){
        fileName = s;
    }
    public void setDate(long l){
        date = l;
    }

    public long getDate(){
        return date;
    }

    public int getDay(){
        return day;
    }

    public void setDay(int d){
        day = d;
    }

    public void setIsSelfie(boolean b){
        isSelfie = b;
    }
    public String getNote(){
        return note;
    }

    public void setNote(String n){
        note = n;
    }

    public byte[] getData(){
        return data;
    }

    public String getFilename(){
        return fileName;
    }

    public BitmapDrawable getImage(){
        return image;
    }

    public boolean getIsSelfie(){
        return isSelfie;
    }

    public void setImage(BitmapDrawable bitmapDrawable){
        image = bitmapDrawable;
    }



    public JSONObject toJSON() throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_DATE, date);
        jsonObject.put(JSON_DAY, day);
        jsonObject.put(JSON_FILENAME, fileName);
        jsonObject.put(JSON_ISSELFIE, isSelfie);
        jsonObject.put(JSON_NOTE, note);
        return jsonObject;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Photo(JSONObject jsonObject) throws JSONException{
        date = jsonObject.getLong(JSON_DATE);
        isSelfie = jsonObject.getBoolean(JSON_ISSELFIE);
        note = jsonObject.getString(JSON_NOTE);
        fileName = jsonObject.getString(JSON_FILENAME);
        day = jsonObject.getInt(JSON_DAY);
    }


    public Photo(byte[] d, String g){
        data = d;
        fileName = g;
    }

    public Photo(byte[] d, String g, BitmapDrawable i){
        data = d;
        fileName = g;
        image = i;
        isSelfie = false;
    }

    public Photo(byte[] d, String g, boolean b, BitmapDrawable i){
        data = d;
        fileName = g;
        image = i;
        isSelfie = b;

    }

    public Photo(byte[] d, String g, boolean s){
        data = d;
        fileName = g;
        isSelfie = s;
    }



}
