package com.swiftfox.model;
/**
 * Represents a single entry in the Gantt chart for the CPU scheduling simulation.
 * <p>
 * Each entry contains the process executed, its start time, and end time.
 * An entry with a {@code null} process indicates an idle CPU period.
 * </p>
 */

public final class GanttEntry {
    private final Process process;
    private final long startTime;
    private final long endTime;
    /**
     * Constructs a GanttEntry with the specified process and time interval.
     *
     * @param process   the process that was executed; {@code null} indicates an idle interval
     * @param startTime the start time of the execution period
     * @param endTime   the end time of the execution period
     */
    public GanttEntry(Process process, long startTime, long endTime) {
        this.process = process;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the process associated with this Gantt entry.
     *
     * @return the executed {@link Process}, or {@code null} if the CPU was idle
     */
    public Process getProcess() { return process; }
    /**
     * Returns the start time of the execution period.
     *
     * @return the start time in milliseconds or time units
     */
    public long getStartTime() { return startTime; }

    /**
     * Returns the end time of the execution period.
     *
     * @return the end time in milliseconds or time units
     */
    public long getEndTime() { return endTime; }
    /**
     * Returns a string representation of this Gantt entry.
     * Displays the process name or "IDLE" along with its time interval.
     *
     * @return formatted string representing the Gantt entry
     */
    @Override
    public String toString()
    {
        String processName = process == null ? "IDLE" : process.getName();
        return String.format("[%s %d-%d]", processName, startTime, endTime);
    }
}