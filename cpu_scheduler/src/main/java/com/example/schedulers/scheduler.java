package com.example.schedulers;

import java.util.ArrayList;
 
public abstract class scheduler {
    private ArrayList<process> processList;
    private ArrayList<GantChartUnit> GanttChartList = new ArrayList<>();
    private static boolean  status=false; // Assuming the scheduler is busy when true and idle when false
    private boolean isPreemptive; // Assuming the preemptive is true and non-preemptive is false
    private int time;
    private int processCount;
    private process currentProcessInExecution;

    public scheduler(ArrayList processList, boolean isPreemptive) {
        this.processList = processList;
        this.status = false; 
        this.isPreemptive = isPreemptive;
        this.time = 0;
        this.processCount = processList.size();
        this.currentProcessInExecution = null;
    }
    public ArrayList<process> getProcessList() {
        return processList;
    }
    public void setProcessList(ArrayList<process> processList) {
        this.processList = processList;
    }
    public ArrayList<GantChartUnit> getGanttChartList() {
        return GanttChartList;
    }
    public void setGanttChartList(ArrayList<GantChartUnit> ganttChartList) {
        GanttChartList = ganttChartList;
    }
    public static boolean getStatus() {
        return status;
    }
    public static void setStatus(boolean status) {
        scheduler.status = status;
    }
    public boolean isPreemptive() {
        return isPreemptive;
    }
    public void setPreemptive(boolean preemptive) {
        isPreemptive = preemptive;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int currentTime) {
        this.time = currentTime;
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
        this.time += timeUnit;
        if (currentProcessInExecution != null&& currentProcessInExecution.getRemainingTime()-timeUnit >= 0) {
            status = true; // Set status to busy when incrementing time
            // Decrease the remaining time of the current process in execution
            currentProcessInExecution.setRemainingTime(currentProcessInExecution.getRemainingTime() - timeUnit);
        }
        else   {
            System.out.println("no Process is  in execution or remaining time is negative.");
        }
        //  if u want change current process in execution to another process without the old process finishing its execution
        // if available u must before cahnge add it to gantt chart then change it to another process (it was always one time unit )
     
        //  if the current process has finished executing 
        if (currentProcessInExecution != null && currentProcessInExecution.getRemainingTime() == 0) {
            calculateTurnAroundTime(currentProcessInExecution);
            calculateWaitingTime(currentProcessInExecution);
            status = false; // Set status to idle when the process is finished
            // Add the current process to the Gantt chart
           // addToGanttChart(currentProcessInExecution, timer);
            if(isFinished()){
                //  System.out.println("All processes are finished.");
                // System.out.println("Average Waiting Time: " + calculateAverageWaitingTime());
                //  System.out.println("Average Turnaround Time: " + calculateAverageTurnAroundTime());
                status = false; // Set status to false when all processes are finished
            } else {
                // update the current process in execution to another process if available
            }
        } 
        
    }
  public void addToGanttChart(process process, int timeUnit) {
        //update timeUnit when id change only
         GantChartUnit gu= new GantChartUnit(process, timeUnit);
        GanttChartList.add(gu);
    }
    
    //implemented in Here
    public double  calculateAverageWaitingTime(){
        int totalWaitingTime = 0;
        for (process p : processList) {
            totalWaitingTime += p.getWaitingTime();
        }
        double averageWaitingTime = (double) totalWaitingTime / processCount;
        return averageWaitingTime;
        //System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
    public double  calculateAverageTurnAroundTime(){
        int totalTurnAroundTime = 0;
        for (process p : processList) {
            totalTurnAroundTime += p.getTurnaroundTime();
        }
        double averageTurnAroundTime = (double) totalTurnAroundTime / processCount;
        return averageTurnAroundTime;
       // System.out.println("Average Turnaround Time: " + averageTurnAroundTime);
    }
    public void calculateWaitingTime(process currentProcessInExecution){
        // Calculate waiting time for the current process in execution
        currentProcessInExecution.setWaitingTime(time - currentProcessInExecution.getArrivalTime() - currentProcessInExecution.getBurstTime());
    }
    public void calculateTurnAroundTime(process currentProcessInExecution){
            // Calculate turnaround time for the current process in execution
            currentProcessInExecution.setTurnaroundTime(time - currentProcessInExecution.getArrivalTime());
    }
    public boolean  isFinished(){
        for (process p : processList) {
            if (p.getRemainingTime() > 0) {
                return false; // Process is not finished
            }
        }
        status = false; // All processes are finished
        return true;
    }

    // Abstract method to be implemented by subclasses
    public abstract ArrayList<GantChartUnit> schedule(); 
    /*  *************how to run scheduler*************
      1) in the first line of the main method create an object from the scheduler class
         and pass the process list and the preemptive value to it.
        then call the schedule method to start the scheduling process according to algo u choose.
      2) then call fitsh process method to fitsh process based on the scheduling algorithm 
         and each time unit make sure  the process is choosed according to the arrival time of all the processes
        take  process and  set it as the current process in execution 
        and call the incrementCurrentTime method to update the time and take care  if u want to change the current process in execution to another process without the old process finishing its execution
        update the gantt chart with old process and time unit (it was always one time unit )and then change it to another process (it was always one time unit )
        (u don`t need priveous step in FCFS & RR) 
      3) when the current process in execution is finished call the calculateTurnAroundTime and calculateWaitingTime methods to update the waiting and turnaround times for the current process.
      4) when all processes are finished call the calculateAverageWaitingTime and calculateAverageTurnAroundTime methods to get the average waiting and turnaround times.
      5) u can also call the isFinished method to check if all processes are finished or not.
      6) u can also call the addToGanttChart method to add the current process to the Gantt chart with the time unit.
     */
}
