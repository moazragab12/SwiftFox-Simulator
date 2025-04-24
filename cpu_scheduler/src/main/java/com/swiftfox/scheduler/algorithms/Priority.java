package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Priority extends Scheduler
{
    boolean preemptive;

    public Priority(boolean preemptive)
    {
        super(new PriorityQueue<>(buildComparator()));
        this.preemptive = preemptive;
    }

    private static Comparator<com.swiftfox.model.Process> buildComparator() {
        return Comparator
                .comparingInt(com.swiftfox.model.Process::getPriority)
                .thenComparingInt(com.swiftfox.model.Process::getArrivalTime)
                .thenComparingInt(com.swiftfox.model.Process::getPid);
    }

    @Override
    public com.swiftfox.model.Process decideNextProcess()
    {
        if (preemptive)
        {
            com.swiftfox.model.Process highestPriorityProcess = readyQueue.peek();
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
    public void onProcessCompleted(com.swiftfox.model.Process process)
    {
        if (process.getState() == Process.State.TERMINATED)
            currentProcess = null;
    }
}
