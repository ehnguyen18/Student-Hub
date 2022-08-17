package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainStudent extends AppCompatActivity {

    private DatabaseManager mydManager;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_student);

        mydManager = new DatabaseManager(MainStudent.this);
        list =  findViewById(R.id.student_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //GET STUDENT ID
                String item = (String) list.getAdapter().getItem(position);
                char[] temp = new char[item.length()];
                int i = 0;
                while (item.charAt(i) != ','){
                    temp[i]=item.charAt(i);
                    i++;
                }
                String SID = new String(temp);
                i++;
                while (item.charAt(i) != ','){
                    temp[i]=item.charAt(i);
                    i++;
                }
                item = new String(temp);
                Toast.makeText(getApplicationContext(), item + " selected",
                        Toast.LENGTH_SHORT).show();
                //CARRY TO EDIT ACTIVITY
                Intent intent = new Intent(MainStudent.this, EditStudent.class);
                intent.putExtra("SID",SID);
                startActivity(intent);
            }
        });
        //SHOW RECORDS
        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.retrieveStudent();
        String info = "";
        for (int i = 0; i < tableContent.size(); i++) {
            info += tableContent.get(i) + "\n";
        }
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tableContent);
        list.setAdapter(arrayAdpt);
    }

    public void home(View view) {
        mydManager.close();
        Intent intent = new Intent(MainStudent.this, MainActivity.class);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent = new Intent(MainStudent.this, AddStudent.class);
        startActivity(intent);
    }
}