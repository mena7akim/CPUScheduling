package Schedulers;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class PriorityScheduler implements Scheduler {
    TreeSet<Process> processQueue = new TreeSet<>(new ProcessesComparatorByPriority());
    Process[] processes;
    int starvationSolver = 1000;

    public PriorityScheduler() {
    }

    public PriorityScheduler(Process[] processes) {
        this.processes = processes;
        addProcesses(processes);
    }

    public PriorityScheduler(Process[] processes, int starvationSolver) {
        this.processes = processes;
        this.starvationSolver = starvationSolver;
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
        int starvationCounter = starvationSolver;
        Process currentProcess = null;

        while (lastProcessIndex < processes.length || currentProcess != null || !processQueue.isEmpty()) {
            while (lastProcessIndex < processes.length && processes[lastProcessIndex].getArrivalTime() == currentTime) {
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }
            if (currentProcess != null && processExitTime == currentTime) {
                currentProcess.addRange(processExitTime - currentProcess.getBurstTime(), currentTime);
                currentProcess = null;
            }
            if (currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = processQueue.pollFirst();
                currentProcess = nextProcess;
                processExitTime = currentTime + currentProcess.getBurstTime();
            } else if (!processQueue.isEmpty()) {
                Process nextProcess = processQueue.pollFirst();
                if (nextProcess.getPriority() < currentProcess.getPriority()) {
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
            if(starvationCounter == 0){
                if(!processQueue.isEmpty()){
                    Process lastProcess = processQueue.pollLast();
                    lastProcess.setPriority(lastProcess.getPriority() - 1);
                    processQueue.add(lastProcess);
                }
                starvationCounter = starvationSolver;
            }
            starvationCounter--;
            currentTime++;
        }
    }

    public Process[] getProcesses() {
        return processes;
    }


}
