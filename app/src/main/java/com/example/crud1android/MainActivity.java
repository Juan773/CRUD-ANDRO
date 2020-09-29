package com.example.crud1android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public void countRecords() {
        int recordCount = new TableControllerStudent(this).count();
        TextView textViewRecordCount = (TextView) findViewById(R.id.textViewRecordCount);
        textViewRecordCount.setText(recordCount + " Registros Encontrados.");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonCreateStudent = (Button) findViewById(R.id.buttonCreateStudent);
        buttonCreateStudent.setOnClickListener(new OnClickListenerCreateStudent());
        countRecords();
        readRecords();
    }
    public void readRecords() {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutRecords);
        linearLayoutRecords.removeAllViews();

        List<ObjectStudent> students = new TableControllerStudent(this).read();

        if (students.size() > 0) {

            for (ObjectStudent obj : students) {

                int id = obj.id;
                String studentFirstname = obj.firstname;
                String studentEmail = obj.email;

                String textViewContents = studentFirstname + " - " + studentEmail;

                TextView textViewStudentItem= new TextView(this);
                textViewStudentItem.setPadding(0, 10, 0, 10);
                textViewStudentItem.setText(textViewContents);
                textViewStudentItem.setTag(Integer.toString(id));
                textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());

                linearLayoutRecords.addView(textViewStudentItem);
            }

        }

        else {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No hay Registros.");

            linearLayoutRecords.addView(locationItem);
        }

    }
}