package com.example.schedulers;

import java.util.List;
import java.util.Queue;

public abstract class Scheduler
{
    protected Queue<Process> readyQueue;
    protected Process currentProcess;

    public void addProcess(Process process)
    {
        if ( readyQueue != null && process.getState() == Process.ProcessState.READY)
            readyQueue.add(process);

    }

    public abstract void initialize(List<Process> processes);

    public abstract Process decideNextProcess();

    public abstract void onProcessCompleted(Process process);
}