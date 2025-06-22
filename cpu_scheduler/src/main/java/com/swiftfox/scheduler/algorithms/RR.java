package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.LinkedList;
/**
 * Round Robin (RR) scheduling algorithm implementation.
 * Each process is given a fixed time quantum for execution in a cyclic order.
 */
public class RR extends Scheduler
{
    private final int timeQuantum;
    private int remainingQuantum;
    /**
     * Constructs a Round Robin scheduler with the specified time quantum.
     *
     * @param timeQuantum the fixed time slice for each process
     */
    public RR(int timeQuantum)
    {
        super(new LinkedList<>());
        this.timeQuantum = timeQuantum;
    }
    /**
     * Decides the next process to run based on Round Robin logic.
     * If the current process has exhausted its quantum, it is re-added to the queue.
     *
     * @return the selected {@link Process}, or {@code null} if no process is ready
     */
    @Override
    public com.swiftfox.model.Process decideNextProcess()
    {

        // Handle quantum expiration
        if (currentProcess != null && remainingQuantum <= 0)
        {
            currentProcess.preempt(); // Proper state transition
            readyQueue.add(currentProcess);
            currentProcess = null;
        }

        // Get next process if needed
        if (currentProcess == null)
        {
            currentProcess = readyQueue.poll();
            remainingQuantum = timeQuantum;
        }

        return currentProcess;
    }
    /**
     * Handles actions after a process completes its current time unit.
     * If the process terminates, it is removed from the CPU.
     * Otherwise, its quantum is decremented.
     *
     * @param process the {@link Process} that has just executed
     */
    @Override
    public void onProcessCompleted(com.swiftfox.model.Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null; // Clear current if terminated

        remainingQuantum--;
    }
}