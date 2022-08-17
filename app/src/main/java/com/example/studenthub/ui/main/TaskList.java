package com.example.studenthub.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studenthub.AddTask;
import com.example.studenthub.DatabaseManager;
import com.example.studenthub.EditTask;
import com.example.studenthub.MainActivity;
import com.example.studenthub.R;

import java.util.ArrayList;


public class TaskList extends Fragment {
    private DatabaseManager mydManager;
    private ListView list;
    private View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
        Button add = (Button)rootView.findViewById(R.id.add_button);
        Button home = (Button)rootView.findViewById(R.id.return_button);

        home.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }

        });

        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), AddTask.class);
                startActivity(intent);
            }

        });

        mydManager = new DatabaseManager(getActivity());
        list =  rootView.findViewById(R.id.task_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //GET TASK ID
                String item = (String) list.getAdapter().getItem(position);
                char[] temp = new char[item.length()];
                int i = 0;
                while (item.charAt(i) != '|'){
                    temp[i]=item.charAt(i);
                    i++;
                }
                String TID = new String(temp);
                while (item.charAt(i) != ','){
                    temp[i]=item.charAt(i);
                    i++;
                }
                item = new String(temp);
                Toast.makeText(getActivity(), item + " selected", Toast.LENGTH_SHORT).show();
                //CARRY TO EDIT ACTIVITY
                Intent intent = new Intent(getActivity(), EditTask.class);
                intent.putExtra("TID",TID);
                startActivity(intent);
            }
        });
        //SHOW RECORDS
        mydManager.openReadable();
        ArrayList<String> tableContent = mydManager.retrieveTask("PENDING");
        String info = "";
        for (int i = 0; i < tableContent.size(); i++) {
            info += tableContent.get(i) + "\n";
        }
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tableContent);
        list.setAdapter(arrayAdpt);

        return rootView;
    }
}