package com.example.hp.tpdhars;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HP on 7/6/2016.
 */

public class voicea extends Activity {

    Button speak;
    ListView options;
    ArrayList<String> results;
    private static final String TAG = "LocalBroadcastDemo";
    private IntentFilter receiveFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice);

        //doctory endsl...
        speak = (Button) findViewById(R.id.bSpeak);
        options = (ListView) findViewById(R.id.lvOptions);


        speak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // This are the intents needed to start the Voice recognizer
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                //   RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "EN-UK");
                i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1); // number of maximum results..
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");

                startActivityForResult(i, 1010);
            }
        });

        // retrieves data from the previous state. This is incase the phones
        // orientation changes
        if (savedInstanceState != null) {
            results = savedInstanceState.getStringArrayList("results");


            if (results != null) {
                options.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, results));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        // retrieves data from the VoiceRecognizer
        if (requestCode == 1010 && resultCode == RESULT_OK) {
            results = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (results.contains("close")) {
                finish();
            }//extra testing...
            options.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, (results)));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // This should save all the data so that when the phone changes
        // orientation the data is saved
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("results", results);
    }

    private BroadcastReceiver handler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "BroadcastReceiver() {...}.onReceive()");
            Toast.makeText(voicea.this,
                    "Message received", Toast.LENGTH_LONG).show();
        }
    };

    /**
     * Create and dispatch an Intent via the LocalBroadcastManager.
     * Called from a Button with android:onClick="send"
     */
    public void send(View v) {
        Log.d(TAG, "voicea.send()");
        Intent sendableIntent = new Intent(getClass().getName());
        LocalBroadcastManager.getInstance(this).
                sendBroadcast(sendableIntent);
    }
}