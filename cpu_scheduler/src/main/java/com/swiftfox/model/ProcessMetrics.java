package com.swiftfox.model;
/**
 * A record representing performance metrics of a process,
 * including turnaround time and waiting time.
 *
 * @param process the associated process
 * @param turnaroundTime total turnaround time for the process
 * @param waitingTime total waiting time for the process
 */

public record ProcessMetrics(
        Process process,
        int turnaroundTime,
        int waitingTime
) {
    /**
     * Returns a string representation of the process metrics.
     *
     * @return a formatted string with process name, turnaround time, and waiting time
     */
    @Override
    public String toString() {
        return String.format("%s: Turnaround=%d, Waiting=%d",
                process.getName(), turnaroundTime, waitingTime);
    }
    /**
     * Constructs a new ProcessMetrics object with the specified process,
     * turnaround time, and waiting time.
     *
     * @param process the process this metric is associated with
     * @param turnaroundTime the total turnaround time of the process
     * @param waitingTime the total waiting time of the process
     */
    public ProcessMetrics(Process process, int turnaroundTime, int waitingTime) {
        this.process = process;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

    /**
     * Returns the process this metric belongs to.
     *
     * @return the process
     */
    public Process getProcess() {
        return process;
    }
    /**
     * Returns the turnaround time of the process.
     *
     * @return the turnaround time
     */
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    /**
     * Returns the waiting time of the process.
     *
     * @return the waiting time
     */
    public int waitingTime() {
        return waitingTime;
    }

}