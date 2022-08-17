package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class AddTask extends AppCompatActivity {
    private DatabaseManager mydManager;
    private EditText tname, location;
    private RadioGroup rg;
    private RadioButton rb;
    private boolean recInserted;
    private TableLayout addLayout;
    private LinearLayout navbar;
    private TextView response;
    private Button re_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mydManager = new DatabaseManager(AddTask.this);
        addLayout = findViewById(R.id.add_table);
        response = findViewById(R.id.response);
        re_button = findViewById(R.id.return_button2);
        navbar = findViewById(R.id.navbar);
    }

    public void addTask(View v) {
        tname = findViewById(R.id.tname_edit);
        location = findViewById(R.id.location_edit);

        rg = findViewById(R.id.statusGroup);
        int selectedId = rg.getCheckedRadioButtonId();
        rb = findViewById(selectedId);
        String Status = (String) rb.getText();
        recInserted = mydManager.addTask(
                tname.getText().toString(), location.getText().toString(),
                Status);
        addLayout.setVisibility(View.GONE);
        navbar.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);
        if (recInserted) {
            response.setText("Task Added Successfully");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        tname.setText("");
        location.setText("");
    }
    public void task(View view) {
        mydManager.close();
        Intent intent = new Intent(AddTask.this, MainTask.class);
        startActivity(intent);
    }
}