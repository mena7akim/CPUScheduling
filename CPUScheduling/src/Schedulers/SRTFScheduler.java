package Schedulers;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class SRTFScheduler implements Scheduler {
    TreeSet<Process> processQueue = new TreeSet<>(new ProcessesComparatorByBurst());
    Process[] processes;
    int starvationSolver = 1000;

    public SRTFScheduler() {
    }

    public SRTFScheduler(Process[] processes) {
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
            while (lastProcessIndex < processes.length && processes[lastProcessIndex].getArrivalTime() == currentTime) {
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }

            if (processExitTime == currentTime) {
                currentProcess.addRange(processExitTime - currentProcess.getBurstTime(), processExitTime);
                currentProcess = null;
            }

            if (currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = getBestProcess(processQueue.first(), processQueue.last(), currentTime);
                processQueue.remove(nextProcess);
                currentProcess = nextProcess;
                processExitTime = currentTime + currentProcess.getBurstTime();
            } else if (!processQueue.isEmpty()) {
                Process nextProcess = getBestProcess(processQueue.first(), processQueue.last(), currentTime);
                processQueue.remove(nextProcess);
                if (nextProcess.getBurstTime() < currentProcess.getBurstTime() || currentTime - nextProcess.getArrivalTime() >= starvationSolver) {
                    currentProcess.addRange(processExitTime - currentProcess.getBurstTime(), currentTime);
                    int remainingTime = processExitTime - currentTime;
                    currentProcess.setBurstTime(remainingTime);
                    processQueue.add(currentProcess);
                    currentProcess = nextProcess;
                    processExitTime = currentTime + currentProcess.getBurstTime();
                } else {
                    processQueue.add(nextProcess);
                }
            }
            currentTime++;
        }
    }

    public Process getBestProcess(Process firstProcess, Process lastProcess, int currentTime) {
        if(currentTime - lastProcess.getArrivalTime() >= starvationSolver) {
            return lastProcess;
        } else {
            return firstProcess;
        }
    }

    public Process[] getProcesses() {
        return processes;
    }

}
