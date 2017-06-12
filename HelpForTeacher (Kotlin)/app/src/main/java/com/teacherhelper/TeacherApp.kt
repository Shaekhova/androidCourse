package com.teacherhelper

import android.app.Application
import android.support.multidex.MultiDexApplication

import com.kinvey.android.Client

class TeacherApp : Application() {

    var sharedClient: KniveyClient?  = null
        internal set

    override fun onCreate() {
        super.onCreate()
        sharedClient = KniveyClient(Client.Builder(this).build())
    }
}
