package com.kareemdarkazanli.flexlogapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by kareem_darkazanli on 12/22/15.
 */

//Data saved to Parse
public class DayWorkout {


    private ArrayList<UserExercise> exercises;
    long dateInt;
    String date;
    ArrayList<UserExercise> allUserExercises;

    private static final String JSON_TYPE = "type";
    private static final String JSON_NAME = "name";

    private static final String JSON_USER_EXERCISES = "userexercises";
    private static final String JSON_DATE_INT = "dateint";
    private static final String JSON_DATE_STRING = "datestring";


    public DayWorkout(){
       allUserExercises = LibraryAllUserExercises.get().getAllUserExercises();
        exercises = new ArrayList<UserExercise>();
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayString = String.valueOf(day);
        if(day < 10){
            dayString = "0"+dayString;
        }
        int month = calendar.get(Calendar.MONTH);
        String monthString = String.valueOf(month);
        if(month < 10){
            monthString = "0"+ monthString;
            Log.e("Month string: ", monthString);
        }
        int year = calendar.get(Calendar.YEAR);

        dateInt = Integer.parseInt(String.valueOf(year) + monthString + dayString);

        date = "";
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if(dayOfWeek == 1){
            date += "Sun, ";
        }
        else if(dayOfWeek == 2){
            date += "Mon, ";
        }
        else if(dayOfWeek == 3){
            date += "Tue, ";
        }
        else if(dayOfWeek == 4){
            date += "Wed, ";
        }
        else if(dayOfWeek == 5){
            date += "Thu, ";
        }
        else if(dayOfWeek == 6){
            date += "Fri, ";
        }
        else if(dayOfWeek == 7){
            date += "Sat, ";
        }

        if(month == 0){
            date+= "Jan ";
        }
        else if(month == 1){
            date+= "Feb ";
        }
        else if(month == 2){
            date+= "Mar ";
        }
        else if(month == 3){
            date+= "Apr ";
        }
        else if(month == 4){
            date+= "May ";
        }
        else if(month == 5){
            date+= "June ";
        }
        else if(month == 6){
            date+= "July ";
        }
        else if(month == 7){
            date+= "Aug ";
        }
        else if(month == 8){
            date+= "Sep ";
        }
        else if(month == 9){
            date+= "Oct ";
        }
        else if(month == 10){
            date+= "Nov ";
        }
        else if(month == 11){
            date+= "Dec ";
        }

        date+= dayOfMonth;

//        dayWorkoutParse.setParseUser(ParseUser.getCurrentUser());



    }

    public String getDateWithYear(){
        return date + " " + String.valueOf(dateInt).substring(0, 4);
    }

    public DayWorkout(JSONObject json) throws JSONException, IOException{
        dateInt = json.getInt(JSON_DATE_INT);
        date = json.getString(JSON_DATE_STRING);
        exercises = loadExercises(json.getJSONArray(JSON_USER_EXERCISES));

    }

    private ArrayList<UserExercise> loadExercises(JSONArray jsonArray) throws IOException, JSONException {
        ArrayList<UserExercise> exercises = new ArrayList<UserExercise>();
        allUserExercises = LibraryAllUserExercises.get().getAllUserExercises();

        try {
            JSONArray array = jsonArray;
            for(int i = 0; i < array.length(); i++) {

                UserExercise e;
                //Log.e("Cardio",array.getJSONObject(i).getString(JSON_NAME));


                if(array.getJSONObject(i).getString(JSON_TYPE).equals("Weight and Reps")){
                    e = new UserWeightExercise(array.getJSONObject(i));
                }else{
                    e = new UserCardioExercise(array.getJSONObject(i));
                }
                Log.e("Date: ", dateInt + "");
                e.setDate(dateInt);
                exercises.add(e);
                allUserExercises.add(e);


            }
        }catch (Exception e)
        {

        }

        Log.e("Size: ", exercises.size() + "");
        return exercises;
    }

    public JSONArray toJSONArray() throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(UserExercise l: exercises)
        {
            array.put(l.toJSON());
        }
        return array;
    }

    public JSONObject toJSON() throws JSONException, IOException {
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        for(UserExercise l: exercises)
        {
            array.put(l.toJSON());
        }
        json.put(JSON_USER_EXERCISES, array);
        json.put(JSON_DATE_INT, dateInt);
        json.put(JSON_DATE_STRING, date);

        return json;

    }

    public void addExercise(UserExercise exercise) throws JSONException, IOException
    {
        int index = 0;
        if(exercises.size() == 0){
            exercises.add(exercise);
        }
        else{
            for(int i = 0; i < exercises.size(); i++){
                if(exercises.get(i).getName().equals(exercise.getName())){
                    index = i;
                    exercises.add(index, exercise);
                    return;
                }

            }

            exercises.add(index, new UserCardioExercise());
            exercises.add(index, exercise);
        }


    }


    public UserExercise getExercise(int index)
    {
        return exercises.get(index);
    }

    public ArrayList<UserExercise> getExercises(){

        return exercises;
    }
    public void removeExercise(int index) throws JSONException, IOException{
        exercises.remove(index);
        if(exercises.get(exercises.size() - 1).getName().equals("")){
            exercises.remove(exercises.size() - 1);
        }

        JSONArray array = toJSONArray();

    }

    public void removeExercise(UserExercise e) throws JSONException, IOException{

        boolean remove = false;
        for(int i = 0; i < exercises.size(); i++){
            if(exercises.get(i).equals(e)){
                if(exercises.size() == 1){

                }
                else if(i == (exercises.size() - 1)){
                    if(exercises.get(i - 1).getName().equals("")){
                        remove = true;
                    }
                }
                else if(i == 0){
                    if(exercises.get(i + 1).getName().equals("")){
                        exercises.remove(i + 1);
                    }
                }
                else{
                    if(!exercises.get(i + 1).getName().equals(exercises.get(i).getName())){
                        if(exercises.get(i - 1).getName().equals("")){
                            remove = true;
                        }
                    }
                }

                exercises.remove(i);
                if (remove){
                    exercises.remove(i - 1);
                }
                return;
            }
        }


    }

    public long getDateInt(){
        return dateInt;
    }

    public String toString(){
        return date;
    }

}
