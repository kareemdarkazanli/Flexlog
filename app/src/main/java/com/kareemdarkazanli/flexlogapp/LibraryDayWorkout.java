package com.kareemdarkazanli.flexlogapp;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 12/22/15.
 */
public class LibraryDayWorkout {

    private ArrayList<DayWorkout> userExercises;
    //private ArrayList<UserExercise> allUserExercises;
    private Exercise mExercise;
    private static LibraryDayWorkout sUserExercisesLibrary;


    private Context mAppContext;
    private static final String mFilename = "librarydayworkout.json";


    public static LibraryDayWorkout get(Context c) throws JSONException, IOException{
        if(sUserExercisesLibrary == null){
            sUserExercisesLibrary = new LibraryDayWorkout(c);
        }
        return sUserExercisesLibrary;
    }

    private LibraryDayWorkout(Context c) throws IOException, JSONException{
        mAppContext = c;
        userExercises = loadDayWorkouts();
    }


    public ArrayList<DayWorkout> loadDayWorkouts() throws IOException, JSONException{
        ArrayList<DayWorkout> dayWorkouts = new ArrayList<DayWorkout>();
        BufferedReader reader = null;
        try{
            InputStream in = mAppContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            for(int i = 0; i < array.length(); i++){
                dayWorkouts.add(new DayWorkout(array.getJSONObject(i)));
            }
        }catch (FileNotFoundException e){

        }
        finally {
            if(reader != null)
                reader.close();
        }
        return dayWorkouts;
    }

    public void saveDayWorkouts() throws JSONException, IOException{
        JSONArray array = new JSONArray();

        for(int i = 0; i < userExercises.size(); i++){
            array.put(userExercises.get(i).toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = mAppContext.openFileOutput(mFilename, mAppContext.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer != null)
                writer.close();
        }
    }


    public JSONArray toJSON() throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for(DayWorkout l: userExercises)
        {
            array.put(l.toJSON());
        }

        return array;
    }


    public static LibraryDayWorkout get(){
        return sUserExercisesLibrary;
    }

    public ArrayList<DayWorkout> getUserExercises(){
        return userExercises;
    }

    public void addUserExercises(DayWorkout e){
        userExercises.add(0, e);
    }

    public void setUserExercises(int index, DayWorkout e){
        userExercises.set(index, e);
    }

    public DayWorkout getUserExercises(long l){
        for(DayWorkout e: userExercises){
            if(e.getDateInt() == l){
                Log.e("myApp", "Found");

                return e;
            }
        }
        return  null;
    }

    public boolean hasUserExercises(long l){
        for(DayWorkout e: userExercises){
            Log.e("e: ", "" + e.getDateInt());
            Log.e("l: ", "" + l);
            if(e.getDateInt() == l){
                return true;
            }
        }
        return  false;
    }

}
