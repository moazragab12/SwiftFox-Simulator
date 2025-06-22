package com.swiftfox.scheduler;

import com.swiftfox.model.GanttChart;
import com.swiftfox.model.Process;
import com.swiftfox.scheduler.algorithms.Priority;

import java.util.ArrayList;
import java.util.List;
/**
 * Runs a test simulation using the Priority scheduling algorithm (non-preemptive).
 * <p>
 * This method creates a small set of processes, runs both static and live simulations,
 * generates the Gantt chart, and prints the scheduling results including process metrics
 * and average turnaround/waiting times.
 * <p>
 * Note: {@code runLive()} includes a delay using {@code Thread.sleep()}, so the execution
 * will pause visibly in real-time.
 */
public class Test
{
    public static void testSimulator()
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
