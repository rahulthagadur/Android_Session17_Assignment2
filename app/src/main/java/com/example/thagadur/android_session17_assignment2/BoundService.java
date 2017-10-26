package com.example.thagadur.android_session17_assignment2;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.app.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thagadur on 10/25/2017.
 */

public class BoundService extends Service {

    IBinder mBinder = new LocalBinder();

    //onBind() method needs  modified to return a reference to the myBinder object
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //    Binding the service to Bounded binder
    public class LocalBinder extends Binder {
        public BoundService getServerInstance() {
            return BoundService.this;
        }
    }

    //  this method is called to return current time when called by any clients that bind to the service
    public String currentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }
}
