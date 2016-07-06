package com.kareemdarkazanli.flexlogapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by kareem_darkazanli on 1/5/16.
 */
public class FragmentPhotos extends Fragment {

    private ListView listView;
    private LibraryPhoto libraryPhoto;
    private PhotosAdapter photosAdapter;
    private RelativeLayout relativeLayout;
    private BroadcastReceiver mMessageReceiver;
    private Button countButton;
    private EditText noteEditText;
    private EditText dateEditText;
    private EditText editEditText;
    private ImageButton updateImageButton;
    private ImageButton gridImageButton;
    private ImageButton cameraBackButton;
    private ImageButton downloadButton;
    private Photo currentPhoto;
    private TextView titleTextView;
    private boolean isInEditMode;
    private boolean inListView;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private TextView yourGalleryTextView;
    private boolean isFilterOn;
    private boolean deletedWithListView;
    private boolean mHasBeenDisplayed;
    private int mCacheRefCount = 0;
    private int mDisplayRefCount = 0;
    private int count = 0;


    private static int MaxTextureSize = 2048; /* True for most devices. */

    private LruCache<String, Bitmap> mMemoryCache;
    ImageLoader imageLoader;
    Set<SoftReference<Bitmap>> mReusableBitmaps;


    @Override
    public void onResume() {
        super.onResume();
        FragmentCamera.closeKeyboard(getActivity(), noteEditText.getWindowToken());
        editEditText.setVisibility(View.GONE);
        noteEditText.setEnabled(false);
        listView.setEnabled(true);
        //updateImageButton.setVisibility(View.GONE);
        if(inListView){
            gridImageButton.setImageResource(R.drawable.grid); //
            if(isFilterOn){
                downloadButton.setVisibility(View.VISIBLE);

                countButton.setVisibility(View.VISIBLE);
                noteEditText.setVisibility(View.VISIBLE);
                dateEditText.setVisibility(View.VISIBLE);
            }
        }
        else{
            gridImageButton.setImageResource(R.drawable.menu);
            downloadButton.setVisibility(View.GONE);

            countButton.setVisibility(View.GONE);
            noteEditText.setVisibility(View.GONE);
            dateEditText.setVisibility(View.GONE);
        }
        cameraBackButton.setImageResource(R.drawable.leftwhite);
        updateImageButton.setVisibility(View.GONE); //
        if(isFilterOn){
            updateImageButton.setImageResource(R.drawable.filtericon);
        }
        else{
            updateImageButton.setImageResource(R.drawable.nofiltericon);
        }
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText("Flexlog");
        isInEditMode = false;
        if(libraryPhoto.getPhotos().size() == 0){
            //dateEditText.setTextColor(Color.BLACK);
            //noteEditText.setTextColor(Color.BLACK);
            //dateEditText.setText("Your gallery's empty.");
            //noteEditText.setText("Start taking selfies and view your progress!");
            yourGalleryTextView.setVisibility(View.VISIBLE);
            countButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.GONE);
            dateEditText.setVisibility(View.GONE);
            noteEditText.setVisibility(View.GONE);

        }
        else{
            dateEditText.setVisibility(View.VISIBLE);
            noteEditText.setVisibility(View.VISIBLE);
            dateEditText.setTextColor(Color.WHITE);
            noteEditText.setTextColor(Color.WHITE);
            countButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        FragmentCamera.closeKeyboard(getActivity(), noteEditText.getWindowToken());
        editEditText.setVisibility(View.GONE);
        noteEditText.setEnabled(false);
        listView.setEnabled(true);
        //updateImageButton.setVisibility(View.GONE);
        if(inListView){
            gridImageButton.setImageResource(R.drawable.grid); //
            if(isFilterOn){
                downloadButton.setVisibility(View.VISIBLE);
                countButton.setVisibility(View.VISIBLE);
                noteEditText.setVisibility(View.VISIBLE);
                dateEditText.setVisibility(View.VISIBLE);
            }
        }
        else{
            gridImageButton.setImageResource(R.drawable.menu);
            downloadButton.setVisibility(View.GONE);
            countButton.setVisibility(View.GONE);
            noteEditText.setVisibility(View.GONE);
            dateEditText.setVisibility(View.GONE);
        }
        cameraBackButton.setImageResource(R.drawable.leftwhite);
        updateImageButton.setVisibility(View.GONE); //

        if(isFilterOn){
            updateImageButton.setImageResource(R.drawable.filtericon);
        }
        else{
            updateImageButton.setImageResource(R.drawable.nofiltericon);
        }
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText("Flexlog");
        isInEditMode = false;
        if(libraryPhoto.getPhotos().size() == 0){
            //dateEditText.setTextColor(Color.BLACK);
            //noteEditText.setTextColor(Color.BLACK);
            //dateEditText.setText("Your gallery's empty.");
            //noteEditText.setText("Start taking selfies and view your progress!");
            yourGalleryTextView.setVisibility(View.VISIBLE);
            countButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.GONE);
            dateEditText.setVisibility(View.GONE);
            noteEditText.setVisibility(View.GONE);

        }
        else{
            dateEditText.setVisibility(View.VISIBLE);
            noteEditText.setVisibility(View.VISIBLE);
            dateEditText.setTextColor(Color.WHITE);
            noteEditText.setTextColor(Color.WHITE);
            countButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listview_photos, container, false);
        setHasOptionsMenu(false);

