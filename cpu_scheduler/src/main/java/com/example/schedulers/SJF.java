package com.example.schedulers;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF extends Scheduler
{
    boolean preemptive;

    public SJF(boolean preemptive) {
        super(new PriorityQueue<>(buildComparator(preemptive))); // like Priority constructor
        this.preemptive = preemptive;
    }

    private static Comparator<Process> buildComparator(boolean preemptive) {
        Comparator<Process> comparator = preemptive
                ? Comparator.comparingInt(Process::getRemainingTime)
                : Comparator.comparingInt(Process::getBurstTime);

        return comparator
                .thenComparingInt(Process::getArrivalTime)
                .thenComparingInt(Process::getPid);
    }

    @Override
    public Process decideNextProcess() {
        if (preemptive)
        {
            // If there's a running process and a different one with shorter remaining time arrives
            Process shortestProcess = readyQueue.peek();
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
    public void onProcessCompleted(Process process)
    {
        if (process.getState() == Process.ProcessState.TERMINATED)
            currentProcess = null;
    }
}
