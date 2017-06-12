package com.teacherhelper.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.lili.teacherhelper.R
import com.teacherhelper.model.ClassEntity
import com.teacherhelper.KniveyClient
import com.teacherhelper.TeacherApp

class ClassCreateActivity : AppCompatActivity() {
    private var client: KniveyClient? = null
    var classesName: EditText? = null
    var addBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_create)
        client = (application as TeacherApp).sharedClient!!
        classesName = findViewById(R.id.nameEdit) as EditText?
        addBtn = findViewById(R.id.addBtn) as Button?
        addBtn?.setOnClickListener {
            if (!classesName?.text?.isEmpty()!!) {
                val newClass = ClassEntity()
                newClass.name = classesName?.text?.toString()
                client?.createClass(newClass)
                onBackPressed()
            }
        }

    }
}
