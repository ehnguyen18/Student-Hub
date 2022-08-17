package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void student(View view) {
        Intent intent = new Intent(MainActivity.this, MainStudent.class);
        startActivity(intent);
    }

    public void task(View view) {
        Intent intent = new Intent(MainActivity.this, MainTask.class);
        startActivity(intent);
    }

    public void exit(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}