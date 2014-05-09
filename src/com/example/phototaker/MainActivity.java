package com.example.phototaker;

import android.app.Activity;
// import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
// import android.os.Build;
import android.provider.MediaStore;

public class MainActivity extends Activity {

    private Bitmap photoImg = null;
    private final static String PHOTOIMG_KEY = "photoImg";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }
        else
            photoImg = savedInstanceState.getParcelable(PHOTOIMG_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPhoto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }

    }

    private final static int MY_REQUEST_FOR_PHOTO = 1234;
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PHOTOIMG_KEY, photoImg);
    }

    public void takePhoto(View view) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, MY_REQUEST_FOR_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case MY_REQUEST_FOR_PHOTO:
            if (resultCode == RESULT_OK) {
                photoImg = (Bitmap)data.getExtras().get("data");
                showPhoto();
            }
            break;
        }
    }

    private void showPhoto() {
        if (photoImg != null) {
            ImageView photoView = (ImageView) findViewById(R.id.photo_view);
            photoView.setImageBitmap(photoImg);
        }
    }
}
