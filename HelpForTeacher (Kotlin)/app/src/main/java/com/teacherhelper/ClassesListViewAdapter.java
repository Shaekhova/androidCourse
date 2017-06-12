package com.teacherhelper;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lili.teacherhelper.R;
import com.teacherhelper.activity.ClassActivity;
import com.teacherhelper.model.ClassEntity;

/**
 * Created by ruzal on 11-Jun-17.
 */

public class ClassesListViewAdapter<T> extends ArrayAdapter<T> {
    //to reference the Activity
    private final Activity context;

    public static final String CLASS_KEY = "classEnt";

    //to store the list of countries
    private final T[] nameArray;

    //to store the list of countries
    private final String[] infoArray;
    public ClassesListViewAdapter(Activity context, T[] nameArrayParam, String[] infoArrayParam){
        super(context, R.layout.classes_listview_row, nameArrayParam);
        this.context=context;
        this.nameArray = nameArrayParam;
        this.infoArray = infoArrayParam;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.classes_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.classesText);
        nameTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClassActivity.class);
                intent.putExtra(CLASS_KEY, ((ClassEntity)nameArray[position]).getName());
                context.startActivity(intent);
            }
        });

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(((ClassEntity)nameArray[position]).getName());
        return rowView;

    };
}
