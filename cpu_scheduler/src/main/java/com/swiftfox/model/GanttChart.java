package com.swiftfox.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents the Gantt chart used to visualize the execution timeline of processes
 * in the SwiftFox CPU Scheduler Simulator.
 * <p>
 * This class maintains an observable list of {@link GanttEntry} objects that track
 * when each process starts and ends during the scheduling simulation. It supports
 * both regular process entries and idle time entries.
 * </p>
 *
 * <p>
 * The observable list allows JavaFX UI components to reactively update when entries
 * are added, making it suitable for real-time visualization.
 * </p>
 *
 * @see GanttEntry
 * @see com.swiftfox.model.Process
 */
public final class GanttChart {
    private final ObservableList<GanttEntry> entries = FXCollections.observableArrayList();//converted it to obeservable list
    /**
     * Adds a new process entry to the Gantt chart.
     * <p>
     * This represents a segment of time during which the given process was executing,
     * defined by a start and end time.
     * </p>
     *
     * @param process   the process that was executed during the given time span
     * @param startTime the time at which the process started execution
     * @param endTime   the time at which the process finished execution
     */
    public void addEntry(Process process, long startTime, long endTime) {
        entries.add(new GanttEntry(process, startTime, endTime));
    }
    /**
     * Adds an idle time entry to the Gantt chart.
     * <p>
     * This represents a time period during which no process was executing (i.e., the CPU was idle).
     * A {@code null} process is used to indicate idle time.
     * </p>
     *
     * @param startTime the time at which the idle period started
     * @param endTime   the time at which the idle period ended
     */

    public void addIdleEntry(long startTime, long endTime) {
        entries.add(new GanttEntry(null, startTime, endTime));
    }
    /**
     * Returns the list of Gantt chart entries.
     * <p>
     * Each entry represents a time interval where a specific process (or idle period) was active
     * on the CPU. The list is observable, allowing UI components to react to changes automatically.
     * </p>
     *
     * @return an observable list of {@link GanttEntry} objects representing the Gantt chart data
     */

    public ObservableList<GanttEntry> getEntries() {
        return  entries;
    }
}