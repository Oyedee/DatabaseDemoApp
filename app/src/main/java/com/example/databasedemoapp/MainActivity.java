package com.example.databasedemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    EditText computerName, computerType;
    Button addButton, deleteButton;
    ListView listDataItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        computerName = findViewById(R.id.edit_computer_name);
        computerType = findViewById(R.id.edit_computer_type);
        addButton = findViewById(R.id.button_add);
        deleteButton = findViewById(R.id.button_delete);
        listDataItems = findViewById(R.id.list_database_items);



    }
}