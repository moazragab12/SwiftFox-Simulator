package com.example.schedulers;

import java.util.*;
import java.util.function.Predicate;

public final class Simulator
{
    private final Scheduler scheduler;
    private final GanttChart ganttChart;
    private final List<Process> processes;
    private int timer;
    private volatile boolean isRunning;

    public Simulator(List<Process> processes, Scheduler scheduler, GanttChart ganttChart)
    {
        timer = 0;
        isRunning = false;
        this.scheduler = Objects.requireNonNull(scheduler);
        this.ganttChart = Objects.requireNonNull(ganttChart);
        this.processes = new ArrayList<>(Objects.requireNonNull(processes));
    }

    public synchronized void addProcess(Process process)
    {
        if (!isRunning) return;
        addToReadyQueue(process);
        processes.add(process);
    }

    public void start()
    {
        isRunning = true;
        while (!allProcessesTerminated())
        {
            tick();
            try
            {
                Thread.sleep(1000); // Control tick rate manually
            }
            catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
        isRunning = false;
    }

    private boolean allProcessesTerminated()
    {
        return processes.stream()
                .allMatch(p -> p.getState() == Process.ProcessState.TERMINATED);
    }

    private void tick()
    {
        if (!isRunning) return;

        checkNewArrivals();

        Process next = scheduler.decideNextProcess();
        if (next != null)
        {
            ganttChart.addEntry(next, timer, timer + 1);
            next.execute(1);
            scheduler.onProcessCompleted(next);
        }
        else
            ganttChart.addIdleEntry(timer, timer + 1);

        timer++;
    }

    private void checkNewArrivals()
    {
        Predicate<Process> isArrived = p ->
                p.getArrivalTime() <= timer &&
                        p.getState() == Process.ProcessState.NEW;

        processes.stream()
                .filter(isArrived)
                .forEach(this::addToReadyQueue);

    }

    private void addToReadyQueue(Process process)
    {
        process.setReady();
        scheduler.addProcess(process);
    }


}