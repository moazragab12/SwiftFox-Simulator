package com.example.schedulers;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        // Create processes
        List<Process> processes = new ArrayList<>();

        processes.add(new Process("P1", 0, 4, 0));
        processes.add(new Process("P2", 12, 1, 2));

        // Initialize components
        GanttChart chart = new GanttChart();
        Scheduler scheduler = new Priority(false); // Round Robin with quantum=2
        Simulator simulator = new Simulator(processes, scheduler, chart);

        simulator.runStatic();
        simulator.runLive();

        // Get results
        Results results = new Results(chart);

        // Output results
        System.out.println("=== Simulation Results ===");
        System.out.println("Gantt Chart:");
        chart.getEntries().forEach(System.out::println);

        System.out.println("\nProcess Metrics:");
        results.getAllMetrics().forEach(System.out::println);

        System.out.printf("Average Turnaround Time: %.2f\n", results.getAverageTurnaroundTime());
        System.out.printf("\nAverage Waiting Time: %.2f\n", results.getAverageWaitingTime());
    }
}
