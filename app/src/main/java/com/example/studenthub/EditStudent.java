package com.example.studenthub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class EditStudent extends AppCompatActivity {
    public String student;
    private TextView response;
    private DatabaseManager mydManager;
    private ImageView img;
    private Uri imgUri;
    private EditText SID, fName, lName, c, Address;
    private NumberPicker np;
    private RadioGroup rg;
    private RadioButton rb,rm,rf;
    private boolean status;
    private TableLayout editLayout;
    private LinearLayout navbar;
    private Button re_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        Intent intent = getIntent();
        student = intent.getStringExtra("SID");

        //Link with layout
        mydManager = new DatabaseManager(EditStudent.this);
        mydManager.openReadable();
        editLayout = findViewById(R.id.edit_table);
        response = findViewById(R.id.response);
        re_button = findViewById(R.id.return_button2);
        navbar = findViewById(R.id.navbar);
        np = findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(100);
        np.setWrapSelectorWheel(false);
        SID = findViewById(R.id.SID_edit);
        fName = findViewById(R.id.FName_edit);
        lName = findViewById(R.id.LName_edit);
        c = findViewById(R.id.Course_edit);
        Address = findViewById(R.id.Address_edit);
        img = findViewById(R.id.imageView);

        //Show Selected Student
        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.selectStudent(student);
        SID.setText(tableContent.get(0));
        student = tableContent.get(0);
        fName.setText(tableContent.get(1));
        lName.setText(tableContent.get(2));
        c.setText(tableContent.get(3));
        rm = findViewById(R.id.male);
        rf = findViewById(R.id.female);
        if (tableContent.get(4).equals("Male"))
                rm.setChecked(true);
        else rf.setChecked(true);
        np.setValue(Integer.parseInt(tableContent.get(5)));
        Address.setText(tableContent.get(6));
        if (tableContent.get(7) != null){
            imgUri = Uri.parse(tableContent.get(7));
            img.setImageURI(imgUri);
        }
        else img.setImageResource(R.mipmap.westernlogo);
    }

    public void deleteFriend(View view){
        navbar.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        status = mydManager.deleteStudent(student);
        if (status)
            response.setText("Friend Deleted Successful");
        else response.setText("Not Deleted");
    }

    public void updateFriend(View view){
        navbar.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
        response.setVisibility(View.VISIBLE);
        re_button.setVisibility(View.VISIBLE);

        rg = findViewById(R.id.genderGroup);
        int selectedId = rg.getCheckedRadioButtonId();
        rb = findViewById(selectedId);
        String Gender = (String) rb.getText();

        status = mydManager.updateStudent(Long.parseLong(SID.getText().toString()),
                fName.getText().toString(), lName.getText().toString(),
                Integer.parseInt(c.getText().toString()), np.getValue(),
                Gender, Address.getText().toString(), imgUri.toString() );
        if (status)
            response.setText("Friend Updated Successful");
        else response.setText("Not Updated");
    }
    public void student(View view) {
        mydManager.close();
        Intent intent = new Intent(EditStudent.this, MainStudent.class);
        startActivity(intent);
    }
    public void showmap(View view) {
        String addr = Address.getText().toString();
        Intent intent = new Intent(EditStudent.this, MapsActivity.class);
        intent.putExtra("address",addr);
        startActivity(intent);
    }

    public void exam(View view) {
        Intent intent = new Intent(EditStudent.this, MainExam.class);
        intent.putExtra("SID",student);
        startActivity(intent);
    }

    public void setImg(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"),1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                imgUri = data.getData();
                img.setImageURI(imgUri);
            }
        }
    }
}