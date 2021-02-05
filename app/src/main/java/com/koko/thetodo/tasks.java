package com.koko.thetodo;

public class tasks {
    private int id;
    private String task;

    public tasks(int id, String task) {
        this.id = id;
        this.task = task;
    }

    //getters

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }


    //setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }


    //tostring


    @Override
    public String toString() {
        return task;
    }
}
