package com.example.schedulers;

import java.util.ArrayList;
 
public abstract class scheduler {
    private ArrayList<process> processList;
    private ArrayList<ArrayList<Integer>> GanttChartList = new ArrayList<>();
    private boolean status; // Assuming the scheduler is busy when true and idle when false
    private boolean isPreemptive; // Assuming the preemptive is true and non-preemptive is false
    private int timer;
    private int processCount;
    private process currentProcessInExecution;

    public scheduler(ArrayList processList, boolean isPreemptive) {
        this.processList = processList;
        this.status = true; 
        this.isPreemptive = isPreemptive;
        this.timer = 0;
        this.processCount = processList.size();
        this.currentProcessInExecution = null;
    }
    public ArrayList<process> getProcessList() {
        return processList;
    }
    public void setProcessList(ArrayList<process> processList) {
        this.processList = processList;
    }
    public ArrayList<ArrayList<Integer>> getGanttChartList() {
        return GanttChartList;
    }
    public void setGanttChartList(ArrayList<ArrayList<Integer>> ganttChartList) {
        GanttChartList = ganttChartList;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isPreemptive() {
        return isPreemptive;
    }
    public void setPreemptive(boolean preemptive) {
        isPreemptive = preemptive;
    }
    public int getTimer() {
        return timer;
    }
    public void setTimer(int currentTime) {
        this.timer = currentTime;
    }
    public int getProcessCount() {
        return processCount;
    }
    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }
    public process getCurrentProcessInExecution() {
        return currentProcessInExecution;
    }
    public void setCurrentProcessInExecution(process currentProcessInExecution) {
        this.currentProcessInExecution = currentProcessInExecution;
    }
    public void incrementCurrentTime(int timeUnit) {
        this.timer += timeUnit;
        currentProcessInExecution.setRemainingTime(currentProcessInExecution.getRemainingTime() - timeUnit);
        /*
        if (currentProcessInExecution.getRemainingTime() == 0) {
            currentProcessInExecution.setTurnaroundTime(timer - currentProcessInExecution.getArrivalTime());
            currentProcessInExecution.setWaitingTime(currentProcessInExecution.getTurnaroundTime() - currentProcessInExecution.getBurstTime());
            isFinished();
        }
        */
    }
  public void addToGanttChart(int processId, int timeUnit) {
        //update timeUnit when id change only
        ArrayList<Integer> ganttEntry = new ArrayList<>();
        ganttEntry.add(processId);
        ganttEntry.add(timeUnit);
        GanttChartList.add(ganttEntry);
    }
    
    //implemented in Here
    public void calculateAverageWaitingTime(){}
    public void calculateAverageTurnAroundTime(){}
    public void calculateWaitingTime(process currentProcessInExecution){}
    public void calculateTurnAroundTime(process currentProcessInExecution){}
    public void isFinished(){}

    // Abstract method to be implemented by subclasses
    public abstract void schedule(); 
    public abstract void sortProcessList();
    //public abstract void currentProcessInExecutionUpdate(process currentProcessInExecution);

}
