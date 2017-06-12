package com.teacherhelper.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.example.lili.teacherhelper.R
import com.teacherhelper.ClassesListViewAdapter
import com.teacherhelper.KniveyClient


class ClassActivity : AppCompatActivity() {
    private var client: KniveyClient? = null
    private var classEntityName: String? = null
    private var fileSelectBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        classEntityName = intent?.extras?.get(ClassesListViewAdapter.CLASS_KEY) as String?
        setTitle(classEntityName)
        fileSelectBtn = findViewById(R.id.chooseBtn) as Button?
        fileSelectBtn?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            try {
                startActivityForResult(
                        Intent.createChooser(intent, "Select a File to Upload"),
                        0)
            } catch (ex: android.content.ActivityNotFoundException) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(this, "Please install a File Manager.",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }
}
