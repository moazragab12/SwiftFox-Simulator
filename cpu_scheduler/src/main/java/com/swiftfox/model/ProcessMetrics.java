package com.swiftfox.model;


public record ProcessMetrics(
        Process process,
        int turnaroundTime,
        int waitingTime
) {
    @Override
    public String toString() {
        return String.format("%s: Turnaround=%d, Waiting=%d",
                process.getName(), turnaroundTime, waitingTime);
    }

    public ProcessMetrics(Process process, int turnaroundTime, int waitingTime) {
        this.process = process;
        this.turnaroundTime = turnaroundTime;
        this.waitingTime = waitingTime;
    }

    public Process getProcess() {
        return process;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int waitingTime() {
        return waitingTime;
    }

}