package com.example.schedulers;

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
    public Process decideNextProcess()
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
    public void onProcessCompleted(Process process)
    {
        if (process.getState() == Process.ProcessState.TERMINATED)
            currentProcess = null; // Clear current if terminated

        remainingQuantum--;
    }
}