package com.example.schedulers;

public abstract class scheduler {
    private process processList[];
    private process GanttChartList[][];
    private boolean status;
    private boolean isPreemptive;
    private int currentTime;
    private int processCount;
    private process currentProcessInExecution;

    public scheduler(process processList[], boolean isPreemptive) {
        this.processList = processList;
        this.GanttChartList = new process[2][];
        this.status = true;
        this.isPreemptive = isPreemptive;
        this.currentTime = 0;
        this.processCount = processList.length;
        this.currentProcessInExecution = null;
    }
    public process[] getProcessList() {
        return processList;
    }
    public void setProcessList(process[] processList) {
        this.processList = processList;
    }
    public boolean status() {
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
    public int getCurrentTime() {
        return currentTime;
    }
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
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
    public void incrementCurrentTime() {
        this.currentTime++;
    }
    public void addProcessToProcessList(process process) {
        process[] newProcessList = new process[processList.length + 1];
        System.arraycopy(processList, 0, newProcessList, 0, processList.length);
        newProcessList[processList.length] = process;
        this.processList = newProcessList;
    }
    public void addProcessToGanttChart(process process) {
        if (GanttChartList[0] == null) {
            GanttChartList[0] = new process[1];
            GanttChartList[0][0] = process;
        } else {
            process[] newGanttChart = new process[GanttChartList[0].length + 1];
            System.arraycopy(GanttChartList[0], 0, newGanttChart, 0, GanttChartList[0].length);
            newGanttChart[GanttChartList[0].length] = process;
            GanttChartList[0] = newGanttChart;
        }
    }

    //implemented in Here
    public abstract void calculateAverageWaitingTime(); 
    public abstract void calculateAverageTurnAroundTime();
    


    // Abstract method to be implemented by subclasses
    public abstract void schedule(); 
    public abstract void sortProcessList();
    public abstract void calculateWaitingTime(process currentProcessInExecution); 
    public abstract void calculateTurnAroundTime(process currentProcessInExecution);
    public abstract void currentProcessInExecutionUpdate(process currentProcessInExecution);
    public abstract void isFinished();

}
