package com.teacherhelper.activity

import com.example.lili.teacherhelper.R

class TeacherActivity : android.support.v7.app.AppCompatActivity(), android.support.design.widget.NavigationView.OnNavigationItemSelectedListener {
    private var client: com.teacherhelper.KniveyClient? = null
    private var mUserName: android.widget.TextView? = null
    private var mLogout: android.view.MenuItem? = null
    private var mNavigation: android.support.design.widget.NavigationView? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        client = (application as com.teacherhelper.TeacherApp).sharedClient!!
        if (!client!!.user<com.kinvey.java.User<*>>().isUserLoggedIn()) {
            val intent = android.content.Intent(this@TeacherActivity, LoginActivity::class.java)
            startActivity(intent)
            return
        }
        setContentView(com.example.lili.teacherhelper.R.layout.activity_profile)
        configureDrawer()
        configureNavView()
        title = client?.user<com.kinvey.java.User<*>>()?.username
    }

    fun configureDrawer() {
        val toolbar = findViewById(com.example.lili.teacherhelper.R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)/*
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
        val drawer = findViewById(com.example.lili.teacherhelper.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        val toggle = android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
    }

    fun configureNavView() {
        mNavigation = findViewById(com.example.lili.teacherhelper.R.id.nav_view) as android.support.design.widget.NavigationView
        mNavigation?.setNavigationItemSelectedListener(this)

        mUserName = mNavigation?.getHeaderView(0)?.findViewById(com.example.lili.teacherhelper.R.id.profile_name) as android.widget.TextView
        mUserName?.text = client!!.user<com.kinvey.java.User<*>>().getUsername()

        mLogout = mNavigation?.menu?.findItem(com.example.lili.teacherhelper.R.id.nav_logout)
        mLogout?.setOnMenuItemClickListener {
            client?.user<com.kinvey.java.User<*>>()?.logout()?.execute()
            if (!client?.user<com.kinvey.java.User<*>>()?.isUserLoggedIn!!) {
                val intent1 = android.content.Intent(this@TeacherActivity, LoginActivity::class.java)
                startActivity(intent1)
            }
            true
        }
    }
    override fun onBackPressed() {
        val drawer = findViewById(com.example.lili.teacherhelper.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        if (drawer.isDrawerOpen(android.support.v4.view.GravityCompat.START)) {
            drawer.closeDrawer(android.support.v4.view.GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == com.example.lili.teacherhelper.R.id.nav_classes) {
            // Handle the camera action
        }

        val drawer = findViewById(com.example.lili.teacherhelper.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        drawer.closeDrawer(android.support.v4.view.GravityCompat.START)
        return true
    }

}
