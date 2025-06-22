package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * Priority-based scheduling algorithm implementation.
 * Supports both preemptive and non-preemptive modes.
 */
public class Priority extends Scheduler
{
    boolean preemptive;
    /**
     * Constructs a Priority scheduler with the specified mode.
     *
     * @param preemptive {@code true} for preemptive scheduling, {@code false} for non-preemptive
     */
    public Priority(boolean preemptive)
    {
        super(new PriorityQueue<>(buildComparator()));
        this.preemptive = preemptive;
    }    /**
 * Builds the comparator used by the priority queue to order processes.
 * Orders by:
 * <ul>
 *     <li>Priority (ascending; lower value = higher priority)</li>
 *     <li>Arrival time (earlier first)</li>
 *     <li>Process ID (PID)</li>
 * </ul>
 *
 * @return a comparator for ordering {@link Process} instances
 */

    private static Comparator<com.swiftfox.model.Process> buildComparator() {
        return Comparator
                .comparingInt(com.swiftfox.model.Process::getPriority)
                .thenComparingInt(com.swiftfox.model.Process::getArrivalTime)
                .thenComparingInt(com.swiftfox.model.Process::getPid);
    }
    /**
     * Decides the next process to run based on priority.
     * In preemptive mode, the currently running process may be replaced if a higher-priority process arrives.
     *
     * @return the selected {@link Process}, or {@code null} if no process is ready
     */
    @Override
    public com.swiftfox.model.Process decideNextProcess()
    {
        if (preemptive)
        {
            com.swiftfox.model.Process highestPriorityProcess = readyQueue.peek();
            if (currentProcess != null && highestPriorityProcess != null && currentProcess != highestPriorityProcess )
            {
                currentProcess.preempt();
                readyQueue.add(currentProcess);
                currentProcess = null;
            }
        }

        // If no process is currently running, pick the next one
        if (currentProcess == null)
            currentProcess = readyQueue.poll();  // may be null if queue is empty

        return currentProcess;
    }
    /**
     * Handles process completion.
     * If the process has terminated, the current process is reset.
     *
     * @param process the {@link Process} that has completed
     */
    @Override
    public void onProcessCompleted(com.swiftfox.model.Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null;
    }
}
