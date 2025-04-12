package com.example.schedulers;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Priority extends Scheduler
{
    boolean preemptive;

    public Priority(boolean preemptive)
    {
        this.preemptive = preemptive;
        this.readyQueue = new PriorityQueue<>(Comparator
                .comparingInt(Process::getPriority)
                .thenComparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getPid));
    }


    @Override
    public Process decideNextProcess()
    {
        if (preemptive)
        {
            // If there's a running process and a different one with shorter remaining time arrives
            Process highestPriorityProcess = readyQueue.peek();
            if (currentProcess != null && highestPriorityProcess != null && currentProcess != highestPriorityProcess )
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

    @Override
    public void onProcessCompleted(Process process)
    {
        if (process.getState() == Process.ProcessState.TERMINATED)
            currentProcess = null;
    }
}
