package com.example.schedulers;
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


    public abstract Process decideNextProcess();

    public abstract void onProcessCompleted(Process process);
}