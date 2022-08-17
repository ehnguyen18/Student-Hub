package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditTask extends AppCompatActivity {
    private String task;
    private Long TID;
    private TextView response;
    private DatabaseManager mydManager;
    private EditText tname, location;
    private NumberPicker np;
    private RadioGroup rg;
    private RadioButton rb,rp,rc;
    private boolean status;
    private TableLayout editLayout;
    private LinearLayout navbar;
    private Button re_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        task = intent.getStringExtra("TID");

        //Link with layout
        mydManager = new DatabaseManager(EditTask.this);
        mydManager.openReadable();
        editLayout = findViewById(R.id.edit_table);
        response = findViewById(R.id.response);
        re_button = findViewById(R.id.return_button2);
        navbar = findViewById(R.id.navbar);
        tname = findViewById(R.id.tname_edit);
        location = findViewById(R.id.location_edit);
        //Show Selected Student
        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.selectTask(task);
        task = tableContent.get(0);
        tname.setText(tableContent.get(1));
        location.setText(tableContent.get(2));
        rp = findViewById(R.id.pending);
        rc = findViewById(R.id.completed);
        if (tableContent.get(3).equals("PENDING"))
            rp.setChecked(true);
        else rc.setChecked(true);
    }

    public void deleteTask(View view){
        navbar.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);
        status = mydManager.deleteTask(task);
        if (status)
            response.setText("Task Deleted Successful");
        else response.setText("Not Deleted");
    }

    public void updateTask(View view){
        navbar.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);

        rg = findViewById(R.id.statusGroup);
        int selectedId = rg.getCheckedRadioButtonId();
        rb = findViewById(selectedId);
        String status2 = (String) rb.getText();

        status = mydManager.updateTask(task, tname.getText().toString(),
                location.getText().toString(), status2);
        if (status)
            response.setText("Task Updated Successful");
        else response.setText("Not Updated");
    }

    public void task(View view) {
        mydManager.close();
        Intent intent = new Intent(EditTask.this, MainTask.class);
        startActivity(intent);
    }

}