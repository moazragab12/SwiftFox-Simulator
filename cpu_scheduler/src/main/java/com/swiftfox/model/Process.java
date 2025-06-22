package com.swiftfox.model;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a process in the CPU scheduling simulation.
 * Each process has a unique PID and contains attributes relevant to scheduling algorithms.
 */
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
    /**
     * Represents the possible states of a process during the simulation lifecycle.
     *
     * <ul>
     *   <li><b>NEW</b>: The process has been created but has not entered the ready queue yet.</li>
     *   <li><b>READY</b>: The process is in the ready queue waiting to be scheduled.</li>
     *   <li><b>RUNNING</b>: The process is currently using the CPU.</li>
     *   <li><b>TERMINATED</b>: The process has finished execution.</li>
     * </ul>
     */
    public enum State {NEW, READY, RUNNING, TERMINATED}
    private State state;
    private final SimpleIntegerProperty remainingTimeProperty = new SimpleIntegerProperty();
    /**
     * Constructs a new Process with a specific name, arrival time, burst time, and priority.
     *
     * @param name the name of the process
     * @param arrivalTime the time at which the process arrives in the system
     * @param burstTime the total CPU time required by the process
     * @param priority the priority of the process (lower number may indicate higher priority)
     */
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

    /**
     * Constructs a new Process without a specified priority (defaults to 0).
     *
     * @param name the name of the process
     * @param arrivalTime the time at which the process arrives
     * @param burstTime the total CPU time required
     */
    public Process(String name, int arrivalTime, int burstTime) {
        this(name,arrivalTime,burstTime,0);
    }

    /**
     * Executes the process for a given number of time units.
     * Reduces the remaining time and updates the process state if it finishes.
     *
     * @param timeUnits the number of time units to execute
     */
    public void execute(int timeUnits) {
        remainingTime = Math.max(0, remainingTime - timeUnits);
        if (remainingTime == 0)
            state = State.TERMINATED;

        // Update the remaining time property, which will automatically update the table view
        remainingTimeProperty.set(remainingTime);
    }

    /**
     * Preempts the process if it's running and not finished.
     * Sets its state to READY.
     */
    public void preempt() {
        if (state == State.RUNNING && remainingTime > 0) {
            state = State.READY;
        }
    }
    /**
     * Resets the static process ID counter.
     * Useful for restarting simulations.
     */
    public static void resetCounter() {
        counter = 1;
    }

    // Getters

    /**
     * Returns the unique process ID.
     *
     * @return the process ID
     */
    public int getPid() { return pid; }
    /**
     * Returns the name of the process.
     *
     * @return the process name
     */
    public String getName() { return name; }
    /**
     * Returns the arrival time of the process.
     *
     * @return the arrival time
     */
    public int getArrivalTime() { return arrivalTime; }
    /**
     * Returns the total burst time of the process.
     *
     * @return the burst time
     */
    public int getBurstTime() { return burstTime; }
    /**
     * Returns the priority of the process.
     *
     * @return the priority value
     */
    public int getPriority() { return priority; }
    /**
     * Returns the remaining execution time for the process.
     *
     * @return remaining time
     */
    public int getRemainingTime() { return remainingTime; }
    /**
     * Returns the current state of the process.
     *
     * @return the process state
     */
    public State getState() { return state; }
    /**
     * Sets the process state to RUNNING.
     */
    public void setRunning() {state = State.RUNNING;}

    /**
     * Sets the process state to READY.
     */
    public void setReady() {state = State.READY;}
    /**
     * Sets the process state to TERMINATED.
     */
    public void setTerminated() {state = State.TERMINATED;}
    /**
     * Returns the waiting time of the process.
     *
     * @return waiting time
     */
    public int getWaitingTime() { return waitingTime; }
    /**
     * Sets the waiting time for the process.
     *
     * @param waitingTime the waiting time to set
     */
    public void setWaitingTime(int waitingTime) { this.waitingTime = waitingTime; }
    /**
     * Returns the turnaround time of the process.
     *
     * @return turnaround time
     */
    public int getTurnaroundTime() { return turnaroundTime; }
    /**
     * Sets the turnaround time for the process.
     *
     * @param turnaroundTime the turnaround time to set
     */
    public void setTurnaroundTime(int turnaroundTime) { this.turnaroundTime = turnaroundTime; }
    /**
     * Returns the JavaFX property that reflects the remaining time,
     * enabling UI bindings for updates.
     *
     * @return remaining time as a JavaFX SimpleIntegerProperty
     */
    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTimeProperty;
    }

}