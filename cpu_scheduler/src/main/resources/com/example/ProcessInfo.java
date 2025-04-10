package com.example;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProcessInfo {

        private final SimpleStringProperty name;
        private final SimpleIntegerProperty arrivalTime;
        private final SimpleIntegerProperty burstTime;
        private final SimpleIntegerProperty priority;
        private SimpleIntegerProperty remainingTime;

        public ProcessInfo(String name, int arrivalTime, int burstTime, int remainingTime, int priority) {
            this.name = new SimpleStringProperty(name);
            this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
            this.burstTime = new SimpleIntegerProperty(burstTime);
            this.remainingTime = new SimpleIntegerProperty(burstTime); // Initially same as burst time
            this.priority = new SimpleIntegerProperty(priority);
        }

        public ProcessInfo(String name, int arrivalTime , int remainingTime,  int burstTime) {
            this(name,arrivalTime,burstTime,remainingTime,0);
        }


    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public SimpleIntegerProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime.get();
    }

    public SimpleIntegerProperty burstTimeProperty() {
        return burstTime;
    }

    public int getPriority() {
        return priority.get();
    }

    public SimpleIntegerProperty priorityProperty() {
        return priority;
    }

    public int getRemainingTime() {
        return remainingTime.get();
    }

    public SimpleIntegerProperty remainingTimeProperty() {
        return remainingTime;
    }
}



