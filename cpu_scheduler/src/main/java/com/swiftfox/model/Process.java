package com.swiftfox.model;

import javafx.beans.property.SimpleIntegerProperty;


public final class Process {
    private static int counter = 0 ;
    private final int pid;
    private final String name;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime ;
    public enum State {NEW, READY, RUNNING, TERMINATED}
    private State state;
    private final SimpleIntegerProperty remainingTimeProperty = new SimpleIntegerProperty();

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.pid = ++counter;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
        this.state = State.NEW;

        this.remainingTimeProperty.set(burstTime);
    }

    public Process(String name, int arrivalTime, int burstTime) {
        this(name,arrivalTime,burstTime,0);
    }


    public void execute(int timeUnits) {
        remainingTime = Math.max(0, remainingTime - timeUnits);
        if (remainingTime == 0)
            state = State.TERMINATED;

        // Update the remaining time property, which will automatically update the table view
        remainingTimeProperty.set(remainingTime);
    }

    public void preempt() {
        if (state == State.RUNNING && remainingTime > 0) {
            state = State.READY;
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
    public State getState() { return state; }
    public void setRunning() {state = State.RUNNING;}
    public void setReady() {state = State.READY;}
    public void setTerminated() {state = State.TERMINATED;}
    public int getWaitingTime() { return waitingTime; }
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    public int getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTimeProperty;
    }

}