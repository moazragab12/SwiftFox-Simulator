package com.example.schedulers;

 import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class FCFS extends Scheduler
{
    public FCFS()
    {
        this.readyQueue = new LinkedList<>();
    }
    @Override
    public void initialize(List<Process> processes)
    {
        processes.stream()
                .sorted(Comparator.comparingInt(Process::getArrivalTime))
                .forEach(this::addProcess);
    }

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
