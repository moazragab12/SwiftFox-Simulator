package com.swiftfox.scheduler.algorithms;

import com.swiftfox.model.Process;
import com.swiftfox.scheduler.Scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF extends Scheduler
{
    boolean preemptive;

    public SJF(boolean preemptive) {
        super(new PriorityQueue<>(buildComparator(preemptive))); // like Priority constructor
        this.preemptive = preemptive;
    }

    private static Comparator<com.swiftfox.model.Process> buildComparator(boolean preemptive) {
        Comparator<com.swiftfox.model.Process> comparator = preemptive
                ? Comparator.comparingInt(com.swiftfox.model.Process::getRemainingTime)
                : Comparator.comparingInt(com.swiftfox.model.Process::getBurstTime);

        return comparator
                .thenComparingInt(com.swiftfox.model.Process::getArrivalTime)
                .thenComparingInt(com.swiftfox.model.Process::getPid);
    }

    @Override
    public com.swiftfox.model.Process decideNextProcess() {
        if (preemptive)
        {
            // If there's a running process and a different one with shorter remaining time arrives
            com.swiftfox.model.Process shortestProcess = readyQueue.peek();
            if (currentProcess != null && shortestProcess != null && currentProcess != shortestProcess )
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
