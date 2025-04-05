package com.example.schedulers;

import java.util.ArrayList;

public class Pri extends scheduler {
    public Pri(ArrayList<process> processList, boolean isPreemptive) {
        super(processList, isPreemptive);
    }

    @Override
    public ArrayList<GantChartUnit> schedule() {
        
    }

}
