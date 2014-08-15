package com.intellibeacons.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btDeepZoom = (Button) findViewById(R.id.btDeepZoom);
        final Intent mapIntent = new Intent(this, IndoorMapActivity.class);
        btDeepZoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mapIntent.putExtra("mapId", "12345");
                startActivity(mapIntent);
            }
        });

        final Button imageChooserBt = (Button) findViewById(R.id.btImageChooser);
        final Intent chooserIntent = new Intent(this, ImageChooserActivity.class);
        imageChooserBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(chooserIntent);
            }
        });

        final Button imageSliderBt = (Button) findViewById(R.id.btImageSlider);
        final Intent sliderIntent = new Intent(this, ImageSliderActivity.class);
        imageSliderBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(sliderIntent);
            }
        });


        findViewById(R.id.btnWriteToFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnWriteToFileAsyncTaskOnClick((Button) view);
            }
        });

        findViewById(R.id.btnReadToFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnReadFileAsyncOnClick((Button) view);
            }
        });

        StrictMode.enableDefaults();
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
        if (id == R.id.action_dial) {
            makePhoneCall();
            return true;
        }
        else if (id == R.id.action_browse_web) {
            showBlogSite();
            return true;
        }
        else if (id == R.id.action_browse_disk) {
            browseDisk();
            return true;
        }
        else if (id == R.id.action_indoor_map) {
            openInddorMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makePhoneCall(){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:647.985.4410"));
        startActivity(callIntent);
    }

    private void showBlogSite(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://jeffjin.com"));
        startActivity(intent);
    }

    private void browseDisk(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivity(intent);
    }

    private void openInddorMap(){
        Intent intent = new Intent("com.intellibeacons.action.INDOORMAP");
        intent.putExtra("mapId", "12345");
        startActivity(intent);
    }


    private void btnWriteToFileOnClick(Button view) {

        Toast bread = Toast.makeText(getApplicationContext(), getApplicationContext().getFilesDir().toString(), Toast.LENGTH_LONG);
        bread.show();
        FileOutputStream outStream = openOutStream("testout.dat");
        for (int i = 0; i < 22; i++) {
            simpleWrite(outStream, "Hello World");
        }
        closeOutStream(outStream);

    }

    private void btnReadFileAsyncOnClick(Button view) {

        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarMain);
        initializeProgressBar(pb);
        displayStartedMessage();

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String txt = "";
                for (int i = 0; i < 22; i++) {
                    txt += simpleRead(strings[0]);
                    publishProgress(i);
                }
                return txt;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                pb.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String txt) {
                displayCompletionMessage();
                cleanupProgressBar(pb);
                Log.i("ASYNC RESULT", txt);
            }
        }.execute("testout.dat");
    }


    private void btnWriteToFileAsyncTaskOnClick(Button view) {
        String messageToWrite = "Message to write to file";

        final ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarMain);
        initializeProgressBar(pb);
        displayStartedMessage();

        new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String outputValue = strings[0];
                FileOutputStream outStream = openOutStream("testout.dat");
                for (int i = 0; i < 22; i++) {
                    slowWrite(outStream, outputValue);
                    publishProgress(i);
                }

                closeOutStream(outStream);
                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                pb.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Void txt) {
                displayCompletionMessage();
                cleanupProgressBar(pb);
            }
        }.execute(messageToWrite);
    }


    protected FileOutputStream openOutStream(String filename) {
        FileOutputStream outStream = null;
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, filename);
        try {
            outStream = new FileOutputStream(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outStream;
    }

    protected void closeOutStream(FileOutputStream outStream) {
        try {
            if (outStream != null)
                outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String simpleRead(String fileName){

        StringBuilder text = new StringBuilder();
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, fileName);
            System.out.println("exception");

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                System.out.println("text : " + text + " : end");
                text.append('\n');
            }
            br.close();
            Thread.sleep(400, 0);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("exception occurred");

        }
        return text.toString();
//
//        TextView tv = (TextView)findViewById(R.id.amount);
//
//        tv.setText(text.toString()); ////Set the text to text view.
    }


    protected void simpleWrite(FileOutputStream outStream, String buffer) {
        try {
            outStream.write(buffer.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void slowWrite(FileOutputStream outStream, String buffer) {
        try {
            outStream.write(buffer.getBytes());
            Thread.sleep(1000, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void initializeProgressBar(ProgressBar pb) {
        pb.setMax(22);
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
    }

    protected void cleanupProgressBar(ProgressBar pb) {
        pb.setVisibility(View.INVISIBLE);
    }

    protected void displayStartedMessage() {
        Toast.makeText(this, "File operation started", Toast.LENGTH_SHORT).show();
    }

    protected void displayCompletionMessage() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(MainActivity.this);
        bldr.setTitle("Write to File")
                .setMessage("File operation complete")
                .setIcon(R.drawable.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

}
