package com.example.schedulers;

import java.util.ArrayList;
import java.util.List;

public final class GanttChart {
    private final List<GanttEntry> entries = new ArrayList<>();

    public void addEntry(Process process, long startTime, long endTime) {
        entries.add(new GanttEntry(process, startTime, endTime));
    }

    public void addIdleEntry(long startTime, long endTime) {
        entries.add(new GanttEntry(null, startTime, endTime));
    }

    public List<GanttEntry> getEntries() {
        return new ArrayList<>(entries);
    }
}