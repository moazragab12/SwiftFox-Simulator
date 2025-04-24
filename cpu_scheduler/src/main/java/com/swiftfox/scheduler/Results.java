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

public final class Results
{
    private final List<ProcessMetrics> processMetrics = new ArrayList<>();

    public Results(GanttChart ganttChart)
    {
        calculateMetrics(ganttChart);
    }

    private void calculateMetrics(GanttChart ganttChart)
    {
        // Define the predicate to check if the process is terminated
        Predicate<GanttEntry> isTerminatedProcess = entry ->
                entry.getProcess() != null
                        && entry.getProcess().getState() == Process.State.TERMINATED;

        // Using streams to filter, map, and collect the results
        Map<com.swiftfox.model.Process, Integer> completionTimes = ganttChart.getEntries().stream()
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


        public List<ProcessMetrics> getAllMetrics ()
        {
            return new ArrayList<>(processMetrics);
        }

        public double getAverageWaitingTime ()
        {
            return processMetrics.stream()
                    .mapToInt(ProcessMetrics::waitingTime)
                    .average()
                    .orElse(0.0);
        }

        public double getAverageTurnaroundTime ()
        {
            return processMetrics.stream()
                    .mapToInt(ProcessMetrics::turnaroundTime)
                    .average()
                    .orElse(0.0);
        }


        private void addProcessMetrics (com.swiftfox.model.Process process, int finish)
        {
            int turnaround = finish - process.getArrivalTime();
            int waiting = turnaround - process.getBurstTime();
            process.setTurnaroundTime(turnaround);
            process.setWaitingTime(waiting);
            processMetrics.add(new ProcessMetrics(process, turnaround, waiting));
        }

        public Map<com.swiftfox.model.Process, Integer> getProcessWaitingTimes() {
            return processMetrics.stream()
                                 .collect(Collectors.toMap(
                                     ProcessMetrics::getProcess, // Key: Process
                                     ProcessMetrics::waitingTime // Value: Waiting time
                                 ));
        }

        public Map<Process, Integer> getProcessTurnaroundTimes() {
            return processMetrics.stream()
                                 .collect(Collectors.toMap(
                                     ProcessMetrics::getProcess, // Key: Process
                                     ProcessMetrics::turnaroundTime // Value: Waiting time
                                 ));
        }
        
    }