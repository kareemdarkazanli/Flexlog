package com.kareemdarkazanli.flexlogapp;

import java.util.ArrayList;

/**
 * Created by kareem_darkazanli on 1/21/16.
 */
public class LibraryAllUserExercises {

    private static LibraryAllUserExercises sLibraryAllUserExercises;
    private ArrayList<UserExercise> allUserExercises;

    private LibraryAllUserExercises() {
        allUserExercises = new ArrayList<UserExercise>();
    }

    public static LibraryAllUserExercises get(){
        if(sLibraryAllUserExercises == null){
            sLibraryAllUserExercises = new LibraryAllUserExercises();
        }
        return sLibraryAllUserExercises;
    }

    public ArrayList<UserExercise> getAllUserExercises(){
        return allUserExercises;
    }


}
