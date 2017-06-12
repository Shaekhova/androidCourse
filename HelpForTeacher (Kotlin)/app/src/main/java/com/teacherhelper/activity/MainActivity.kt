package com.teacherhelper.activity

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : android.support.v7.app.AppCompatActivity() {
    private var client: com.teacherhelper.KniveyClient? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(com.example.lili.teacherhelper.R.layout.activity_main)
        client = (application as com.teacherhelper.TeacherApp).sharedClient!!
        val handler = android.os.Handler()
        handler.postDelayed({
            if (client?.user<com.kinvey.java.User<*>>()?.isUserLoggedIn!!) {
                val intent = android.content.Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            } else {
                val intent = android.content.Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }, 1000)
    }
}
