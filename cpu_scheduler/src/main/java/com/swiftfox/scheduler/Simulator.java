package com.swiftfox.scheduler;

import com.swiftfox.model.GanttChart;
import com.swiftfox.model.Process;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
/**
 * Simulates the execution of scheduling algorithms over a list of {@link Process}es.
 * <p>
 * This class integrates a {@link Scheduler} with a {@link GanttChart} to visualize
 * process execution and collect timing information. It supports both live and static simulations.
 */
public final class Simulator
{
    private final Scheduler scheduler;
    private final GanttChart ganttChart;
    private final List<Process> processes;
    private int timer;
    private volatile boolean isRunning;
    private final SimpleIntegerProperty simpleIntegerProperty;
    /**
     * Constructs a Simulator instance.
     *
     * @param processes   the list of processes to simulate
     * @param scheduler   the scheduler to use (e.g., FCFS, SJF)
     * @param ganttChart  the Gantt chart to populate with execution entries
     * @throws NullPointerException if any argument is null
     */
    public Simulator(List<Process> processes, Scheduler scheduler, GanttChart ganttChart)
    {
        timer = 0;
        isRunning = false;
        this.scheduler = Objects.requireNonNull(scheduler);
        this.ganttChart = Objects.requireNonNull(ganttChart);
        this.processes = new ArrayList<>(Objects.requireNonNull(processes));
        this.simpleIntegerProperty = new SimpleIntegerProperty();
    }
    /**
     * Adds a process to the simulator. If simulation is running, the process is moved to the ready queue.
     *
     * @param process the process to add
     */
    public synchronized void addProcess(Process process)
    {
        if (isRunning) addToReadyQueue(process);
        processes.add(process);
    }
    /**
     * Starts the simulation in live mode. Each tick waits 1 second (1000 ms).
     */
    public void runLive()
    {
        run(true);
    }

    /**
     * Starts the simulation in static mode. The simulation runs without delay between ticks.
     */
    public void runStatic()
    {
        run(false);
    }

    /// //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Runs the simulation loop until all processes are terminated.
     * <p>
     * Each iteration represents a CPU time unit ("tick") where the simulator:
     * <ul>
     *   <li>Checks for newly arrived processes and moves them to the ready queue</li>
     *   <li>Asks the scheduler for the next process to run</li>
     *   <li>Executes the selected process or adds an idle slot if none is ready</li>
     * </ul>
     * If {@code live} is {@code true}, the simulator waits 1 second between ticks
     * to simulate real-time execution. If {@code false}, the simulation runs as fast as possible.
     *
     * @param live whether to simulate in real-time (with 1-second delay per tick)
     */
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
    /**
     * Pauses the current thread for the specified duration.
     * <p>
     * This is used to control the tick rate of the simulation when running in real-time mode.
     *
     * @param timeInMs the amount of time to sleep in milliseconds
     * @throws RuntimeException if the thread is interrupted during sleep
     */
    private void sleep(int timeInMs)
    {
        try {Thread.sleep(timeInMs);} // Control tick rate manually
        catch (InterruptedException e) {throw new RuntimeException(e);}
    }
    /**
     * Returns whether all processes in the simulation have terminated.
     *
     * @return true if all processes have state TERMINATED; false otherwise
     */
    public boolean allProcessesTerminated()
    {
        return processes.stream().allMatch(p -> p.getState() == Process.State.TERMINATED);
    }
    /**
     * Advances the simulation by one time unit (tick).
     * <p>
     * This method performs the following in order:
     * <ul>
     *   <li>Checks for newly arrived processes and adds them to the ready queue.</li>
     *   <li>Uses the scheduler to select the next process to run.</li>
     *   <li>Executes the selected process for one time unit, updating its state and the Gantt chart.</li>
     *   <li>If no process is available, records an idle slot in the Gantt chart.</li>
     *   <li>Increments the internal timer.</li>
     * </ul>
     * If the simulation is not currently running, the method returns immediately.
     */
    private void tick()
    {
        if (!isRunning) return;

        checkNewArrivals();
        Process next = scheduler.decideNextProcess();

        if (next != null)
        {
            ganttChart.addEntry(next, timer, timer + 1);
            next.execute(1);

            simpleIntegerProperty.set(next.getRemainingTime());

            scheduler.onProcessCompleted(next);
        }
        else ganttChart.addIdleEntry(timer, timer + 1);

        timer++;
    }
    /**
     * Checks for newly arrived processes at the current simulation time.
     * <p>
     * A process is considered "arrived" if its arrival time is less than or equal to the current timer
     * and its state is {@code NEW}. Such processes are moved to the ready queue by changing their state
     * to {@code READY} and passing them to the scheduler.
     */
    private void checkNewArrivals()
    {
        Predicate<Process> isArrived = p -> p.getArrivalTime() <= timer && p.getState() == Process.State.NEW;

        processes.stream().filter(isArrived).forEach(this::addToReadyQueue);

    }
    /**
     * Moves a process to the ready queue if it is in the {@code NEW} state.
     * <p>
     * This method updates the process state to {@code READY} and adds it to the scheduler's ready queue.
     * If the process is not in the {@code NEW} state, it is ignored.
     *
     * @param process the process to be added to the ready queue
     */
    private void addToReadyQueue(Process process)
    {

        if (process.getState() != Process.State.NEW) return;

        process.setReady();
        scheduler.addProcess(process);
    }


}