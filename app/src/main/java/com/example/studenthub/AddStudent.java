package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class AddStudent extends AppCompatActivity {
    private DatabaseManager mydManager;
    private EditText SID, fName, lName, c, Address;
    private NumberPicker np;
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
        setContentView(R.layout.activity_add_student);

        mydManager = new DatabaseManager(AddStudent.this);
        addLayout = findViewById(R.id.add_table);
        response = findViewById(R.id.response);
        re_button = findViewById(R.id.return_button2);
        navbar = findViewById(R.id.navbar);
        np = findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(false);

    }

    public void addStudent(View v) {
        SID = findViewById(R.id.SID_edit);
        fName = findViewById(R.id.FName_edit);
        lName = findViewById(R.id.LName_edit);
        c = findViewById(R.id.Course_edit);

        rg = findViewById(R.id.genderGroup);
        int selectedId = rg.getCheckedRadioButtonId();
        rb = findViewById(selectedId);
        String Gender = (String) rb.getText();
        Address = findViewById(R.id.Address_edit);
        recInserted = mydManager.addStudent(Long.parseLong(SID.getText().toString()),
                fName.getText().toString(), lName.getText().toString(),
                Integer.parseInt(c.getText().toString()), np.getValue(),
                Gender, Address.getText().toString() );

        addLayout.setVisibility(View.GONE);
        navbar.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);
        if (recInserted) {
            response.setText("Friend Added Successfully");
        }
        else {
            response.setText("Sorry, errors when inserting to DB");
        }
        //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(price.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        SID.setText("");
        fName.setText("");
        lName.setText("");
        c.setText("");
        Address.setText("");
    }


    public void student(View view) {
        mydManager.close();
        Intent intent = new Intent(AddStudent.this, MainStudent.class);
        startActivity(intent);
    }
}