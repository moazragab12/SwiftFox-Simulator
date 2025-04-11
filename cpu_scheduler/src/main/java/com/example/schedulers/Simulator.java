package com.example.schedulers;

import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public final class Simulator
{
    private final Scheduler scheduler;
    private final GanttChart ganttChart;
    private final List<Process> processes;
    private int timer;
    private volatile boolean isRunning;
    private TableView<Process> table;

    public Simulator(List<Process> processes, Scheduler scheduler, GanttChart ganttChart)
    {
        timer = 0;
        isRunning = false;
        this.scheduler = Objects.requireNonNull(scheduler);
        this.ganttChart = Objects.requireNonNull(ganttChart);
        this.processes = new ArrayList<>(Objects.requireNonNull(processes));
        table= new TableView<>(); //added it because it was not initialized and gave null pointer exception

    }
    public Simulator(List<Process> processes, Scheduler scheduler, GanttChart ganttChart, TableView<Process> table)
    {
        timer = 0;
        isRunning = false;
        this.scheduler = Objects.requireNonNull(scheduler);
        this.ganttChart = Objects.requireNonNull(ganttChart);
        this.processes = new ArrayList<>(Objects.requireNonNull(processes));
        this.table = table;
    }

    public synchronized void addProcess(Process process)
    {
        if (isRunning)
            addToReadyQueue(process);
        processes.add(process);
    }

    public void runLive() {
        run(true);
    }

    public void runStatic() {
        run(false);
    }

    /// //////////////////////////////////////////////////////////////////////////////////////////
    private void run(boolean live)
    {
        isRunning = true;
        while (!allProcessesTerminated())
        {
            tick();
            if (live) sleep(1000);
        }
        isRunning = false;
    }

    private void sleep(int timeInMs)
    {
        try {Thread.sleep(timeInMs);} // Control tick rate manually
        catch (InterruptedException e) {throw new RuntimeException(e);}
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

            next.remainingTimeProperty().set(next.getRemainingTime());
            table.refresh();

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

        if (process.getState() != Process.ProcessState.NEW)
            return;

        process.setReady();
        scheduler.addProcess(process);
    }


}