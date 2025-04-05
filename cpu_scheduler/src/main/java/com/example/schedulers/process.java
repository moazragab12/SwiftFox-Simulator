package com.example.schedulers;


public class process {
private int id;
private int arrivalTime;  
private int burstTime;
private int waitingTime=0;    
private int turnaroundTime=0;
private int remainingTime; // For SRTF
private int priority=1; // For Priority
private String name;  
private static int Count = 0; // Static variable to make process ID unique
process(String name,int arrivalTime, int burstTime, int priority) {
    this.id = ++Count; // Increment Count and assign it to id
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.remainingTime = burstTime; // Initialize remaining time to burst time
    this.priority = priority;
    this.name = name;
}
process(String name,int arrivalTime, int burstTime){
    this.id = ++Count; // Increment Count and assign it to id
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.remainingTime = burstTime; // Initialize remaining time to burst time
    this.priority = 1;
    this.name = name;
}

public int getId() {
    return id;
}

public int getArrivalTime() {
    return arrivalTime;
}

public int getBurstTime() {
    return burstTime;
}

public int getWaitingTime() {
    return waitingTime;
}

public void setWaitingTime(int waitingTime) {
    this.waitingTime = waitingTime;
}

public int getTurnaroundTime() {
    return turnaroundTime;
}

public void setTurnaroundTime(int turnaroundTime) {
    this.turnaroundTime = turnaroundTime;
}

public int getRemainingTime() {
    return remainingTime;
}

public void setRemainingTime(int remainingTime) {
    this.remainingTime = remainingTime;
}

public int getPriority() {
    return priority;
}


public String getName() {
    return name;
}

public static void resetCount() {
    Count = 0; // Reset the static variable Count to 0
}
public void incWaitingTime(int time) {
    this.waitingTime += time; // Increment waiting time by the given time
}

public void incTurnaroundTime(int time) {
    this.turnaroundTime += time; // Increment turnaround time by the given time
}
public void decRemainingTime(int time) {
    this.remainingTime -= time; // Decrement remaining time by the given time
}

}