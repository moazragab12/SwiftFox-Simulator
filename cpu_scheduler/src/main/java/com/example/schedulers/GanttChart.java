package com.example.schedulers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public final class GanttChart {
    private final ObservableList<GanttEntry> entries = FXCollections.observableArrayList();//converted it to obeservable list

    public void addEntry(Process process, long startTime, long endTime) {
        entries.add(new GanttEntry(process, startTime, endTime));
    }

    public void addIdleEntry(long startTime, long endTime) {
        entries.add(new GanttEntry(null, startTime, endTime));
    }

    public ObservableList<GanttEntry> getEntries() {
        return  entries;
    }
}