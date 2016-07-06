package com.kareemdarkazanli.flexlogapp;

import android.content.Context;

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
 * Created by kareem_darkazanli on 12/23/15.
 */
public class LibraryExercise {

    private ArrayList<Exercise> exercises;
    private Context mAppContext;
    private static LibraryExercise sExerciseLibrary;
    private static final String mFilename = "libraryexercise.json";


    public static LibraryExercise get(Context c) throws JSONException, IOException{
        if(sExerciseLibrary == null){
            sExerciseLibrary = new LibraryExercise(c);
        }


        return sExerciseLibrary;
    }

    private LibraryExercise(Context c) throws IOException, JSONException{
        mAppContext = c;
        exercises = loadExercises();
        if(exercises.size() == 0){
            exercises.add(new Exercise("Bentover Rows", "Back", "Weight and Reps"));
            exercises.add(new Exercise("Barbell Curl", "Biceps", "Weight and Reps"));
            exercises.add(new Exercise("Barbell Rows", "Back", "Weight and Reps"));
            exercises.add(new Exercise("Barbell Shrug", "Shoulder", "Weight and Reps"));
            exercises.add(new Exercise("Barbell Squat", "Legs", "Weight and Reps"));
            exercises.add(new Exercise("Chest Press", "Chest", "Weight and Reps"));
            exercises.add(new Exercise("Crunches", "Abs", "Weight and Reps"));
            exercises.add(new Exercise("Cycling", "Cardio", "Distance and Time"));
            exercises.add(new Exercise("Deadlift", "Back", "Weight and Reps"));
            exercises.add(new Exercise("Dips", "Triceps", "Weight and Reps"));
            exercises.add(new Exercise("Dumbbell Curl", "Biceps", "Weight and Reps"));
            exercises.add(new Exercise("Leg Press", "Legs", "Weight and Reps"));
            exercises.add(new Exercise("Lying Leg Curl", "Legs", "Weight and Reps"));
            exercises.add(new Exercise("Rowing Machine", "Cardio", "Distance and Time"));
            exercises.add(new Exercise("Running (Treadmill)", "Cardio", "Distance and Time"));
            exercises.add(new Exercise("Shoulder Press", "Shoulder", "Weight and Reps"));
            exercises.add(new Exercise("Tricep Extension", "Triceps", "Weight and Reps"));
            exercises.add(new Exercise("Triceps Press", "Triceps", "Weight and Reps"));



        }
    }


    public ArrayList<Exercise> loadExercises() throws IOException, JSONException{
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
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
                exercises.add(new Exercise(array.getJSONObject(i)));
            }
        }catch (FileNotFoundException e){

        }
        finally {
            if(reader != null)
                reader.close();
        }
        return exercises;
    }

    public void saveExercises() throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(Exercise exercise: exercises){
            array.put(exercise.toJSON());
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



    public LibraryExercise(){
        exercises = new ArrayList<Exercise>();
    }

    private LibraryExercise(JSONArray array, Context c){
        mAppContext = c;
        try {

            exercises = loadExercises(array);
        }catch (Exception e)
        {

        }

    }

    public static LibraryExercise get(JSONArray array, Context c){
        if(sExerciseLibrary == null){
            sExerciseLibrary = new LibraryExercise(array, c);
        }
        return sExerciseLibrary;
    }
    public static LibraryExercise get(){
        return sExerciseLibrary;
    }


    public ArrayList<Exercise> getExercises(){
        return exercises;
    }
    public void addExercise(Exercise e)
    {
        exercises.add(e);
    }

    public Exercise getExercise(int index)
    {
       return exercises.get(index);
    }

    public Exercise getExercise(String e)
    {
        for(Exercise exercise: exercises)
        {
            if(exercise.getExerciseName().equals(e))
            {
                return exercise;
            }
        }
        return null;
    }

    private ArrayList<Exercise> loadExercises(JSONArray jsonArray) throws IOException, JSONException {
        ArrayList<Exercise> exercises = new ArrayList<Exercise>();
        try {
            JSONArray array = jsonArray;
            for(int i = 0; i < array.length(); i++) {
                exercises.add(new Exercise(array.getJSONObject(i)));
            }
        }catch (Exception e)
        {
            //Ignore this one; it happens when starting fresh
        }
        return exercises;
    }

    public JSONArray toJSONARRAY() throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(Exercise l: exercises)
        {
            array.put(l.toJSON());
        }
        return array;
    }

}