        downloadButton = (ImageButton)v.findViewById(R.id.downloadButton);
        deletedWithListView = true;
        imageLoader = ImageLoader.getInstance();
        yourGalleryTextView = (TextView) v.findViewById(R.id.yourGalleryTextView);
        yourGalleryTextView.setVisibility(View.GONE);

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory/4;


        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };



        try{
            libraryPhoto = LibraryPhoto.get();
            Log.e("Photo Number:", libraryPhoto.getPhotos().size() + "");

        }catch (Exception e){

        }
        inListView = true;
        isInEditMode = false;
        gridView = (GridView)v.findViewById(R.id.photoGridView);
        updateImageButton = (ImageButton)v.findViewById(R.id.imageButtonUpdate);
        gridImageButton = (ImageButton)v.findViewById(R.id.gridImageButton);
        gridImageButton.setImageResource(R.drawable.grid); //
        cameraBackButton = (ImageButton)v.findViewById(R.id.cameraBackButton);
        listView = (ListView)v.findViewById(R.id.photosListView);
        relativeLayout = (RelativeLayout)v.findViewById(R.id.photoRelativeLayout);
        countButton = (Button)v.findViewById(R.id.countButton);
        noteEditText = (EditText)v.findViewById(R.id.photoNoteEditText);
        noteEditText.setEnabled(false);
        dateEditText = (EditText)v.findViewById(R.id.photoDateEditText);
        dateEditText.setEnabled(false);
        editEditText = (EditText)v.findViewById(R.id.photoEditEditText);
        editEditText.setVisibility(View.GONE);
        titleTextView = (TextView)v.findViewById(R.id.historyTextView);
        //countButton.setHeight(countButton.getWidth());
        gridView.setVisibility(View.GONE);
        updateImageButton.setImageResource(R.drawable.filtericon);
        updateImageButton.setVisibility(View.GONE); //

        isFilterOn = true;


        photosAdapter = new PhotosAdapter(libraryPhoto.getPhotos());
        gridViewAdapter = new GridViewAdapter(libraryPhoto.getPhotos());
        gridView.setAdapter(gridViewAdapter);

        if(libraryPhoto.getPhotos().size() == 0){
            //dateEditText.setTextColor(Color.BLACK);
            //noteEditText.setTextColor(Color.BLACK);
            //dateEditText.setText("Your gallery's empty.");
            //noteEditText.setText("Start taking selfies and view your progress!");
            yourGalleryTextView.setVisibility(View.VISIBLE);
            countButton.setVisibility(View.GONE);
            downloadButton.setVisibility(View.GONE);
            dateEditText.setVisibility(View.GONE);
            noteEditText.setVisibility(View.GONE);

        }
        else{
            dateEditText.setVisibility(View.VISIBLE);
            noteEditText.setVisibility(View.VISIBLE);
            dateEditText.setTextColor(Color.WHITE);
            noteEditText.setTextColor(Color.WHITE);
            countButton.setVisibility(View.VISIBLE);
            downloadButton.setVisibility(View.VISIBLE);

        }


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save saveFile = new Save();
                saveFile.SaveImage(getActivity(),LibraryPhoto.get().getPhotos().get(count - 1), PictureUtils.getScaledDrawableBitmap(getActivity(), getActivity().getFileStreamPath(LibraryPhoto.get().getPhotos().get(count - 1).getFilename()).getAbsolutePath()));
            }
        });
        //photoAdapter = new AdapterPhoto(libraryPhoto.getPhotos(), R.id.photosListView, getActivity(), getActivity());
        listView.setAdapter(photosAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String divisor = "";
                if (visibleItemCount > 0) {
                    Log.e("Note: ", libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition()).getNote());

                    noteEditText.setText(libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition()).getNote());
                    dateEditText.setText(getDate(String.valueOf(libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition()).getDate())));//.substring(4, 6) + "/" + String.valueOf(libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition()).getDate()).substring(6, 8) + "/" + String.valueOf(libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition()).getDate()).substring(0, 4));
                    count = (listView.getFirstVisiblePosition() + 1);
                    divisor = "" + count + "/" + (libraryPhoto.getPhotos().size());
                    countButton.setText(divisor);
                    //}
               /* else{
                    noteEditText.setText(libraryPhoto.getPhotos().get(listView.getFirstVisiblePosition() + 1).getNote());
                    count = (listView.getFirstVisiblePosition() + 2);
                    divisor = "" + count + "/" + (libraryPhoto.getPhotos().size());
                    countButton.setText(divisor);                }*/

                    if (divisor.length() < 6) {
                        countButton.setTextSize(14);
                    } else if (divisor.length() < 7) {
                        countButton.setTextSize(12);
                    } else if (divisor.length() < 8) {
                        countButton.setTextSize(10);
                    } else {
                        countButton.setTextSize(8);
                    }

                }
            }
        });

        gridImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInEditMode) {

                    titleTextView.setText("Flexlog");
                    libraryPhoto.getPhotos().remove(currentPhoto);
                    photosAdapter.notifyDataSetChanged();
                    gridViewAdapter.notifyDataSetChanged();
                    updateImageButton.setImageResource(R.drawable.filtericon);
                    updateImageButton.setVisibility(View.GONE); //
                    cameraBackButton.setImageResource(R.drawable.leftwhite);
                    titleTextView.setVisibility(View.VISIBLE);
                    if(deletedWithListView)
                    gridImageButton.setImageResource(R.drawable.grid); //
                    else
                    gridImageButton.setImageResource(R.drawable.menu);
                    listView.setEnabled(true);
                    noteEditText.setEnabled(false);
                    isInEditMode = false;
                    FragmentCamera.closeKeyboard(getActivity(), noteEditText.getWindowToken());
                    if(libraryPhoto.getPhotos().size() == 0){
                        //dateEditText.setText("Your gallery's empty.");
                        //noteEditText.setText("Start taking selfies and view your progress!");
                        countButton.setVisibility(View.GONE);
                        downloadButton.setVisibility(View.GONE);

                        //dateEditText.setTextColor(Color.BLACK);
                        //noteEditText.setTextColor(Color.BLACK);
                        dateEditText.setVisibility(View.GONE);
                        noteEditText.setVisibility(View.GONE);
                        yourGalleryTextView.setVisibility(View.VISIBLE);

                    }
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    try{
                        LibraryPhoto.get().savePhotos();

                    }catch (Exception e){

                    }

                }
                else if(inListView){
                    listView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    dateEditText.setVisibility(View.GONE);
                    noteEditText.setVisibility(View.GONE);
                    countButton.setVisibility(View.GONE);
                    downloadButton.setVisibility(View.GONE);

                    gridImageButton.setImageResource(R.drawable.menu);
                    updateImageButton.setVisibility(View.GONE);
                    inListView = false;

                }
                else{
                    updateImageButton.setVisibility(View.VISIBLE);
                    updateImageButton.setVisibility(View.GONE); //
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    if(isFilterOn){

                        if(libraryPhoto.getPhotos().size() > 0){
                            dateEditText.setVisibility(View.VISIBLE);
                            noteEditText.setVisibility(View.VISIBLE);
                            countButton.setVisibility(View.VISIBLE);
                            downloadButton.setVisibility(View.VISIBLE);

                        }

                    }else{
                        dateEditText.setVisibility(View.GONE);
                        noteEditText.setVisibility(View.GONE);
                        countButton.setVisibility(View.GONE);
                        downloadButton.setVisibility(View.GONE);

                    }
                    gridImageButton.setImageResource(R.drawable.grid); //
                    inListView = true;
                }
            }
        });

        cameraBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInEditMode){

                    FragmentCamera.closeKeyboard(getActivity(), noteEditText.getWindowToken());
                    editEditText.setVisibility(View.GONE);
                    noteEditText.setEnabled(false);
                    listView.setEnabled(true);
                    //updateImageButton.setVisibility(View.GONE);
                    if(inListView){
                        gridImageButton.setImageResource(R.drawable.grid); //
                    }
                    else{
                        gridImageButton.setImageResource(R.drawable.menu);
                    }
                    cameraBackButton.setImageResource(R.drawable.leftwhite);
                    updateImageButton.setVisibility(View.GONE); //

                    if(isFilterOn){
                        updateImageButton.setImageResource(R.drawable.filtericon);
                    }
                    else{
                        updateImageButton.setImageResource(R.drawable.nofiltericon);
                    }
                    titleTextView.setVisibility(View.VISIBLE);
                    titleTextView.setText("Flexlog");
                    isInEditMode = false;
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
                else{
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "camera");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                }
            }
        });

        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInEditMode){

                    titleTextView.setText("Flexlog");
                    currentPhoto.setNote(noteEditText.getText().toString().trim());
                    editEditText.setVisibility(View.GONE);
                    //noteEditText.setVisibility(View.VISIBLE);
                    noteEditText.setEnabled(false);
                    listView.setEnabled(true);
                    updateImageButton.setVisibility(View.GONE); //

                    if(isFilterOn){
                        updateImageButton.setImageResource(R.drawable.filtericon);
                    }
                    else{
                        updateImageButton.setImageResource(R.drawable.nofiltericon);
                    }
                    gridImageButton.setImageResource(R.drawable.grid); //
                    cameraBackButton.setImageResource(R.drawable.leftwhite);
                    titleTextView.setVisibility(View.VISIBLE);
                    isInEditMode = false;
                    Intent intent = new Intent("custom-event-name");
                    // You can also include some extra data.
                    intent.putExtra("message", "release");
                    //intent.putExtra("message", "release");
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    FragmentCamera.closeKeyboard(getActivity(), noteEditText.getWindowToken());

                    try{
                        LibraryPhoto.get().savePhotos();

                    }catch (Exception e){

                    }
                }else if(isFilterOn){
                    updateImageButton.setVisibility(View.GONE); //
                    updateImageButton.setImageResource(R.drawable.nofiltericon);
                    noteEditText.setVisibility(View.GONE);
                    dateEditText.setVisibility(View.GONE);
                    countButton.setVisibility(View.GONE);
                    downloadButton.setVisibility(View.GONE);

                    isFilterOn = false;
                }
                else{
                    updateImageButton.setImageResource(R.drawable.filtericon);
                    updateImageButton.setVisibility(View.GONE); //

                    if(libraryPhoto.getPhotos().size() > 0){
                        noteEditText.setVisibility(View.VISIBLE);
                        dateEditText.setVisibility(View.VISIBLE);
                        countButton.setVisibility(View.VISIBLE);
                        downloadButton.setVisibility(View.VISIBLE);

                    }
                    isFilterOn = true;
                }




            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deletedWithListView = true;
                listView.setSelection(position);
                listView.setEnabled(false);
                isInEditMode = true;
                titleTextView.setText("Edit");
                //dateEditText.setVisibility(View.GONE);
                //noteEditText.setVisibility(View.GONE);
                //editEditText.setVisibility(View.VISIBLE);
                //cameraBackButton.setVisibility(View.GONE);
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                } catch (Exception e) {

                }
                noteEditText.setEnabled(true);
                noteEditText.requestFocus();
                updateImageButton.setVisibility(View.VISIBLE);

                cameraBackButton.setImageResource(R.drawable.leftv2);
                updateImageButton.setImageResource(R.drawable.checkmarkwhite);
                gridImageButton.setImageResource(R.drawable.deletewhite);
                currentPhoto = libraryPhoto.getPhotos().get(position);
                editEditText.setText(currentPhoto.getNote());
                Intent intent = new Intent("custom-event-name");
                // You can also include some extra data.
                intent.putExtra("message", "freeze");
                //intent.putExtra("message", "release");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                //titleTextView.setVisibility(View.GONE);
                //setAllowEnterTransitionOverlap(true);
                //noteEditText.setEnabled(true);

                return true;
            }
        });
