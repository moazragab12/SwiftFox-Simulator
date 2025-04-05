package com.example.schedulers;

import java.util.ArrayList;
import java.util.List;

public class SJF extends scheduler {
    public SJF(ArrayList<process> processList, boolean isPreemptive) {
        super(processList, isPreemptive);
    }

    @Override
    public ArrayList<GantChartUnit> schedule() {
        if (isPreemptive() == true) {
            preemptiveSJF(getProcessList());
        } else if (isPreemptive() == false) {
            nonpreemptiveSJF(getProcessList());
        }
        return getGanttChartList();
    }

    private void nonpreemptiveSJF(ArrayList<process> processList) {
        int n = getProcessCount();
        int time = 0, completed = 0;
        boolean[] isCompleted = new boolean[n];
        List<Integer> gantt = new ArrayList<>();

        while (completed < n) {
            int idx = -1;
            int minBurst = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (processList.get(i).getArrivalTime() <= time && !isCompleted[i]) {
                    if (processList.get(i).getBurstTime() < minBurst) {
                        minBurst = processList.get(i).getBurstTime();
                        idx = i;
                    } else if (processList.get(i).getBurstTime() == minBurst) {
                        if (processList.get(i).getArrivalTime() < processList.get(idx).getArrivalTime()) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                processList.get(idx).setStartTime(time);
                time += processList.get(idx).getBurstTime();
                processList.get(idx).setCompletionTime(time);
                processList.get(idx).setTurnaroundTime(processList.get(idx).getCompletionTime() - processList.get(idx).getArrivalTime());
                processList.get(idx).setWaitingTime(processList.get(idx).getTurnaroundTime() - processList.get(idx).getBurstTime());

                isCompleted[idx] = true;
                completed++;
                for (int t = 0; t < processList.get(idx).getBurstTime(); t++) gantt.add(processList.get(idx).getId());
            } else {
                gantt.add(0); // idle
                time++;
            }
        }

        printResults(processList, gantt);
    }

    private void preemptiveSJF(ArrayList<process> processList) {
    }

    static void printResults(ArrayList<process> processList, List<Integer> gantt) {
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        double totalTAT = 0, totalWT = 0;
        for (process p : processList) {
            System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
                p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getCompletionTime(),
                p.getTurnaroundTime(), p.getWaitingTime());
            totalTAT += p.getTurnaroundTime();
            totalWT += p.getWaitingTime();
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", totalTAT / processList.size());
        System.out.printf("Average Waiting Time: %.2f\n", totalWT / processList.size());

        System.out.println("\nGantt Chart:");
        for (int pid : gantt) {
            System.out.print(pid == 0 ? "Idle " : "P" + pid + " ");
        }
        System.out.println();
    }

    @Override
    public void fitchProcess() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fitchProcess'");
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
        ArrayList<process> processList = new ArrayList<>();
        processList.add(new process("P1", 0, 10, 2));
        processList.add(new process("P2", 1, 5, 1));
        processList.add(new process("P3", 2, 8, 3));
        processList.add(new process("P4", 3, 6, 2));
        processList.add(new process("P5", 4, 7, 1));
        processList.add(new process("P6", 5, 4, 3));
        processList.add(new process("P7", 6, 3, 2));
        processList.add(new process("P8", 7, 2, 1));
        SJF scheduler = new SJF(processList, false);
        System.out.println(scheduler.getStatus());
        ArrayList<GantChartUnit> ganttChart = scheduler.schedule();
        System.out.println(scheduler.getStatus());

        for (GantChartUnit unit : ganttChart) {
            System.out.println("Process: " + unit.getProcess().getName() + ", Time: " + unit.getTime());
        }
        System.out.println(scheduler.calculateAverageTurnAroundTime());
        System.out.println(scheduler.calculateAverageWaitingTime());
    }
}
