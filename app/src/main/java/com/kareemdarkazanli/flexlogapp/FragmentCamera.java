package com.kareemdarkazanli.flexlogapp;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class FragmentCamera extends android.support.v4.app.Fragment {

    private static final String TAG = "FragmentCamera";

    public static final String EXTRA_PHOTO_FILENAME =
            "com.teammelotto7.android.melotto.photo_filename";

    private Camera mCamera;
    private String note;
    private SurfaceView mSurfaceView;
    private View mProgressContainer;
    private Photo p;
    private byte[] image;
    private LibraryPhoto libraryPhoto;
    private ImageButton switchCameraModeButton;
    private ImageButton deleteImageButton;
    private ImageButton checkImageButton;
    private ImageButton takePictureButton;
    private ImageButton picsImageButton;
    private ImageButton galleryImageButton;
    private EditText imageNoteEditText;
    private int cameraId;
    private SurfaceHolder holder;
    private Camera.Size bestCameraSize;
    private Camera.Size bestPhotoSize;
    private boolean pictureTaken = false;
    private RelativeLayout cameraLayout;
    private boolean isFreeCam;
    private ImageView previewPictureImageView;
    private Bitmap bitmap;




    private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
        @Override
        public void onShutter() {
            //Display the progress indicator

        }
    };

    private Camera.PictureCallback mJpegCallback = new Camera.PictureCallback(){
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //Create a filename

            String fileName = getCurrentDate() + UUID.randomUUID().toString() + ".jpg";

            if(cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT){
                p = new Photo(data, fileName, true);//, PictureUtils.getScaledDrawable(getActivity(),data));

            }else{
                p = new Photo(data, fileName, false);//, PictureUtils.getScaledDrawable(getActivity(),data));

            }

            if(cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT){
                bitmap = PictureUtils.getScaledDrawableSelfie(getActivity(), p.getData());

            }else{
                bitmap = PictureUtils.getScaledDrawable(getActivity(), p.getData());
            }
            previewPictureImageView.setVisibility(View.VISIBLE);
            previewPictureImageView.setImageBitmap(bitmap);
            //if(!pictureTaken){
            isFreeCam = false;
            deleteImageButton.setImageResource(R.drawable.red_delete_sign);
            takePictureButton.setVisibility(View.GONE);
            switchCameraModeButton.setVisibility(View.GONE);
            checkImageButton.setVisibility(View.VISIBLE);
            imageNoteEditText.setVisibility(View.VISIBLE);
            picsImageButton.setVisibility(View.GONE);



        }
    };

    private String getCurrentDate(){
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int month = calendar.get(Calendar.MONTH) + 1;

        int year = calendar.get(Calendar.YEAR);

        return String.valueOf(month) + String.valueOf(day) + String.valueOf(year);
    }

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_camera_fragment, container, false);
        libraryPhoto = LibraryPhoto.get();
        previewPictureImageView = (ImageView) v.findViewById(R.id.previewPictureImageView);
        previewPictureImageView.setVisibility(View.GONE);

        cameraLayout = (RelativeLayout)v.findViewById(R.id.cameraLayout);
        galleryImageButton = (ImageButton)v.findViewById(R.id.imageButtonPics);
        galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("custom-event-name");
                // You can also include some extra data.
                intent.putExtra("message", "gallery");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            }
        });
        note = "";
        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        picsImageButton = (ImageButton)v.findViewById(R.id.imageButtonPics);
        isFreeCam = true;
        //mCamera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        switchCameraModeButton = (ImageButton) v.findViewById(R.id.switchButton);
        deleteImageButton = (ImageButton)v.findViewById(R.id.imageButtonCancel);
        checkImageButton = (ImageButton)v.findViewById(R.id.imageButtonCheck);
        imageNoteEditText = (EditText)v.findViewById(R.id.imageNoteEditText);
        checkImageButton.setVisibility(View.GONE);
        imageNoteEditText.setVisibility(View.GONE);


        imageNoteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                note = imageNoteEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSurfaceView = (SurfaceView)v.findViewById(R.id.surfaceView);
       // mSurfaceView.setMinimumWidth(cameraLayout.getWidth());
       // mSurfaceView.setMinimumHeight(cameraLayout.getHeight());
        holder = mSurfaceView.getHolder();
        //holder.lockCanvas();
        //holder.setFixedSize(cameraLayout.getWidth(), cameraLayout.getHeight());
        //setType() and SURFACE_TYPE_PUSH_BUFFERS are both deprecated,
        //but are required for camera preview to work on pre-3.0 devices.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (mCamera != null) {
                        mCamera.setPreviewDisplay(holder);

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error setting up preview display", e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mCamera == null) {
                    return;
                }
                Camera.Parameters parameters = mCamera.getParameters();
                if (Integer.parseInt(Build.VERSION.SDK) >= 8)
                    mCamera.setDisplayOrientation(90);
                else {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        parameters.set("orientation", "portrait");
                        parameters.set("rotation", 90);
                    }
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        parameters.set("orientation", "landscape");
                    }
                }
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);


                bestCameraSize = getBestSupportedSizeKdarks(parameters.getSupportedPreviewSizes(), width, height);
                parameters.setPreviewSize(bestCameraSize.width, bestCameraSize.height);
                bestPhotoSize = getBestSupportedSizeKdarks(parameters.getSupportedPictureSizes(), width, height);
                parameters.setPictureSize(bestPhotoSize.width, bestPhotoSize.height);


                mCamera.setParameters(parameters);
                try {
                    mCamera.startPreview();
                } catch (Exception e) {
                    Log.e(TAG, "Could not start preview", e);
                    mCamera.release();
                    mCamera = null;
                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mCamera != null) {
                    mCamera.stopPreview();
                }
            }
        });


        takePictureButton = (ImageButton)v.findViewById(R.id.captureButton);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCamera != null) {
                    mCamera.takePicture(mShutterCallback, null, mJpegCallback);
                    //if(p.getIsSelfie()){

                    Intent intent = new Intent("custom-event-name");
                    intent.putExtra("message", "freeze");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                        //pictureTaken = true;

                    //}
                    /*else{
                        mCamera.startPreview();
                        pictureTaken = false;
                    }*/



                }
            }
        });

        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFreeCam){
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "home");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }else{

                    previewPictureImageView.setVisibility(View.GONE);
                    closeKeyboard(getActivity(), imageNoteEditText.getWindowToken());
                    imageNoteEditText.setText("");
                    switchCameraModeButton.setVisibility(View.VISIBLE);
                    takePictureButton.setVisibility(View.VISIBLE);
                    imageNoteEditText.setVisibility(View.GONE);
                    checkImageButton.setVisibility(View.GONE);
                    mCamera.startPreview();
                    deleteImageButton.setImageResource(R.drawable.leftred);
                    picsImageButton.setVisibility(View.VISIBLE);
                    isFreeCam = true;
                    Intent intent = new Intent("custom-event-name");
                    intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }

            }
        });

        checkImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new SavePhotoRunnable().run();





                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
                    p.setIsSelfie(false);
                }
                else{
                    p.setIsSelfie(true);
                }
                if(LibraryDayWorkout.get().getUserExercises().size() > 0){
                    p.setDay(1 + (int)(getDateLong() - LibraryDayWorkout.get().getUserExercises().get(LibraryDayWorkout.get().getUserExercises().size() - 1).getDateInt()));
                } else {
                    p.setDay(1);
                }
                p.setNote(note);
                p.setDate(getDateLong());

                isFreeCam = true;
                picsImageButton.setVisibility(View.VISIBLE);
                deleteImageButton.setImageResource(R.drawable.leftred);
                imageNoteEditText.setText("");
                switchCameraModeButton.setVisibility(View.VISIBLE);
                takePictureButton.setVisibility(View.VISIBLE);
                imageNoteEditText.setVisibility(View.GONE);
                checkImageButton.setVisibility(View.GONE);

                previewPictureImageView.setVisibility(View.GONE);




                mCamera.startPreview();


                closeKeyboard(getActivity(), imageNoteEditText.getWindowToken());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                FileOutputStream os = null;
                boolean success = true;
                try{
                    os = getActivity().openFileOutput(p.getFilename(), Context.MODE_PRIVATE);
                    os.write(byteArray);
                }catch (Exception e){
                    success = false;
                }finally{
                    try{
                        if(os != null)
                            os.close();
                    } catch (Exception e){
                        success = false;
                    }
                }
                libraryPhoto.addPhoto(p);
                try{
                    LibraryPhoto.get().savePhotos();
                }catch (Exception e){

                }

                Log.e("Pic:", "Taken");
                Toast.makeText(getActivity(), "Photo saved",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent("custom-event-name");
                // You can also include some extra data.
                intent.putExtra("message", "newphoto");
                //intent.putExtra("message", "release");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);



            }
        });


        switchCameraModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.release();
                if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK){
                    cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    //switchCameraModeButton.setImageResource(R.drawable.slr_back_side);
                }
                else {
                    cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
                    //switchCameraModeButton.setImageResource(R.drawable.selfie);
                }

                mCamera = Camera.open(cameraId);
                try {
                    //this step is critical or preview on new camera will no know where to render to
                    mCamera.setPreviewDisplay(holder);
                    Camera.Parameters parameters = mCamera.getParameters();

                    if(cameraId == Camera.CameraInfo.CAMERA_FACING_BACK)
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);


                    bestCameraSize = getBestSupportedSizeKdarks(parameters.getSupportedPreviewSizes());

                    bestPhotoSize = getBestSupportedSizeKdarks(parameters.getSupportedPictureSizes());
                    parameters.setPreviewSize(bestCameraSize.width, bestCameraSize.height);
                    parameters.setPictureSize(bestPhotoSize.width, bestPhotoSize.height);

                    mCamera.setParameters(parameters);
                    if (Integer.parseInt(Build.VERSION.SDK) >= 8)
                        mCamera.setDisplayOrientation(90);
                    else
                    {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                        {
                            parameters.set("orientation", "portrait");
                            parameters.set("rotation", 90);
                        }
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                        {
                            parameters.set("orientation", "landscape");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCamera.startPreview();
            }
        });

        return v;
    }


    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }


    public static Camera.Size getBestSize(List<Camera.Size> supportedSizes, int displayWidth,
            int displayHeight) {

        final int PREVIEW_SIZE_WIDTH_EMULATOR = 176;
        final int PREVIEW_SIZE_HEIGHT_EMULATOR = 144;
        final int PICTURE_SIZE_WIDTH_EMULATOR = 213;
        final int PICTURE_SIZE_HEIGHT_EMULATOR = 350;

        double temporalDiff = 0;
        double diff = Integer.MAX_VALUE;

        Camera.Size size = null;
        Camera.Size supportedSize = null;


            Iterator<Camera.Size> iterator = supportedSizes.iterator();
            while (iterator.hasNext()) {
                supportedSize = iterator.next();
                temporalDiff = Math.sqrt(
                        Math.pow(supportedSize.width - displayWidth, 2) +
                                Math.pow(supportedSize.height - displayHeight, 2));

                if (temporalDiff < diff) {
                    diff = temporalDiff;
                    size = supportedSize;
                }
            }


        return size;
    }
    /**
     * A simple algorithm to get the largest size available. For a more
     * robust version, see CameraPreview.java in the ApiDemos
     * sample app from Android
     */

    private Camera.Size getBestPhotoSize(List<Camera.Size> sizes, int width, int height)
    {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        int j = 0;
        for(int i = 0; i < sizes.size(); i++){//Camera.Size s: sizes){
            //Log.e("Size:", "width:" + sizes.get(i).width + "height:"+sizes.get(i).height);
            Log.e("Size:", "width:" + sizes.get(i).width + "height:"+sizes.get(i).height);

            int area = sizes.get(i).width * sizes.get(i).height;
            if(area > largestArea) {
                bestSize = sizes.get(i);
                j = i;
                largestArea = area;

            }

        }

        //Log.e("Size:", "width:" + bestSize.width + "height:" + bestSize.height);


        Log.e("Best Photo Size:", "width:" + bestSize.width + "height:" + bestSize.height);
        return sizes.get(1);
    }

    private Camera.Size getBestSupportedSizeKdarks(List<Camera.Size> sizes, int width, int height) {
        Camera.Size bestSize = sizes.get(0);


        double bestRatio = 1.7777;
        int largestArea = bestSize.width * bestSize.height;
        int size = 0;
        double delta = Math.abs((((double)bestSize.width) / bestSize.height) - bestRatio);

        for (int i = 0; i < sizes.size(); i++) {//Camera.Size s: sizes){
            Log.e("Size:", "width:" + sizes.get(i).width + "height:" + sizes.get(i).height);

            int area = sizes.get(i).width * sizes.get(i).height;
            double delt = Math.abs(((sizes.get(i).width * 1.0) / sizes.get(i).height) - bestRatio);
            if(delt < delta){
                bestSize = sizes.get(i);
                delta = delt;
            }
            else if (delt == delta && area > largestArea) {
                    bestSize = sizes.get(i);
                    largestArea = area;
                    delta = delt;
                }

            }
        return bestSize;
    }

    private Camera.Size getBestSupportedSizeKdarks(List<Camera.Size> sizes) {
        Camera.Size bestSize = sizes.get(0);


        double bestRatio = 1.7777;
        int largestArea = bestSize.width * bestSize.height;
        int size = 0;
        double delta = Math.abs((((double)bestSize.width) / bestSize.height) - bestRatio);

        for (int i = 0; i < sizes.size(); i++) {//Camera.Size s: sizes){
            Log.e("Size:", "width:" + sizes.get(i).width + "height:" + sizes.get(i).height);

            int area = sizes.get(i).width * sizes.get(i).height;
            double delt = Math.abs(((sizes.get(i).width * 1.0) / sizes.get(i).height) - bestRatio);
            if(delt < delta){
                bestSize = sizes.get(i);
                delta = delt;
            }
            else if (delt == delta && area > largestArea) {
                bestSize = sizes.get(i);
                largestArea = area;
                delta = delt;
            }

        }
        return bestSize;
    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height)
    {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        int j = 0;
        for(int i = 0; i < sizes.size(); i++){//Camera.Size s: sizes){
            Log.e("Size:", "width:" + sizes.get(i).width + "height:"+sizes.get(i).height);
            int area = sizes.get(i).width * sizes.get(i).height;
            if(area > largestArea) {
                bestSize = sizes.get(i);
                j = i;
                largestArea = area;

            }

        }



        Log.e("Best Camera Size:", "width:" + bestSize.width + "height:" + bestSize.height);


        return sizes.get(1);
    }

    private long getDateLong(){
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

        return Long.parseLong(String.valueOf(year) + monthString + dayString);
    }


    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        //mCamera.release();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }else{
            mCamera = Camera.open();
        }
        cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCamera.startPreview();
        Log.e("Resume: ", "");
    }

    @Override
    public void onPause() {
        super.onPause();
        mCamera.startPreview();
        previewPictureImageView.setVisibility(View.GONE);
        isFreeCam = true;
        picsImageButton.setVisibility(View.VISIBLE);
        deleteImageButton.setImageResource(R.drawable.leftred);
        imageNoteEditText.setText("");
        switchCameraModeButton.setVisibility(View.VISIBLE);
        takePictureButton.setVisibility(View.VISIBLE);
        imageNoteEditText.setVisibility(View.GONE);
        checkImageButton.setVisibility(View.GONE);
        if(mCamera!= null)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }

    private class SavePhotoRunnable implements Runnable{
        @Override
        public void run() {
           android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);


        }
    }
}