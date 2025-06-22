package com.swiftfox.scheduler;

import com.swiftfox.model.GanttChart;
import com.swiftfox.model.GanttEntry;
import com.swiftfox.model.Process;
import com.swiftfox.model.ProcessMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
/**
 * A utility class that analyzes a {@link GanttChart} to compute metrics such as
 * waiting time and turnaround time for each {@link Process}.
 * <p>
 * These results are typically used for displaying scheduling performance.
 */
public final class Results
{
    private final List<ProcessMetrics> processMetrics = new ArrayList<>();

    /**
     * Constructs a {@code Results} instance and computes the process metrics from the given {@link GanttChart}.
     *
     * @param ganttChart the Gantt chart from which metrics will be calculated
     */
    public Results(GanttChart ganttChart)
    {
        calculateMetrics(ganttChart);
    }

    /**
     * Computes the waiting and turnaround times for all completed processes based on the Gantt chart.
     *
     * @param ganttChart the Gantt chart containing process execution data
     */
    private void calculateMetrics(GanttChart ganttChart)
    {
        // Define the predicate to check if the process is terminated
        Predicate<GanttEntry> isTerminatedProcess = entry ->
                entry.getProcess() != null
                        && entry.getProcess().getState() == Process.State.TERMINATED;

        // Using streams to filter, map, and collect the results
        Map<Process, Integer> completionTimes = ganttChart.getEntries().stream()
                .filter(isTerminatedProcess)  // Use the predicate for filtering
                .collect(Collectors.toMap(
                        GanttEntry::getProcess,
                        entry -> (int) entry.getEndTime(),
                        Math::max // In case of duplicates
                ));

        // Calculate metrics in a single stream operation
        completionTimes.forEach(this::addProcessMetrics);

        /*
        The map would look like:
        completionTimes =
        {
            Process{id=1, name="Process A"} -> 50,
            Process{id=2, name="Process B"} -> 70,
            Process{id=3, name="Process C"} -> 45
         }
        */
    }
    /**
     * Returns a list of all process metrics (turnaround and waiting times).
     *
     * @return a new list of {@link ProcessMetrics}
     */

        public List<ProcessMetrics> getAllMetrics ()
        {
            return new ArrayList<>(processMetrics);
        }
    /**
     * Calculates the average waiting time of all processes.
     *
     * @return the average waiting time, or {@code 0.0} if no processes exist
     */
        public double getAverageWaitingTime ()
        {
            return processMetrics.stream()
                    .mapToInt(ProcessMetrics::waitingTime)
                    .average()
                    .orElse(0.0);
        }
    /**
     * Calculates the average turnaround time of all processes.
     *
     * @return the average turnaround time, or {@code 0.0} if no processes exist
     */
        public double getAverageTurnaroundTime ()
        {
            return processMetrics.stream()
                    .mapToInt(ProcessMetrics::turnaroundTime)
                    .average()
                    .orElse(0.0);
        }


    /**
     * Adds a single process's metrics to the internal list.
     *
     * @param process the process to evaluate
     * @param finish  the time the process finished execution
     */
        private void addProcessMetrics (Process process, int finish)
        {
            int turnaround = finish - process.getArrivalTime();
            int waiting = turnaround - process.getBurstTime();
            process.setTurnaroundTime(turnaround);
            process.setWaitingTime(waiting);
            processMetrics.add(new ProcessMetrics(process, turnaround, waiting));
        }
    /**
     * Returns a map of each process to its calculated waiting time.
     *
     * @return a map of {@link Process} to waiting time
     */
        public Map<Process, Integer> getProcessWaitingTimes() {
            return processMetrics.stream()
                                 .collect(Collectors.toMap(
                                     ProcessMetrics::getProcess, // Key: Process
                                     ProcessMetrics::waitingTime // Value: Waiting time
                                 ));
        }
    /**
     * Returns a map of each process to its calculated turnaround time.
     *
     * @return a map of {@link Process} to turnaround time
     */
        public Map<Process, Integer> getProcessTurnaroundTimes() {
            return processMetrics.stream()
                                 .collect(Collectors.toMap(
                                     ProcessMetrics::getProcess, // Key: Process
                                     ProcessMetrics::turnaroundTime // Value: Waiting time
                                 ));
        }
        
    }