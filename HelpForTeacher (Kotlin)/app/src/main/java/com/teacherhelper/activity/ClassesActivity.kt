package com.teacherhelper.activity

import com.example.lili.teacherhelper.R
import com.teacherhelper.model.ClassEntity

class ClassesActivity : android.support.v7.app.AppCompatActivity(), android.support.design.widget.NavigationView.OnNavigationItemSelectedListener {
    private val act = this
    private val CLASSES_COL = "classes"
    private val CLASSES_TEACHER_ID_COL = "teacher_id"
    private var client: com.teacherhelper.KniveyClient? = null
    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            com.example.lili.teacherhelper.R.id.nav_logout -> {
                client?.user<com.kinvey.java.User<*>>()?.logout()?.execute()
                if (!client?.user<com.kinvey.java.User<*>>()?.isUserLoggedIn!!) {
                    val intent1 = android.content.Intent(this@ClassesActivity, LoginActivity::class.java)
                    startActivity(intent1)
                }
            }
        }
        val drawer = findViewById(com.example.lili.teacherhelper.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        drawer.closeDrawer(android.support.v4.view.GravityCompat.START)
        return true
    }

    var nameArray = arrayOf("Octopus", "Pig", "Sheep", "Rabbit", "Snake", "Spider")

    var infoArray = arrayOf("8 tentacled monster", "Delicious in rolls", "Great for jumpers", "Nice in a stew", "Great for shoes", "Scary.")

    var listView: android.widget.ListView? = null
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        client = (application as com.teacherhelper.TeacherApp).sharedClient!!
        setContentView(com.example.lili.teacherhelper.R.layout.activity_classes)
        val toolbar = findViewById(com.example.lili.teacherhelper.R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(com.example.lili.teacherhelper.R.id.fab) as android.support.design.widget.FloatingActionButton
        fab.setOnClickListener { view ->
            val intent1 = android.content.Intent(this@ClassesActivity, ClassCreateActivity::class.java)
            startActivity(intent1)

        }

        val drawer = findViewById(com.example.lili.teacherhelper.R.id.drawer_layout) as android.support.v4.widget.DrawerLayout
        val toggle = android.support.v7.app.ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(com.example.lili.teacherhelper.R.id.nav_view) as android.support.design.widget.NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val res = arrayOf(arrayOf<ClassEntity>())
        val user = client?.user<com.kinvey.java.User<*>>()
        val layout: android.support.constraint.ConstraintLayout = layoutInflater.inflate(com.example.lili.teacherhelper.R.layout.content_classes, null).findViewById(com.example.lili.teacherhelper.R.id.layoutClasaes) as android.support.constraint.ConstraintLayout
        if (user?.isUserLoggedIn()!! && user.get("isTeacher") != null) {
            val query = client?.appData<ClassEntity>(CLASSES_COL, ClassEntity::class.java)?.query()?.equals(CLASSES_TEACHER_ID_COL, user.getId())
            val a = client?.linkedData<ClassEntity>(CLASSES_COL, ClassEntity::class.java)
            a?.get(query, object : com.kinvey.android.callback.KinveyListCallback<ClassEntity> {
                override fun onSuccess(classEntities: Array<ClassEntity>?) {
                    if (classEntities == null || classEntities.isEmpty()) {
                        layout.findViewById(com.example.lili.teacherhelper.R.id.noDataText).setVisibility(android.view.View.VISIBLE)
                    } else {
                        layout.findViewById(com.example.lili.teacherhelper.R.id.noDataText).setVisibility(android.view.View.INVISIBLE)
                        val whatever = com.teacherhelper.ClassesListViewAdapter(act, classEntities, infoArray)
                        listView = findViewById(com.example.lili.teacherhelper.R.id.classesList) as android.widget.ListView
                        listView?.setAdapter(whatever)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    println(throwable)
                    layout.findViewById(com.example.lili.teacherhelper.R.id.noDataText).visibility = android.view.View.VISIBLE
                }
            }, null, arrayOf("name", CLASSES_TEACHER_ID_COL), 1, true)
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
}
