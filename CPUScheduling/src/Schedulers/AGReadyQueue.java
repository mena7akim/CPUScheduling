package Schedulers;

import java.util.TreeSet;

class AGReadyQueue {
    private TreeSet<RoundRobinProcess> processQueueTime;
    private TreeSet<RoundRobinProcess> processAGFactor;

    int mean = 0;

    public AGReadyQueue() {
        processQueueTime = new TreeSet<>(new QueueTimeComparator());
        processAGFactor = new TreeSet<>(new AGFactorComparator());
    }

    public void addProcess(RoundRobinProcess process) {
        processQueueTime.add(process);
        processAGFactor.add(process);
        mean += process.getQuantumTime();
    }

    public RoundRobinProcess removeMinAG() {
        RoundRobinProcess process = processAGFactor.pollFirst();
        processQueueTime.remove(process);
        mean -= process.getQuantumTime();
        return process;
    }

    public RoundRobinProcess getMinAG() {
        return processAGFactor.first();
    }

    public RoundRobinProcess removeFirst() {
        RoundRobinProcess process = processQueueTime.pollFirst();
        processAGFactor.remove(process);
        mean -= process.getQuantumTime();
        return process;
    }

    public int getIncreasedQuantumTime() {
        return (int) Math.ceil(1.0 * mean / processQueueTime.size() / 10);
    }

    public int size() {
        return processQueueTime.size();
    }

    public boolean isEmpty() {
        return processQueueTime.isEmpty() && processAGFactor.isEmpty();
    }
}
