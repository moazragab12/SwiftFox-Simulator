package com.example.schedulers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Results {
    private final List<ProcessMetrics> processMetrics = new ArrayList<>();

    public Results(GanttChart ganttChart) {
        calculateMetrics(ganttChart);
    }

    private void calculateMetrics(GanttChart ganttChart) {
        Map<Process, Integer> completionTimes = new HashMap<>();

        // Find when each process completed
        for (GanttEntry entry : ganttChart.getEntries()) {
            Process p = entry.getProcess();
            if (p != null && p.getState() == Process.ProcessState.TERMINATED) {
                completionTimes.put(p, (int)entry.getEndTime());
            }
        }

        // Calculate metrics
        for (Process p : completionTimes.keySet()) {
            int finish = completionTimes.get(p);
            int turnaround = finish - p.getArrivalTime();
            int waiting = turnaround - p.getBurstTime();
            processMetrics.add(new ProcessMetrics(p, turnaround, waiting));
        }
    }

    public List<ProcessMetrics> getAllMetrics() {
        return new ArrayList<>(processMetrics);
    }

    public double getAverageWaitingTime() {
        return processMetrics.stream()
                .mapToInt(ProcessMetrics::waitingTime)
                .average()
                .orElse(0.0);
    }

    public double getAverageTurnaroundTime() {
        return processMetrics.stream()
                .mapToInt(ProcessMetrics::turnaroundTime)
                .average()
                .orElse(0.0);
    }
}