package com.example.schedulers;

import java.util.ArrayList;

public class SJF extends scheduler {

    public SJF(ArrayList processList, boolean isPreemptive) {
        super(processList, isPreemptive);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void schedule() {
        // Implement the Shortest Job First scheduling algorithm here
        // This is a placeholder implementation
        System.out.println("Scheduling using Shortest Job First (SJF) algorithm.");
    }

    @Override
    public void sortProcessList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortProcessList'");
    }

    @Override
    public void calculateWaitingTime(process currentProcessInExecution) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateWaitingTime'");
    }

    @Override
    public void calculateTurnAroundTime(process currentProcessInExecution) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateTurnAroundTime'");
    }

    @Override
    public void calculateAverageWaitingTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateAverageWaitingTime'");
    }

    @Override
    public void calculateAverageTurnAroundTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateAverageTurnAroundTime'");
    }

    @Override
    public void isFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinished'");
    }

}
