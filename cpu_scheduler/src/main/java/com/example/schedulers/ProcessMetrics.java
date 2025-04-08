package com.example.schedulers;

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
}