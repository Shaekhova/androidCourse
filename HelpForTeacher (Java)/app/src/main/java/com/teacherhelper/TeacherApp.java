package com.teacherhelper;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.kinvey.android.Client;

/**
 * Created by Prots on 3/15/16.
 */
public class TeacherApp extends Application {

    Client sharedClient;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedClient = new Client.Builder(this).build();
    }

    public Client getSharedClient(){
        return sharedClient;
    }
}
