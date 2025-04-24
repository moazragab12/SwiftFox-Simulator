package com.swiftfox.model;


public final class GanttEntry {
    private final Process process;
    private final long startTime;
    private final long endTime;

    public GanttEntry(Process process, long startTime, long endTime) {
        this.process = process;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Process getProcess() { return process; }
    public long getStartTime() { return startTime; }
    public long getEndTime() { return endTime; }

    @Override
    public String toString()
    {
        String processName = process == null ? "IDLE" : process.getName();
        return String.format("[%s %d-%d]", processName, startTime, endTime);
    }
}