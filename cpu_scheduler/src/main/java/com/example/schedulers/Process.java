package com.example.schedulers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public final class Process {
    private static int counter = 0 ;
    private final int pid;
    private final String name;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;
    private int remainingTime ;
    public enum ProcessState {NEW, READY, RUNNING, TERMINATED}
    private ProcessState state;

    // Use StringProperty or IntegerProperty for table bindings
    private final SimpleStringProperty nameProperty = new SimpleStringProperty();
    private final SimpleIntegerProperty arrivalTimeProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty burstTimeProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty remainingTimeProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty priorityProperty = new SimpleIntegerProperty();

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.pid = ++counter;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.state = ProcessState.NEW;

        // Set property values
        this.nameProperty.set(name);
        this.arrivalTimeProperty.set(arrivalTime);
        this.burstTimeProperty.set(burstTime);
        this.remainingTimeProperty.set(burstTime);
        this.priorityProperty.set(priority);
    }

    public Process(String name, int arrivalTime, int burstTime) {
        this(name,arrivalTime,burstTime,0);

        // Set property values
        this.nameProperty.set(name);
        this.arrivalTimeProperty.set(arrivalTime);
        this.burstTimeProperty.set(burstTime);
        this.remainingTimeProperty.set(burstTime);
        this.priorityProperty.set(0);
    }


    public void execute(int timeUnits) {
        remainingTime = Math.max(0, remainingTime - timeUnits);
        if (remainingTime == 0)
            state = ProcessState.TERMINATED;
        System.out.println(remainingTime);

        // Update the remaining time property, which will automatically update the table view
        remainingTimeProperty.set(remainingTime);
    }

    public void preempt() {
        if (state == ProcessState.RUNNING && remainingTime > 0) {
            state = ProcessState.READY;
        }
    }

    public static void resetCounter() {
        counter = 1;
    }

    // Getters
    public int getPid() { return pid; }
    public String getName() { return name; }
    public int getArrivalTime() { return arrivalTime; }
    public int getBurstTime() { return burstTime; }
    public int getPriority() { return priority; }
    public int getRemainingTime() { return remainingTime; }
    public ProcessState getState() { return state; }
    public void setRunning() {state = ProcessState.RUNNING;}
    public void setReady() {state = ProcessState.READY;}

    // Getters for properties
    public SimpleStringProperty nameProperty() {
        return nameProperty;
    }

    public SimpleIntegerProperty arrivalTimeProperty() {
        return arrivalTimeProperty;
    }

    public SimpleIntegerProperty burstTimeProperty() {
        return burstTimeProperty;
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTimeProperty;
    }

    public SimpleIntegerProperty priorityProperty() {
        return priorityProperty;
    }

}