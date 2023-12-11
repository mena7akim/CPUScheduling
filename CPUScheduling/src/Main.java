import com.sun.source.tree.Tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

interface Scheduler{
    void addProcesses(Process[] processes);
    void runScheduler();
    double getAverageWaitingTime();
    double getAverageTurnaroundTime();
    String getProcessExecutionOrder();

}
class Process{
    protected String name;
    protected int burstTime;
    protected int arrivalTime;
    protected int priority = 0;

    public Process(String name, int burstTime, int arrivalTime){
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
    }

    public Process(String name, int burstTime, int arrivalTime, int priority){
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
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
}

class AGProcess extends Process{
    private int AGFactor;

    private int quantumTime;

    private int queueTime = 0;

    String getQuantumTimeHistory() {
        return null;
    }

    public AGProcess(String name, int burstTime, int arrivalTime, int priority){
        super(name, burstTime, arrivalTime, priority);
        this.AGFactor = calcAGFactor();
    }
    public AGProcess(String name, int burstTime, int arrivalTime, int priority, int quantumTime){
        super(name, burstTime, arrivalTime, priority);
        this.quantumTime = quantumTime;
        this.AGFactor = calcAGFactor();
    }

    public int getQueueTime(){
        return queueTime;
    }

    public void setQueueTime(int queueTime){
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

    public int RF(){
        Random rand = new Random();
        return rand.nextInt(21);
    }
    public int calcAGFactor(){
        int RFValue = RF();
        if(RFValue < 10){
            return RFValue + this.arrivalTime + this.burstTime;
        }
        else if(RFValue > 10){
            return 10 + this.arrivalTime + this.burstTime;
        }
        else{
            return priority + this.arrivalTime + this.burstTime;
        }
    }
}

class SJFScheduler implements Scheduler{

    @Override
    public void addProcesses(Process[] processes) {

    }

    @Override
    public void runScheduler() {

    }

    @Override
    public double getAverageWaitingTime() {
        return 0;
    }

    @Override
    public double getAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public String getProcessExecutionOrder() {
        return null;
    }
}

class SRTFScheduler implements Scheduler{
    @Override
    public void addProcesses(Process[] processes) {

    }

    @Override
    public void runScheduler() {

    }

    @Override
    public double getAverageWaitingTime() {
        return 0;
    }

    @Override
    public double getAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public String getProcessExecutionOrder() {
        return null;
    }
}

class PriorityScheduler implements Scheduler{
    @Override
    public void addProcesses(Process[] processes) {

    }

    @Override
    public void runScheduler() {

    }

    @Override
    public double getAverageWaitingTime() {
        return 0;
    }

    @Override
    public double getAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public String getProcessExecutionOrder() {
        return null;
    }

}

class AGReadyQueue{
    private TreeSet<AGProcess> processQueueTime;
    private TreeSet<AGProcess> processAGFactor;

    int mean = 0;

    public AGReadyQueue(){
        processQueueTime = new TreeSet<>(new QueueTimeComparator());
        processAGFactor = new TreeSet<>(new AGFactorComparator());
    }

    public void addProcess(AGProcess process){
        processQueueTime.add(process);
        processAGFactor.add(process);
        mean += process.getQuantumTime();
    }

    public AGProcess removeMinAG(){
        AGProcess process = processAGFactor.pollFirst();
        processQueueTime.remove(process);
        mean -= process.getQuantumTime();
        return process;
    }

    public AGProcess getMinAG(){
        return processAGFactor.first();
    }

    public AGProcess removeFirst() {
        AGProcess process = processQueueTime.pollFirst();
        processAGFactor.remove(process);
        mean -= process.getQuantumTime();
        return process;
    }

    public int getIncreasedQuantumTime(){
        return (int) Math.ceil(1.0 * mean / processQueueTime.size() / 10);
    }

    public int size(){
        return processQueueTime.size();
    }

    public boolean isEmpty(){
        return processQueueTime.isEmpty() && processAGFactor.isEmpty();
    }
}

class AGFactorComparator implements Comparator<AGProcess> {
    @Override
    public int compare(AGProcess o1, AGProcess o2) {
        if(o1.getAGFactor() == o2.getAGFactor()){
            return o1.getQueueTime() - o2.getQueueTime();
        }
        return o1.getAGFactor() - o2.getAGFactor();
    }
}

class QueueTimeComparator implements Comparator<AGProcess> {
    @Override
    public int compare(AGProcess o1, AGProcess o2) {
        if(o1.getQueueTime() == o2.getQueueTime()){
            return o1.getAGFactor() - o2.getAGFactor();
        }
        return o1.getQueueTime() - o2.getQueueTime();
    }
}

class AGScheduler implements Scheduler{
    AGReadyQueue readyQueue;
    AGProcess[] AGProcesses;

