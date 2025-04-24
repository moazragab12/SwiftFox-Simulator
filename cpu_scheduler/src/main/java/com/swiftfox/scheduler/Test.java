package com.swiftfox.scheduler;

import com.swiftfox.model.GanttChart;
import com.swiftfox.model.Process;
import com.swiftfox.scheduler.algorithms.Priority;

import java.util.ArrayList;
import java.util.List;

public class Test
{
    public static void testSimulator()
    {
        // Create processes
        List<com.swiftfox.model.Process> processes = new ArrayList<>();

        processes.add(new com.swiftfox.model.Process("P1", 0, 4, 0));
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