//        Log.e("Filename: ", (libraryPhoto.getPhotos().get(0).getFilename())
      //  + "");


        /*listView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
                final ImageView imageView = (ImageView) view.findViewById(R.id.photoImageListView);
                imageView.setImageBitmap(null);
            }
        });

        gridView.setRecyclerListener(new AbsListView.RecyclerListener() {
            @Override
            public void onMovedToScrapHeap(View view) {
                // Release strong reference when a view is recycled
                final ImageView imageView = (ImageView) view.findViewById(R.id.gallery_item_imageView);
                imageView.setImageBitmap(null);
            }
        });*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!isInEditMode) {
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setSelection(position);
                    if (isFilterOn) {
                        dateEditText.setVisibility(View.VISIBLE);
                        noteEditText.setVisibility(View.VISIBLE);
                        countButton.setVisibility(View.VISIBLE);
                        downloadButton.setVisibility(View.VISIBLE);

                    }
                    gridImageButton.setImageResource(R.drawable.grid); //
                    updateImageButton.setVisibility(View.VISIBLE);
                    updateImageButton.setVisibility(View.GONE); //

                    inListView = true;
                }

            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletedWithListView = false;

                currentPhoto = libraryPhoto.getPhotos().get(position);
                gridImageButton.setImageResource(R.drawable.deletewhite);
                isInEditMode = true;
                titleTextView.setText("Edit");
                cameraBackButton.setImageResource(R.drawable.leftv2);
                Intent intent = new Intent("custom-event-name");
                // You can also include some extra data.
                intent.putExtra("message", "freeze");
                //intent.putExtra("message", "release");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                return true;
            }
        });

        for(int i = 0; i < libraryPhoto.getPhotos().size(); i++)
        {
            BitmapDrawable image;

            String path = getActivity().getFileStreamPath(libraryPhoto.getPhotos().get(i).getFilename()).getAbsolutePath();
            //loadBitmap(Uri.parse(path), );
            if(libraryPhoto.getPhotos().get(i).getIsSelfie()){
                //image = PictureUtils.getScaledDrawableSelfie(getActivity(), bitmapdata);
                image = PictureUtils.getScaledDrawableIsSelfie(getActivity(),path);

            }else{
                //image = PictureUtils.getScaledDrawable(getActivity(), bitmapdata);
                image = PictureUtils.getScaledDrawable(getActivity(),path);

            }
            //image.getBitmap().recycle();
            libraryPhoto.getPhotos().get(i).setImage(image);


        }


        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent
                String message = intent.getStringExtra("message");
                if(message.equals("newphoto")){

                    yourGalleryTextView.setVisibility(View.GONE);
                    dateEditText.setVisibility(View.VISIBLE);
                    noteEditText.setVisibility(View.VISIBLE);
                    dateEditText.setTextColor(Color.WHITE);
                    noteEditText.setTextColor(Color.WHITE);
                    countButton.setVisibility(View.VISIBLE);
                    downloadButton.setVisibility(View.VISIBLE);
                    photosAdapter.notifyDataSetChanged();
                    gridViewAdapter.notifyDataSetChanged();

                }
                Log.d("receiver", "Got message: " + message);
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));
        //photosAdapter.notifyDataSetChanged();


        return v;
    }

    private void setPic(ImageView mImageView, Photo p) {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(p.getData(), 0, p.getData().length, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeByteArray(p.getData(), 0, p.getData().length, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
    private class PhotosAdapter extends ArrayAdapter<Photo> {

        //ArrayList<Drawable> photos = new ArrayList<>();
        public PhotosAdapter(ArrayList<Photo> u){
            super(getActivity(), 0, u);
            /*for (int i = 0; i < u.size(); i++){
                photos.add(PictureUtils.getScaledDrawableIsSelfie(getActivity(), getActivity().getFileStreamPath(getItem(i).getFilename()).getAbsolutePath()));
            }*/
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.photo, null);
            }

            Photo u = getItem(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.photoImageListView);
            imageView.setMinimumWidth(relativeLayout.getWidth());
            imageView.setMinimumHeight(relativeLayout.getHeight());
            imageLoader.displayImage(Uri.parse("file://" + getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath()).toString(), imageView);
            //Uri.parse("file://" + getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath()).toString()
            Log.e("FragmentPhotos:", getItem(position).getFilename().toString());
            ///imageView.setImageDrawable(photos.get(position));
            //loadBitmap(getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath(), imageView, u.getIsSelfie());





            return convertView;
        }
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }




    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void loadBitmap(String path, ImageView imageView, boolean isSelfie) {
        final String imageKey = path;

        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, isSelfie);
            task.execute(path);
        }
    }

    /*public void loadBitmap(String path, ImageView imageView, boolean isSelfie) {

        if (cancelPotentialWork(path, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView, isSelfie);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(getResources(), task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(path);
        }
    }*/


    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private boolean isSelfie;
        String path;

        public BitmapWorkerTask(ImageView imageView, boolean s) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
            isSelfie = s;
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPurgeable = true;
            opt.inPreferQualityOverSpeed = false;
            opt.inSampleSize = 0;

            path = params[0];
            Bitmap bitmap = null;
            if(isCancelled()) {
                return bitmap;
            }

            opt.inJustDecodeBounds = true;
            do {
                opt.inSampleSize++;
                //BitmapFactory.decodeByteArray(params[0],0, params[0].length, opt);
                BitmapFactory.decodeFile(params[0],opt);
            } while(opt.outHeight > MaxTextureSize || opt.outWidth > MaxTextureSize);
            opt.inJustDecodeBounds = false;

            //bitmap = BitmapFactory.decodeByteArray(params[0],0, params[0].length, opt);
            bitmap = BitmapFactory.decodeFile(params[0],opt);
            Bitmap rotatedBitmap;
            if(isSelfie){
                Matrix matrix = new Matrix();
                matrix.postScale(-1.0f, 1.0f);
                matrix.postRotate(90);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            else{
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
            }
            addBitmapToMemoryCache(String.valueOf(params[0]), rotatedBitmap);
            return rotatedBitmap;
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


    private class GridViewAdapter extends ArrayAdapter<Photo>{

        //ArrayList<Drawable> photos = new ArrayList<>();

        public GridViewAdapter(ArrayList<Photo> p){
           super(getActivity(), 0, p);
           /*for (int i = 0; i < p.size(); i++){
               photos.add(PictureUtils.getScaledDrawableIsSelfie(getActivity(), getActivity().getFileStreamPath(getItem(i).getFilename()).getAbsolutePath()));
           }*/
       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.gallery_item, parent, false);
            }
            ImageView imageView = (ImageView)convertView.findViewById(R.id.gallery_item_imageView);

            Photo u = getItem(position);

            imageLoader.displayImage(Uri.parse("file://" + getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath()).toString(), imageView);

            //loadBitmap(getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath(), imageView, u.getIsSelfie());
            //imageView.setImageDrawable(PictureUtils.getScaledDrawableIsSelfie(getActivity(), getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath()));
            //imageView.setImageDrawable(photos.get(position));
            //imageView.setImageDrawable(PictureUtils.getScaledDrawable(getActivity(), getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath()));
            /*imageView.setImageBitmap(
                    decodeSampledBitmapFromResource(getItem(position),getActivity().getFileStreamPath(getItem(position).getFilename()).getAbsolutePath(), 200, 200));*/
            return convertView;
        }

    }

    public static Bitmap decodeSampledBitmapFromResource(Photo u, String path,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        //BitmapFactory.decodeResource(res, resId, options);


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Bitmap rotatedBitmap;
        if(u.getIsSelfie()){
            Matrix matrix = new Matrix();
            matrix.postScale(-1.0f, 1.0f);
            matrix.postRotate(90);
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        else{
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
        }
        return rotatedBitmap;

    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private String getDate(String d){
        String date = "";
        if(d.substring(4,6).equals("00")){
            date += "Jan";
        }
        else if(d.substring(4,6).equals("01")){
            date += "Feb";
        }
        else if(d.substring(4,6).equals("02")){
            date += "Mar";
        }
        else if(d.substring(4,6).equals("03")){
            date += "April";
        }
        else if(d.substring(4,6).equals("04")){
            date += "May";
        }
        else if(d.substring(4,6).equals("05")){
            date += "June";
        }
        else if(d.substring(4,6).equals("06")){
            date += "July";
        }
        else if(d.substring(4,6).equals("07")){
            date += "Aug";
        }
        else if(d.substring(4,6).equals("08")){
            date += "Sep";
        }
        else if(d.substring(4,6).equals("09")){
            date += "Oct";
        }
        else if(d.substring(4,6).equals("10")){
            date += "Nov";
        }
        else if(d.substring(4,6).equals("11")){
            date += "Dec";
        }

        date+= " " + d.substring(6,8) + " " + d.substring(0,4);

        //if()
        return date;
    }

}
