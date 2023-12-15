package Schedulers;

import java.awt.*;
import java.util.ArrayList;

public class Process {
    protected String name;
    protected Color color;
    protected int burstTime;
    protected int arrivalTime;

    protected int priority = 0;

    ArrayList<Range> ranges = new ArrayList<>();

    public Process(String name, int burstTime, int arrivalTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public Process(String name, int burstTime, int arrivalTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public Process(String name, Color color, int burstTime, int arrivalTime, int priority) {
        this.name = name;
        this.color = color;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void addRange(int start, int end) {
        ranges.add(new Range(start, end));
    }

    public ArrayList<Range> getRanges() {
        return ranges;
    }

    public int getWaitingTime() {
        int waitingTime = 0;
        int pre = arrivalTime;
        for(Range range : ranges){
            waitingTime += range.getStart() - pre;
            pre = range.getEnd();
        }
        return waitingTime;
    }

    public int getTurnaroundTime() {
        return ranges.getLast().getEnd() - arrivalTime;
    }

    static public double getAverageWaitingTime(Process[] Processes) {
        double sum = 0;
        for (Process process : Processes) {
            sum += process.getWaitingTime();
        }
        return sum / Processes.length;
    }

    static public double getAverageTurnaroundTime(Process[] Processes) {
        double sum = 0;
        for (Process process : Processes) {
            sum += process.getTurnaroundTime();
        }
        return sum / Processes.length;
    }
}
