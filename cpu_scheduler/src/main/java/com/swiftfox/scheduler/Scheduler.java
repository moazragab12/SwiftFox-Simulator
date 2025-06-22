package com.swiftfox.scheduler;
import com.swiftfox.model.Process;

import java.util.Queue;
/**
 * Abstract base class representing a CPU scheduler.
 * Subclasses must implement specific scheduling strategies.
 */
public abstract class Scheduler
{
    protected Queue<Process> readyQueue;
    protected Process currentProcess;
    /**
     * Constructs a Scheduler with the specified ready queue.
     *
     * @param readyQueue the queue used to hold ready processes
     */
    public Scheduler(Queue<Process> readyQueue)
    {
        this.readyQueue = readyQueue;
        this.currentProcess = null;
    }
    /**
     * Adds a process to the ready queue if it is in the READY state.
     *
     * @param process the process to add
     */
    public void addProcess(Process process)
    {
        if ( readyQueue != null && process.getState() == Process.State.READY)
            readyQueue.add(process);

    }
    /**
     * Determines the next process to run according to the scheduling policy.
     *
     * @return the next process to run, or {@code null} if none are available
     */
    public abstract Process decideNextProcess();
    /**
     * Performs any actions required when a process completes execution.
     * This might include updating internal state or removing the process from tracking.
     *
     * @param process the completed process
     */
    public abstract void onProcessCompleted(Process process);
}