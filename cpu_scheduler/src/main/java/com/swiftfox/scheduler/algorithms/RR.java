package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.LinkedList;

public class RR extends Scheduler
{
    private final int timeQuantum;
    private int remainingQuantum;

    public RR(int timeQuantum)
    {
        super(new LinkedList<>());
        this.timeQuantum = timeQuantum;
    }

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

    @Override
    public void onProcessCompleted(com.swiftfox.model.Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null; // Clear current if terminated

        remainingQuantum--;
    }
}