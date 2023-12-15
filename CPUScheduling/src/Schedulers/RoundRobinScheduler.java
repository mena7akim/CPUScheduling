package Schedulers;

import java.util.Arrays;
import java.util.Comparator;

public class RoundRobinScheduler implements Scheduler {
    AGReadyQueue readyQueue;
    RoundRobinProcess[] roundRobinProcesses;

    int quantumTime;

    public RoundRobinScheduler(int quantumTime) {
        this.quantumTime = quantumTime;
        readyQueue = new AGReadyQueue();
    }

    public RoundRobinScheduler(int quantumTime, Process[] processes) {
        this.quantumTime = quantumTime;
        readyQueue = new AGReadyQueue();
        addProcesses(processes);
    }

    @Override
    public void addProcesses(Process[] processes) {
        roundRobinProcesses = new RoundRobinProcess[processes.length];
        for (int i = 0; i < processes.length; i++) {
            roundRobinProcesses[i] = new RoundRobinProcess(processes[i]);
        }
        Arrays.sort(roundRobinProcesses, new Comparator<RoundRobinProcess>() {
            @Override
            public int compare(RoundRobinProcess o1, RoundRobinProcess o2) {
                if(o1.getArrivalTime() == o2.getArrivalTime())
                    return o1.getAGFactor() - o2.getAGFactor();
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
        for (RoundRobinProcess process : roundRobinProcesses) {
            process.setQuantumTime(quantumTime);
        }
    }

    @Override
    public void runScheduler() {
        int currentTime = 0;
        int lastProcessIndex = 0;
        int nProcesses = roundRobinProcesses.length;
        int processEnterTime = -1;
        int processExitTime = -1;
        int queueTime = 0;
        RoundRobinProcess currentProcess = null;
        while (lastProcessIndex != nProcesses || !readyQueue.isEmpty() || currentTime <= processExitTime) {
            while (lastProcessIndex < nProcesses && roundRobinProcesses[lastProcessIndex].getArrivalTime() == currentTime) {
                roundRobinProcesses[lastProcessIndex].setQueueTime(queueTime++);
                readyQueue.addProcess(roundRobinProcesses[lastProcessIndex]);
                lastProcessIndex++;
            }
            if (processExitTime == currentTime && currentProcess != null) {
                currentProcess.addQuantumTime();
                currentProcess.addRange(processEnterTime, currentTime);
                currentProcess.setBurstTime(currentProcess.getBurstTime() - (processExitTime - processEnterTime));
                if (currentProcess.getBurstTime() > 0) {
                    currentProcess.setQuantumTime(currentProcess.getQuantumTime() + readyQueue.getIncreasedQuantumTime());
                    currentProcess.setQueueTime(queueTime++);
                    readyQueue.addProcess(currentProcess);
                }
                currentProcess = null;
            }
            if (currentProcess == null) {
                if (!readyQueue.isEmpty()) {
                    currentProcess = readyQueue.removeFirst();
                    processEnterTime = currentTime;
                    processExitTime = currentTime + Math.min(currentProcess.getQuantumTime(), currentProcess.getBurstTime());
                }
            } else {
                if ((currentProcess.getQuantumTime() + 1) / 2 <= currentTime - processEnterTime) {
                    if (!readyQueue.isEmpty()) {
                        RoundRobinProcess minRoundRobinProcess = readyQueue.getMinAG();
                        AGFactorComparator agFactorComparator = new AGFactorComparator();
                        if (agFactorComparator.compare(currentProcess, minRoundRobinProcess) > 0) {
                            currentProcess.addQuantumTime();
                            currentProcess.addRange(processEnterTime, currentTime);
                            currentProcess.setBurstTime(currentProcess.getBurstTime() - (currentTime - processEnterTime));
                            currentProcess.setQuantumTime(2 * currentProcess.getQuantumTime() - currentTime + processEnterTime);
                            currentProcess.setQueueTime(queueTime++);
                            readyQueue.removeMinAG();
                            readyQueue.addProcess(currentProcess);
                            currentProcess = minRoundRobinProcess;
                            processEnterTime = currentTime;
                            processExitTime = currentTime + Math.min(currentProcess.getQuantumTime(), currentProcess.getBurstTime());
                        }
                    }
                }
            }
            currentTime++;
        }
    }

    public RoundRobinProcess[] getProcesses() {
        return roundRobinProcesses;
    }


}
