package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.LinkedList;
/**
 * First-Come, First-Served (FCFS) scheduling algorithm implementation.
 * Processes are scheduled in the order they arrive.
 */
public class FCFS extends Scheduler
{ /**
 * Constructs an FCFS scheduler with an empty ready queue.
 */
    public FCFS() { super(new LinkedList<>());}
    /**
     * Decides the next process to run using FCFS strategy.
     * If a process is already running, it continues running.
     * Otherwise, the next process is dequeued from the front of the ready queue.
     *
     * @return the next {@link Process} to run, or {@code null} if no process is ready
     */
    @Override
    public Process decideNextProcess()
    {
        if (currentProcess != null)
            return currentProcess;

        currentProcess = readyQueue.poll();
        return currentProcess;
    }
    /**
     * Handles process completion.
     * If the process has terminated, it resets the current process to {@code null}.
     *
     * @param process the {@link Process} that has completed
     */
    @Override
    public void onProcessCompleted(Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null;
    }
}
