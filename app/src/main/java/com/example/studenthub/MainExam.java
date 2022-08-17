package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainExam extends AppCompatActivity {
    private DatabaseManager mydManager;
    private ListView list_now, list_past;
    private String student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exam);

        Intent intent = getIntent();
        student = intent.getStringExtra("SID");

        mydManager = new DatabaseManager(MainExam.this);
        list_now =  findViewById(R.id.current_list);
        list_past = findViewById(R.id.past_list);

        //SHOW RECORDS
        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.retrieveExamNow(student);
        String info = "";
        for (int i = 0; i < tableContent.size(); i++) {
            info += tableContent.get(i) + "\n";
        }
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tableContent);
        list_now.setAdapter(arrayAdpt);

        ArrayList<String> tableContent2 = mydManager.retrieveExamPast(student);
        String info2 = "";
        for (int i = 0; i < tableContent2.size(); i++) {
            info2 += tableContent2.get(i) + "\n";
        }
        ArrayAdapter<String> arrayAdpt2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tableContent2);
        list_past.setAdapter(arrayAdpt2);
    }

    public void back(View view) {
        mydManager.close();
        Intent intent = new Intent(MainExam.this, MainStudent.class);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent = new Intent(MainExam.this, AddExam.class);
        intent.putExtra("SID", student);
        startActivity(intent);
    }
}