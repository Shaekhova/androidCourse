package com.teacherhelper

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.example.lili.teacherhelper.R
import com.kinvey.android.Client
import com.kinvey.java.User

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {
    private var client: Client? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        client = (application as TeacherApp).sharedClient!!
        val handler = Handler()
        handler.postDelayed({
            if (client!!.user<User<*>>().isUserLoggedIn()) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }, 1000)
    }
}
