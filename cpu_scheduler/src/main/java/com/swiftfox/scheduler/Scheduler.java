package com.swiftfox.scheduler;
import com.swiftfox.model.Process;

import java.util.Queue;

public abstract class Scheduler
{
    protected Queue<com.swiftfox.model.Process> readyQueue;
    protected com.swiftfox.model.Process currentProcess;

    public Scheduler(Queue<com.swiftfox.model.Process> readyQueue)
    {
        this.readyQueue = readyQueue;
        this.currentProcess = null;
    }

    public void addProcess(com.swiftfox.model.Process process)
    {
        if ( readyQueue != null && process.getState() == Process.State.READY)
            readyQueue.add(process);

    }

    public abstract com.swiftfox.model.Process decideNextProcess();

    public abstract void onProcessCompleted(Process process);
}