# CPU Scheduler Simulator

**CPU Scheduler Simulator** is a Java-based desktop application built with JavaFX to simulate and visualize various CPU scheduling algorithms in real time. The application offers dynamic process management, live visualization, and detailed statistical analysis—ideal for academic and educational purposes.

---

## Supported Scheduling Algorithms

1. First-Come, First-Served (FCFS)
2. Shortest Job First (SJF)
   - Preemptive
   - Non-Preemptive
3. Priority Scheduling
   - Preemptive
   - Non-Preemptive
4. Round Robin (RR)

> The application only requests inputs relevant to the selected algorithm (e.g., priority values are not required for FCFS).

---

## Key Features

- Real-time simulation: 1 second = 1 time unit
- Live Gantt Chart rendering and process timeline visualization
- Dynamically add processes while the scheduler is running
- Real-time table of remaining burst times
- Calculation of:
  - Average Waiting Time
  - Average Turnaround Time
- Option to simulate only predefined processes (static scheduling mode)
- Built as a JavaFX desktop application

---

## Input Parameters by Scheduler

| Scheduler      | Required Inputs                          |
|----------------|-------------------------------------------|
| FCFS           | Arrival Time, Burst Time                  |
| SJF            | Arrival Time, Burst Time                  |
| Priority       | Arrival Time, Burst Time, Priority        |
| Round Robin    | Arrival Time, Burst Time, Time Quantum    |

---

## Technology Stack

- Programming Language: Java
- GUI Framework: JavaFX
- Build Tool: Maven
- Concurrency: Java multithreading for real-time scheduling

---

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- JavaFX SDK (if not bundled with JDK)
- Maven (used for building and running the project)

### Running the Application (with Maven)

#### 1. Clone the repository
```bash
git clone https://github.com/MoustafaHashem/CPU-Scheduler.git
cd CPU-Scheduler
```

#### 2. Run the application using Maven:
```bash
mvn clean javafx:run
```
Note: Make sure your `pom.xml` is correctly configured with the JavaFX Maven plugin and includes the required JavaFX modules such as javafx.controls and javafx.fxml.
