package com.example.schedulers;

import java.util.ArrayList;

public class Pri extends scheduler {
    public Pri(ArrayList<process> processList, boolean isPreemptive) {
        super(processList, isPreemptive);
    }

    @Override
    public ArrayList<GantChartUnit> schedule() {
       process p = new process("idle", 0, 0);
        while(!isFinished()){
        fitchProcess();
        if(getCurrentProcessInExecution() == null){
            scheduler.setStatus(false);
            incrementCurrentTime(1);
            addToGanttChart(p, 1);
        }
        else{
        if(!isPreemptive()){
             
             //   System.out.println(scheduler.getStatus());
            incrementCurrentTime(getCurrentProcessInExecution().getBurstTime());
            //System.out.println(scheduler.getStatus());
            addToGanttChart(getCurrentProcessInExecution(), getCurrentProcessInExecution().getBurstTime());
            setCurrentProcessInExecution(null);
            
        }
        else{
           
             //   System.out.println(scheduler.getStatus());
                incrementCurrentTime(1);
             //   System.out.println(scheduler.getStatus());

                addToGanttChart(getCurrentProcessInExecution(), 1);
                if(getCurrentProcessInExecution().getRemainingTime() == 0){
                    setCurrentProcessInExecution(null);
                }
            
        }}
        }
        return handleGantchart(getGanttChartList());
    }
    public ArrayList<GantChartUnit> handleGantchart(ArrayList<GantChartUnit> ganttChartList) {
        ArrayList<GantChartUnit> newGanttChartList = new ArrayList<>();
        for (GantChartUnit ganttChartUnit : ganttChartList) {
            if (newGanttChartList.isEmpty()) {
                newGanttChartList.add(ganttChartUnit);
            } else if (ganttChartUnit.getProcess().getId() != newGanttChartList.get(newGanttChartList.size() - 1).getProcess().getId()) {
                newGanttChartList.add(ganttChartUnit);
            } else{
                newGanttChartList.get(newGanttChartList.size() - 1).setTime(ganttChartUnit.getTime() + newGanttChartList.get(newGanttChartList.size() - 1).getTime());
            }
        }
        return newGanttChartList;
    }

    
    public void fitchProcess() {
        for (process p : getProcessList()) {
            if (p.getArrivalTime() <= getTime()&& p.getRemainingTime() > 0) {
                if (getCurrentProcessInExecution() == null) {
                    setCurrentProcessInExecution(p);
                } else if (p.getPriority() < getCurrentProcessInExecution().getPriority()) {
                    setCurrentProcessInExecution(p);
                } else if (p.getPriority() == getCurrentProcessInExecution().getPriority()) {
                    if (p.getArrivalTime() < getCurrentProcessInExecution().getArrivalTime()) {
                        setCurrentProcessInExecution(p);
                    }
                }
                
            }
        }
    }
    // ******************test************************
    public static void main(String[] args) {
        System.out.println("Hello World!");
        ArrayList<process> processList = new ArrayList<>();
        processList.add(new process("P0", 0, 10, 2));
        processList.add(new process("P1", 1, 5, 1));
        processList.add(new process("P2", 2, 8, 3));
        processList.add(new process("P3", 3, 6, 2));
        processList.add(new process("P4", 4, 7, 1));
        processList.add(new process("P5", 5, 4, 3));
        processList.add(new process("P6", 6, 3, 2));
        processList.add(new process("P7", 50, 2, 1)); 
        Pri scheduler = new Pri(processList, true);
      //  System.out.println(scheduler.getStatus());
        ArrayList<GantChartUnit> ganttChart = scheduler.schedule();
      // System.out.println(scheduler.getStatus());

        for (GantChartUnit unit : ganttChart) {
            System.out.println("Process: " + unit.getProcess().getName() + ", Time: " + unit.getTime());
        }
        System.out.println(scheduler.calculateAverageTurnAroundTime());
        System.out.println(scheduler.calculateAverageWaitingTime());
        System.out.println(scheduler.isFinished());
    }

}
