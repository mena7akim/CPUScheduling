package Schedulers;

import java.util.Arrays;
import java.util.PriorityQueue;

public class SJFScheduler implements Scheduler {

    PriorityQueue<Process> processQueue = new PriorityQueue<>(new ProcessesComparatorByBurst());
    Process[] processes;
    int contextSwitchTime = 0;

    public SJFScheduler() {
    }

    public SJFScheduler(Process[] processes) {
        addProcesses(processes);
    }
    public SJFScheduler(Process[] processes, int contextSwitchTime) {
        this.contextSwitchTime = contextSwitchTime;
        addProcesses(processes);
    }

    @Override
    public void addProcesses(Process[] processes) {
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        this.processes = processes;
    }

    @Override
    public void runScheduler() {
        int currentTime = 0;
        int lastProcessIndex = 0;
        int processExitTime = -1;
        Process currentProcess = null;
        while (lastProcessIndex < processes.length || currentProcess != null || !processQueue.isEmpty()) {
            while (lastProcessIndex < processes.length && processes[lastProcessIndex].getArrivalTime() <= currentTime) {
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }
            if (currentProcess != null && processExitTime == currentTime) {
                currentProcess.addRange(processExitTime - currentProcess.getBurstTime(), processExitTime);
                currentProcess = null;
                if(!processQueue.isEmpty()){
                    currentTime += contextSwitchTime;
                }
            }
            if (currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                processExitTime = currentTime + nextProcess.getBurstTime();
                currentProcess = nextProcess;
            }
            currentTime++;
        }
    }

    public Process[] getProcesses() {
        return processes;
    }

}
