package Schedulers;

import java.util.ArrayList;
import java.util.Random;

public class RoundRobinProcess extends Process {
    private int AGFactor = 0;

    private int quantumTime = 0;

    private int queueTime = 0;
    private ArrayList<Integer> quantumTimeHistory = new ArrayList<>();

    public RoundRobinProcess(Process process) {
        super(process.getName(), process.getColor(), process.getBurstTime(), process.getArrivalTime(), process.getPriority());
        this.AGFactor = calcAGFactor();
    }

    public RoundRobinProcess(String name, int burstTime, int arrivalTime, int priority) {
        super(name, burstTime, arrivalTime, priority);
        this.AGFactor = calcAGFactor();
    }

    public RoundRobinProcess(String name, int burstTime, int arrivalTime, int priority, int quantumTime) {
        super(name, burstTime, arrivalTime, priority);
        this.quantumTime = quantumTime;
        this.AGFactor = calcAGFactor();
    }

    public int getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(int queueTime) {
        this.queueTime = queueTime;
    }

    public int getAGFactor() {
        return AGFactor;
    }

    public void setAGFactor(int AGFactor) {
        this.AGFactor = AGFactor;
    }

    public int getQuantumTime() {
        return quantumTime;
    }

    public void setQuantumTime(int quantumTime) {
        this.quantumTime = quantumTime;
    }

    public int RF() {
        Random rand = new Random();
        return rand.nextInt(21);
    }

    public int calcAGFactor() {
        int RFValue = RF();
        if (RFValue < 10) {
            return RFValue + this.arrivalTime + this.burstTime;
        } else if (RFValue > 10) {
            return 10 + this.arrivalTime + this.burstTime;
        } else {
            return priority + this.arrivalTime + this.burstTime;
        }
    }

    public void addQuantumTime() {
        this.quantumTimeHistory.add(quantumTime);
    }

    public ArrayList<Integer> getQuantumTimeHistory() {
        return quantumTimeHistory;
    }
}
