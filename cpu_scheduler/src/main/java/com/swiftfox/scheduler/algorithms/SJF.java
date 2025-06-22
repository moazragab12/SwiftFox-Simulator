package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
/**
 * Shortest Job First (SJF) scheduling algorithm.
 * Supports both preemptive (Shortest Remaining Time First) and non-preemptive variants.
 */
public class SJF extends Scheduler
{
    boolean preemptive;
    /**
     * Constructs an SJF scheduler.
     *
     * @param preemptive {@code true} for preemptive (SRTF), {@code false} for non-preemptive SJF
     */
    public SJF(boolean preemptive) {
        super(new PriorityQueue<>(buildComparator(preemptive))); // like Priority constructor
        this.preemptive = preemptive;
    }
    /**
     * Builds the comparator used for ordering processes in the priority queue.
     * Preemptive mode uses remaining time; non-preemptive uses burst time.
     *
     * @param preemptive whether to build the comparator for preemptive SJF
     * @return a comparator for ordering {@link Process} instances
     */
    private static Comparator<com.swiftfox.model.Process> buildComparator(boolean preemptive) {
        Comparator<com.swiftfox.model.Process> comparator = preemptive
                ? Comparator.comparingInt(com.swiftfox.model.Process::getRemainingTime)
                : Comparator.comparingInt(com.swiftfox.model.Process::getBurstTime);

        return comparator
                .thenComparingInt(com.swiftfox.model.Process::getArrivalTime)
                .thenComparingInt(com.swiftfox.model.Process::getPid);
    }
    /**
     * Determines the next process to run.
     * In preemptive mode, interrupts the current process if a shorter one is available.
     *
     * @return the next {@link Process} to run, or {@code null} if none are available
     */
    @Override
    public com.swiftfox.model.Process decideNextProcess() {
        if (preemptive)
        {
            // If there's a running process and a different one with shorter remaining time arrives
            com.swiftfox.model.Process shortestProcess = readyQueue.peek();
            if (currentProcess != null && shortestProcess != null && currentProcess != shortestProcess )
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
     * Called after a process completes.
     * If the process has terminated, it is removed from CPU.
     *
     * @param process the process that just finished a time unit
     */
    @Override
    public void onProcessCompleted(com.swiftfox.model.Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null;
    }
}
