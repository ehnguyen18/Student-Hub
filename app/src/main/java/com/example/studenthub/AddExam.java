package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExam extends AppCompatActivity {
    private DatabaseManager mydManager;
    private String student;
    private EditText date, ename, location;
    private boolean recInserted;
    private TableLayout addLayout;
    private LinearLayout navbar;
    private TextView response;
    private Button re_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);
        Intent intent = getIntent();
        student = intent.getStringExtra("SID");

        mydManager = new DatabaseManager(AddExam.this);
        addLayout = findViewById(R.id.add_table);
        response = findViewById(R.id.response);
        re_button = findViewById(R.id.return_button2);
        navbar = findViewById(R.id.navbar);
        date = findViewById(R.id.date_edit);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date);
            }
        });

    }

    //Source:https://www.youtube.com/watch?v=XG8OpQ7X-nY
    private void showDateTimeDialog(EditText date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(AddExam.this, timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(AddExam .this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void addExam(View v) {
        ename = findViewById(R.id.ename_edit);
        location = findViewById(R.id.location_edit);

        recInserted = mydManager.addExam(
                Long.parseLong(student), ename.getText().toString(),
                date.getText().toString() ,location.getText().toString());
        addLayout.setVisibility(View.GONE);
        navbar.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);
        if (recInserted) {
            response.setText("Exam Added Successfully");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        ename.setText("");
        date.setText("");
        location.setText("");
    }
    public void exam(View view) {
        mydManager.close();
        Intent intent = new Intent(AddExam.this, MainStudent.class);
        startActivity(intent);
    }
}