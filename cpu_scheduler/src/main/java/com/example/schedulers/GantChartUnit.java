package com.example.schedulers;

public class GantChartUnit {
    private process process;
    private int time;
    public GantChartUnit(process process, int time) {
        this.process = process;
        this.time = time;
    }
    public process getProcess() {
        return process;
    }
    public void setProcess(process process) {
        this.process = process;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }

}
