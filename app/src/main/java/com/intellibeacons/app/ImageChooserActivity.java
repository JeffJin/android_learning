package com.intellibeacons.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ImageChooserActivity extends Activity
        implements OnImageSelectionChangeListener, ImageFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chooser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_chooser, menu);
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

    @Override
    public void OnImageSelectionChanged(int index) {
        Log.d("ImageChooser", "p" + (index + 1) + " image is selected");
        FragmentManager fm = getFragmentManager();
        ImageFragment imgFragment =
                (ImageFragment) fm.findFragmentById(R.id.imageFragment);
        imgFragment.setImageSource(index);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("FragmentInteraction", uri.toString());
    }
}
