package com.example.schedulers;

import java.util.ArrayList;

public class SJF extends scheduler {
    public SJF(ArrayList<process> processList, boolean isPreemptive) {
        super(processList, isPreemptive);
    }


    @Override
    public ArrayList<GantChartUnit> schedule() {
        if (isPreemptive() == true)
           return preemptiveSJF(getProcessList());
        else
           return nonpreemptiveSJF(getProcessList());
    }


    public ArrayList<GantChartUnit> handleGantchart(ArrayList<process> proccesList) {
        ArrayList<GantChartUnit> newGanttChartList = new ArrayList<>();
        for (process p : proccesList) {
            if (newGanttChartList.isEmpty()) {
                newGanttChartList.add(new GantChartUnit(p,1));
            } else if (p.getId() != newGanttChartList.get(newGanttChartList.size() - 1).getProcess().getId()) {
                newGanttChartList.add(new GantChartUnit(p,1));
            } else{
                newGanttChartList.get(newGanttChartList.size() - 1).setTime(1 + newGanttChartList.get(newGanttChartList.size() - 1).getTime());
            }
        }
        return newGanttChartList;
    }


    private ArrayList<GantChartUnit> nonpreemptiveSJF(ArrayList<process> processList) {
        int n = getProcessCount();
        int time = 0, completed = 0;
        boolean[] isCompleted = new boolean[n];
        ArrayList<process> gantt = new ArrayList<>();
        process pIdle = new process(null, 0, 0);

        while (completed < n) {
            int idx = -1;
            int minBurst = Integer.MAX_VALUE;
            setStatus(false);

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
                setStatus(true);
                processList.get(idx).setStartTime(time);
                time += processList.get(idx).getBurstTime();
                processList.get(idx).setCompletionTime(time);
                processList.get(idx).setTurnaroundTime(processList.get(idx).getCompletionTime() - processList.get(idx).getArrivalTime());
                processList.get(idx).setWaitingTime(processList.get(idx).getTurnaroundTime() - processList.get(idx).getBurstTime());

                isCompleted[idx] = true;
                processList.get(idx).setRemainingTime(0);
                completed++;
                for (int t = 0; t < processList.get(idx).getBurstTime(); t++) gantt.add(processList.get(idx));
            } else {
                gantt.add(pIdle); // idle
                time++;
            }
        }

        //printResults(processList, gantt);
        return handleGantchart(gantt);
    }


    private ArrayList<GantChartUnit> preemptiveSJF(ArrayList<process> processList) {
        int n = getProcessCount();
        int time = 0, completed = 0;
        ArrayList<process> gantt = new ArrayList<>();
        process pIdle = new process(null, 0, 0);

        while (completed < n) {
            int idx = -1;
            int minRemaining = Integer.MAX_VALUE;
            setStatus(false);

            for (int i = 0; i < n; i++) {
                if (processList.get(i).getArrivalTime() <= time && processList.get(i).getRemainingTime() > 0) {
                    if (processList.get(i).getRemainingTime() < minRemaining) {
                        minRemaining = processList.get(i).getRemainingTime();
                        idx = i;
                    } else if (processList.get(i).getRemainingTime() == minRemaining) {
                        if (processList.get(i).getArrivalTime() < processList.get(idx).getArrivalTime()) {
                            idx = i;
                        }
                    }
                }
            }

            if (idx != -1) {
                setStatus(true);
                if (processList.get(idx).getStartTime() == -1)
                    processList.get(idx).setStartTime(time);
                processList.get(idx).setRemainingTime(processList.get(idx).getRemainingTime()-1);
                gantt.add(processList.get(idx));

                if (processList.get(idx).getRemainingTime() == 0) {
                    processList.get(idx).setCompletionTime(time + 1);
                    processList.get(idx).setTurnaroundTime(processList.get(idx).getCompletionTime() - processList.get(idx).getArrivalTime());
                    processList.get(idx).setWaitingTime(processList.get(idx).getTurnaroundTime() - processList.get(idx).getBurstTime());
                    completed++;
                }
            } else {
                setStatus(false);
                gantt.add(pIdle);
            }

            time++;
        }

        //printResults(processList, gantt);
        return handleGantchart(gantt);
    }

    //for testing purpose only
    // static void printResults(ArrayList<process> processList, ArrayList<process> gantt) {
    //     System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
    //     for (process p : processList) {
    //         System.out.printf("P%d\t%d\t%d\t%d\t%d\t%d\n",
    //             p.getId(), p.getArrivalTime(), p.getBurstTime(), p.getCompletionTime(),
    //             p.getTurnaroundTime(), p.getWaitingTime());
    //     }

    //     System.out.println("\nGantt Chart:");
    //     for (process p : gantt) {
    //         System.out.print(p.getName() == null ? "Idle " : "P" + p.getId() + " ");
    //     }
    //     System.out.println();
    // }


    // public static void main(String[] args) {
    //     ArrayList<process> processList = new ArrayList<>();
    //     processList.add(new process("P1", 0, 10, 2));
    //     processList.add(new process("P2", 1, 5, 1));
    //     processList.add(new process("P3", 2, 8, 3));
    //     processList.add(new process("P4", 3, 6, 2));
    //     processList.add(new process("P5", 4, 7, 1));
    //     processList.add(new process("P6", 5, 4, 3));
    //     processList.add(new process("P7", 6, 3, 2));
    //     processList.add(new process("P8", 50, 2, 1));
    //     SJF scheduler = new SJF(processList, false);
    //     //SJF scheduler = new SJF(processList, true);
    //     ArrayList<GantChartUnit> ganttChart = scheduler.schedule();

    //     for (GantChartUnit unit : ganttChart) {
    //         System.out.println("Process: " + unit.getProcess().getName() + ", Time: " + unit.getTime());
    //     }
    //     System.out.printf("\nAverage Turnaround Time: %.2f\n",scheduler.calculateAverageTurnAroundTime());
    //     System.out.printf("Average Waiting Time: %.2f\n",scheduler.calculateAverageWaitingTime());
    //     System.out.println("All processes finished: " + scheduler.isFinished() + "\n"); //??????????
    // }
}