    int quantumTime;

    public AGScheduler(int quantumTime){
        this.quantumTime = quantumTime;
        readyQueue = new AGReadyQueue();
    }
    public AGScheduler(int quantumTime, Process[] processes){
        this.quantumTime = quantumTime;
        readyQueue = new AGReadyQueue();
        addProcesses(processes);
    }

    @Override
    public void addProcesses(Process[] processes) {
        this.AGProcesses = (AGProcess[]) processes;
        Arrays.sort(AGProcesses, new Comparator<AGProcess>() {
            @Override
            public int compare(AGProcess o1, AGProcess o2) {
                return o1.getArrivalTime() - o2.getArrivalTime();
            }
        });
        for(AGProcess process : AGProcesses){
            process.setQuantumTime(quantumTime);
        }
    }

    @Override
    public void runScheduler() {
        int time = 0;
        int lastProcessIndex = 0;
        int nProcesses = AGProcesses.length;
        int processEnterTime = -1;
        int processExitTime = -1;
        AGProcess currentProcess = null;
        while(lastProcessIndex != nProcesses || !readyQueue.isEmpty() || time <= processExitTime) {
            while(lastProcessIndex < nProcesses && AGProcesses[lastProcessIndex].getArrivalTime() == time){
                AGProcesses[lastProcessIndex].setQueueTime(time);
                readyQueue.addProcess(AGProcesses[lastProcessIndex]);
                lastProcessIndex++;
            }
            if(processExitTime == time && currentProcess != null){
                currentProcess.setBurstTime(currentProcess.getBurstTime() - (processExitTime - processEnterTime));
                if(currentProcess.getBurstTime() > 0){
                    currentProcess.setQuantumTime(currentProcess.getQuantumTime() + readyQueue.getIncreasedQuantumTime());
                    currentProcess.setQueueTime(time);
                    readyQueue.addProcess(currentProcess);
                }
                currentProcess = null;
            }
            if(currentProcess == null){
                if(!readyQueue.isEmpty()){
                    currentProcess = readyQueue.removeFirst();
                    processEnterTime = time;
                    processExitTime = time + Math.min(currentProcess.getQuantumTime(), currentProcess.getBurstTime());
                }
            }
            else{
                if((currentProcess.getQuantumTime() + 1) / 2 <= time - processEnterTime){
                    if(!readyQueue.isEmpty()){
                        AGProcess minAGProcess = readyQueue.getMinAG();
                        AGFactorComparator agFactorComparator = new AGFactorComparator();
                        if(agFactorComparator.compare(currentProcess, minAGProcess) > 0){
                            currentProcess.setBurstTime(currentProcess.getBurstTime() - (time - processEnterTime));
                            currentProcess.setQuantumTime(2 * currentProcess.getQuantumTime() - time + processEnterTime);
                            currentProcess.setQueueTime(time);
                            readyQueue.removeMinAG();
                            readyQueue.addProcess(currentProcess);
                            currentProcess = minAGProcess;
                            processEnterTime = time;
                            processExitTime = time + Math.min(currentProcess.getQuantumTime(), currentProcess.getBurstTime());
                        }
                    }
                }
            }
            System.out.println(time + " " + (currentProcess == null ? "null" : currentProcess.getName()));
            time++;
        }
    }

    @Override
    public double getAverageWaitingTime() {
        return 0;
    }

    @Override
    public double getAverageTurnaroundTime() {
        return 0;
    }

    @Override
    public String getProcessExecutionOrder() {
        return null;
    }

}
public class Main {
    public static void main(String[] args) {
        AGProcess[] processes = new AGProcess[4];
        processes[0] = new AGProcess("P1", 17, 0, 4);
        processes[1] = new AGProcess("P2", 6, 3, 9);
        processes[2] = new AGProcess("P3", 10, 4, 3);
        processes[3] = new AGProcess("P4", 4, 29, 8);
        for(AGProcess process : processes){
            System.out.println(process.getName() + " " + process.getAGFactor());
        }
        AGScheduler scheduler = new AGScheduler(4, processes);
        scheduler.runScheduler();
    }
}