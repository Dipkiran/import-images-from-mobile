package com.example.dip_24.enterprenuership;//package com.example.dip_24.enterprenuership;
//
//import android.Manifest;
//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Intent;
//import android.content.Loader;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.os.Build;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//public class MainActivity extends AppCompatActivity
//        implements LoaderManager.LoaderCallbacks<Cursor>{
//
//
//
//
////    ImageView mImageView;
////    private static final int REQUEST_OPEN_RESULT_CODE = 0;
//    private final static  int READ_EXTERNAL_STORAGE_PERMISSION_RESULT = 0;
//    private final static  int MEDIASTORE_LOADER_ID = 0;
//    private RecyclerView mThumbnailRecyclerView;
//    private MediaStoreAdapter mMediaStoreAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mThumbnailRecyclerView = (RecyclerView) findViewById(R.id.thumbnailRecyclerView);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
//        mThumbnailRecyclerView.setLayoutManager(gridLayoutManager);
//        mMediaStoreAdapter = new MediaStoreAdapter(this);
//        mThumbnailRecyclerView.setAdapter(mMediaStoreAdapter);
//
//        CheckExternalStoragePermission();
//
////        mImageView = (ImageView) findViewById(R.id.imageView);
////
////        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
////        intent.addCategory(Intent.CATEGORY_OPENABLE);
////        intent.setType("image/*");
////        startActivityForResult(intent, REQUEST_OPEN_RESULT_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case READ_EXTERNAL_STORAGE_PERMISSION_RESULT:
//                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    //call cursor loader
////                    Toast.makeText(this,"you now have access",Toast.LENGTH_SHORT).show();
////                    getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null, this);
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//    }
//
//    private void CheckExternalStoragePermission(){
////        check if version is greater then Marshmellow or not
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//
//            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
//                    PackageManager.PERMISSION_GRANTED){
////                getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null);
//
//            } else{
//
//                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
//                    Toast.makeText(this,"App needs to view thumbnails", Toast.LENGTH_SHORT).show();
//
//                }
//                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
//                        READ_EXTERNAL_STORAGE_PERMISSION_RESULT);
//            }
//        } else {
////            getSupportLoaderManager().initLoader(MEDIASTORE_LOADER_ID, null, this);
//        }
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        String[] projection = {
//                MediaStore.Files.FileColumns._ID,
//                MediaStore.Files.FileColumns.DATE_ADDED,
//                MediaStore.Files.FileColumns.MEDIA_TYPE
//
//        };
//
//        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
//                +  MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
//                + " OR "
//                +  MediaStore.Files.FileColumns.MEDIA_TYPE + "="
//                +  MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
//        return new CursorLoader(
//                this,
//                MediaStore.Files.getContentUri("external"),
//                projection,
//                selection,
//                null,
//                MediaStore.Files.FileColumns.DATE_ADDED + "DESC"
//        );
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mMediaStoreAdapter.changeCursor(data);
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        mMediaStoreAdapter.changeCursor(null);
//    }
//}

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * The Class GallarySample.
 */
public class MainActivity extends Activity {

    /** The images. */
    private ArrayList<String> images;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gallery = (GridView) findViewById(R.id.galleryGridView);

        gallery.setAdapter(new ImageAdapter(this));

        gallery.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                if (null != images && !images.isEmpty())
                    Toast.makeText(
                            getApplicationContext(),
                            "position " + position + " " + images.get(position),
                            300).show();
                ;

            }
        });

    }

    /**
     * The Class ImageAdapter.
     */
    private class ImageAdapter extends BaseAdapter {

        /** The context. */
        private Activity context;

        /**
         * Instantiates a new image adapter.
         *
         * @param localContext
         *            the local context
         */
        public ImageAdapter(Activity localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }

        public int getCount() {
            return images.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(270, 270));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    .placeholder(R.drawable.ic_launcher).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity
         *            the activity
         * @return ArrayList with images Path
         */
        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }
    }
}