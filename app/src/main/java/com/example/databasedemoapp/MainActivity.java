package com.example.databasedemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText computerName, computerType;
    Button addButton, deleteButton;
    ListView listDataItems;

    List<Computer> allComputers;
    ArrayList<String> computerNameList;
    SqLiteHandler databaseHandler;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        computerName = findViewById(R.id.edit_computer_name);
        computerType = findViewById(R.id.edit_computer_type);
        addButton = findViewById(R.id.button_add);
        deleteButton = findViewById(R.id.button_delete);
        listDataItems = findViewById(R.id.list_database_items);

        //create object of our database handler
        databaseHandler = new SqLiteHandler(MainActivity.this);

        //let's instantiate the list object of all computers, we need a class to create the object of the
        //list, hence we use the database handler object
        allComputers = databaseHandler.getAllComputers();

        //Let's instantiate our arrayList
        computerNameList = new ArrayList<>();
        if (allComputers.size() > 0) {
            for(int i = 0; i<allComputers.size(); i++) {
                Computer computer = allComputers.get(i);
                computerNameList.add(computer.getComputerName() + "-" + computer.getComputerType());
            }
        }
        adapter = new ArrayAdapter(MainActivity.this, R.layout.simple_list_item, computerNameList);
        listDataItems.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (computerName.getText().toString().matches("") || computerType.getText().toString().matches("")) {
                    return;
                }
                //to add data to the database, we need to create a new object of the computer class
                Computer computer = new Computer(computerName.getText().toString(), computerType.getText().toString());
                allComputers.add(computer);
                databaseHandler.addComputer(computer);
                //add to arraylist
                computerNameList.add(computer.getComputerName() + "-" + computer.getComputerType());
                computerName.setText("");
                computerType.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allComputers.size()>0) {
                    computerNameList.remove(allComputers.size() - 1);
                    databaseHandler.deleteComputer(allComputers.get(allComputers.size() - 1));
                    allComputers.remove(allComputers.size() - 1);
                    adapter.notifyDataSetChanged();
                } else {
                    return;
                }
            }
        });
    }
}