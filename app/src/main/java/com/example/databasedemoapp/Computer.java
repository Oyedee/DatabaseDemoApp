package com.example.databasedemoapp;

public class Computer {
    private int id;
    private String computerName;
    private String computerType;

    //creating three constructors for our Computer class

    //first constructor is an empty constructor
    public Computer() {
        super();
    }

    //second constructor will take three parameters
    public Computer(int id, String computerName, String computerType) {
        this.id = id;
        this.computerName = computerName;
        this.computerType = computerType;
    }

    //third constructor will take two parameters
    public Computer(String computerName, String computerType) {
        this.computerName = computerName;
        this.computerType = computerType;
    }
// Generate the getters and setters for our class properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getComputerType() {
        return computerType;
    }

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }
}
