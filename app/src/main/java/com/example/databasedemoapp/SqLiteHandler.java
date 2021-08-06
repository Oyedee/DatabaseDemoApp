package com.example.databasedemoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqLiteHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "computer.db";

    //Table Name
    private static final String TABLE_COMPUTER = "computers";

    //Table columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_COMPUTER_NAME = "computerName";
    private static final String COLUMN_COMPUTER_TYPE = "computerType";

    //Let's create an sql command to create a table by storing the commands in a string variable
    String CREATE_COMPUTER_TABLE = "CREATE TABLE " + TABLE_COMPUTER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_COMPUTER_NAME + " TEXT, " + COLUMN_COMPUTER_TYPE +
            " TEXT" +")";

    //Implement a constructor for SqLiteHandler data class

    public SqLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //this will create a computer Table inside our database
        sqLiteDatabase.execSQL(CREATE_COMPUTER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //this method will help us drop a table and replace it with another one.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPUTER); // this will drop the table
        onCreate(sqLiteDatabase);// this will create a new table
    }

    //Let's create a CRUD (Create, Read, Update and Delete) operations

    //Create Operation
    //this method will help us add a new value to our database
    public void addComputer(Computer computer) {
        //creating an object of sqlite database
        SQLiteDatabase database = this.getWritableDatabase();
        //create variable for content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());
        //now let's insert the values inside our database
        database.insert(TABLE_COMPUTER, null, contentValues);
        database.close();
    }

    //get a single computer object from our database or Read a single Computer object
    public Computer getComputer(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        //to read our data from the database, we need to create an object of the cursor interface
        Cursor cursor = database.query(TABLE_COMPUTER, new String[]{COLUMN_ID, COLUMN_COMPUTER_NAME, COLUMN_COMPUTER_TYPE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        //let's fetch the data from the cursor
        if(cursor != null) {
            //this will return the first object found in the specific column id
            cursor.moveToFirst();
        }
        Computer computer = new Computer(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return computer;
    }

    //getting all computer objects
    //create a list of computer type
    public List<Computer> getAllComputers() {
        List<Computer> computers = new ArrayList<>();
        //let's write a query that will select all the computer objects in our database
        String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_COMPUTER;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(SELECT_ALL_QUERY, null);

        //let's check if our database contain any data
        if(cursor.moveToFirst()) {
            do {
                Computer computer = new Computer();
                computer.setId(Integer.parseInt(cursor.getString(0)));
                computer.setComputerName(cursor.getString(1));
                computer.setComputerType(cursor.getString(2));
                computers.add(computer);
            }
            //while there's is more position in the database
            while (cursor.moveToNext());
        }
        return computers;
    }

    //update a single computer object
    public int updateComputer(Computer computer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COMPUTER_NAME, computer.getComputerName());
        contentValues.put(COLUMN_COMPUTER_TYPE, computer.getComputerType());

        //update the values inside a particular position in our database using an id.
        return sqLiteDatabase.update(TABLE_COMPUTER, contentValues, COLUMN_ID + "=?", new String[]{
            String.valueOf(computer.getId())
        });
    }

    //Deleting a single computer object at a specific position in the table using the column ID.
    public void deleteComputer(Computer computer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_COMPUTER, COLUMN_ID + "=?" , new String[]{
                String.valueOf(computer.getId())
        });
        sqLiteDatabase.close();
    }

    //getting the numbers of computers. the total number of computer objects in the database
    public int getComputerCount() {
        //create a string that takes the query command to be used.
        String COMPUTER_COUNT_QUERY = "SELECT * FROM " + TABLE_COMPUTER;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(COMPUTER_COUNT_QUERY, null);
        cursor.close();
        return cursor.getCount();
    }
}
