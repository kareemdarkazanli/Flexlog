package com.kareemdarkazanli.flexlogapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by kareem_darkazanli on 4/28/16.
 */
public class Save {

    private Context currentActivity;
    private String nameOfFolder = "/Flexlog";
    private String nameOfFile;

    public void SaveImage(Context context, Photo photo, Bitmap image){
        currentActivity = context;
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + nameOfFolder;

        File directory = new File(filePath);

        if(!directory.exists()){
            directory.mkdirs();
        }

        File file = new File(directory,photo.getFilename());

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            MakeSureFileWasCreatedThenMakeAvailable(file);
            Toast.makeText(context, "Photo saved to camera roll",
                    Toast.LENGTH_LONG).show();
            //Make a toast
        }catch (Exception e){

        }
    }

    private void MakeSureFileWasCreatedThenMakeAvailable(File file){
        MediaScannerConnection.scanFile(currentActivity, new String[] {file.toString()}, null, new
        MediaScannerConnection.OnScanCompletedListener(){
            @Override
            public void onScanCompleted(String path, Uri uri) {

            }
        });
    }

}
