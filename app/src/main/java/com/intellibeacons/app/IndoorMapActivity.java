package com.intellibeacons.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import uk.co.senab.photoview.PhotoView;


public class IndoorMapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Log.i("Map ID",  intent.getStringExtra("mapId"));
        PhotoView photoView = new PhotoView(this);
        photoView.setMaximumScale(16);
        setContentView(photoView);

        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setTitle("Loading...");
        dlg.setIndeterminate(false);
        dlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dlg.show();

        // this is going to load a 30mb download...
        Ion.with(this)
                .load("https://raw2.github.com/koush/ion/master/ion-sample/telescope.jpg")
                .progressDialog(dlg)
                .setLogging("DeepZoom", Log.VERBOSE)
                .withBitmap()
                .deepZoom()
                .intoImageView(photoView)
                .setCallback(new FutureCallback<ImageView>() {
                    @Override
                    public void onCompleted(Exception e, ImageView result) {
                        dlg.cancel();
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.indoor_map, menu);
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
}
