package com.teacherhelper

import android.app.Application
import android.support.multidex.MultiDexApplication

import com.kinvey.android.Client

/**
 * Created by Prots on 3/15/16.
 */
class TeacherApp : Application() {

    var sharedClient: Client?  = null
        internal set

    override fun onCreate() {
        super.onCreate()
        sharedClient = Client.Builder(this).build()
    }
}
