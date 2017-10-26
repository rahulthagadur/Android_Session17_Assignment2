package com.example.thagadur.android_session17_assignment2;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean mBounded;
    BoundService mServer;
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        button = (Button) findViewById(R.id.button);
        mServer = new BoundService();
        /*
        *  onClick of Button it calls the function getCurrentTime() method of the service and assigns the resulting string to the TextView
        * */
        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                text.setText(mServer.currentTime());
            }
        });
    }

    //creation of an intent and a call to the bindService() method
    @Override
    protected void onStart() {
        super.onStart();
        Intent mIntent = new Intent(this, BoundService.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {

        /*
        * onServiceConnected() method will be called when the client binds successfully to the service
        *  method is passed as an argument the IBinder object returned by the onBind() method of the service.
        * */
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            mBounded = false;
            mServer = null;
        }

        //onServiceDisconnected() method is called when the connection ends and simply sets the Boolean flag to false.
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MainActivity.this, "Service is connected", Toast.LENGTH_SHORT).show();
            mBounded = true;
            BoundService.LocalBinder mLocalBinder = (BoundService.LocalBinder) service;
            mServer = mLocalBinder.getServerInstance();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }
}

