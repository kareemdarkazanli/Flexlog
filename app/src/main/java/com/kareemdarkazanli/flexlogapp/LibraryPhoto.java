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
 * Created by kareem_darkazanli on 1/4/16.
 */
public class LibraryPhoto {

    private ArrayList<Photo> photos;


    private Context mContext;
    private static final String mFilename = "photos.json";

    private static LibraryPhoto sLibraryPhoto;



    public static LibraryPhoto get(Context c) throws JSONException, IOException{
        if(sLibraryPhoto == null){
            sLibraryPhoto = new LibraryPhoto(c);
        }
        return sLibraryPhoto;
    }

    private LibraryPhoto(Context c) throws IOException, JSONException{
        mContext = c;
        photos = loadPhotos();
    }

    public void savePhotos() throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(Photo p : photos){
            array.put(p.toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally {
            if(writer != null)
                writer.close();
        }
    }



    public ArrayList<Photo> loadPhotos() throws IOException, JSONException {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                photos.add(new Photo(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {

        } finally {

        }
        if (reader != null)
            reader.close();
        return photos;
    }

    public static LibraryPhoto get(){
        return sLibraryPhoto;
    }

    public ArrayList<Photo> getPhotos(){
        return photos;
    }

    public void addPhoto(Photo p){
        photos.add(0, p);

    }

}


