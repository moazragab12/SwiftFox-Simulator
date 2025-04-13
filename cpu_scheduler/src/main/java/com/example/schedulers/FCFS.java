package com.example.schedulers;

import java.util.LinkedList;

public class FCFS extends Scheduler
{
    public FCFS() { super(new LinkedList<>());}

    @Override
    public Process decideNextProcess()
    {
        if (currentProcess != null)
            return currentProcess;

        currentProcess = readyQueue.poll();
        return currentProcess;
    }

    @Override
    public void onProcessCompleted(Process process)
    {
        if (process.getState() == Process.ProcessState.TERMINATED)
            currentProcess = null;
    }
}
