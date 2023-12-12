import java.util.*;
import java.util.function.Function;

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
    protected int waitingTime;
    protected int turnaroundTime;
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

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
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

    String getQuantumTimeHistory() {
        return null;
    }

    public AGProcess(String name, int burstTime, int arrivalTime, int priority, int quantumTime){
        super(name, burstTime, arrivalTime, priority);
        this.quantumTime = quantumTime;
        this.AGFactor = calcAGFactor();
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
            return 10 - this.arrivalTime + this.burstTime;
        }
        else{
            return priority + this.arrivalTime + this.burstTime;
        }
    }
}


class ProcessesComparator implements Comparator<Process>{
    @Override
    public int compare(Process p1, Process p2) {
        if(p1.getBurstTime() == p2.getBurstTime()){
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
        return p1.getBurstTime() - p2.getBurstTime();
    }
}


class SJFScheduler implements Scheduler {

    double waitingTimeAvg = 0;
    double turnaroundTimeAvg = 0;
    PriorityQueue<Process> processQueue = new PriorityQueue<>(new ProcessesComparator());
    Process[] processes;
    @Override
    public void addProcesses(Process[] processes) {
        Arrays.sort(processes, (p1, p2) -> Integer.compare(p1.getArrivalTime(), p2.getArrivalTime()));
        this.processes = processes;
//        for (int i = 0; i < processes.length; i++) {
//            System.out.println(i + "-" + processes[i].getName() + " in arrival timeee: " + processes[i].getArrivalTime());
//        }
    }

    @Override
    public void runScheduler() {
        int currentTime = 0;
        int lastProcessIndex = 0;
        int processExitTime = -1;
        Process currentProcess = null;
        while (lastProcessIndex < processes.length || currentProcess != null || !processQueue.isEmpty())
        {
            while (lastProcessIndex < processes.length && processes[lastProcessIndex].getArrivalTime() == currentTime)
            {
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }
            if (processExitTime == currentTime) {
                currentProcess = null;
            }
            if (!processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                processExitTime = currentTime + nextProcess.getBurstTime();
                nextProcess.setTurnaroundTime(processExitTime - nextProcess.getArrivalTime());
                nextProcess.setWaitingTime(nextProcess.getTurnaroundTime() - nextProcess.getBurstTime());
                System.out.println(nextProcess.getName() + " -> waiting time = " +
                        nextProcess.getWaitingTime() + ", turnaround time = " +
                        nextProcess.getTurnaroundTime());
                currentProcess = nextProcess;
            } else {
                currentTime = processes[lastProcessIndex].getArrivalTime();
            }
            currentTime++;
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

class AGScheduler implements Scheduler{
    @Override
    public void addProcesses(Process[] processes) {
        AGProcess[] AGProcesses = (AGProcess[]) processes;

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
public class Main {
    public static void main(String[] args) {
        Process p1 = new Process("P1", 7, 0);

        Process p2 = new Process("P2", 4, 2);

        Process p3 = new Process("P3", 1, 4);

        Process p4 = new Process("P4", 4, 5);



        SJFScheduler sjfScheduler = new SJFScheduler();
        sjfScheduler.addProcesses(new Process[]{p1, p2, p3, p4});
        sjfScheduler.runScheduler();

//        System.out.println("Processes Execution Order: " + sjfScheduler.getProcessExecutionOrder());     //p1 p3 p2 p4
//        System.out.println("Average Waiting Time: " + sjfScheduler.getAverageWaitingTime());
//        System.out.println("Average Turnaround Time: " + sjfScheduler.getAverageTurnaroundTime());
//
//
//        for (Process process : sjfScheduler.processQueue) {
//            System.out.println("Process " + process.getName() +
//                    " - Waiting Time: " + process.getWaitingTime() +
//                    ", Turnaround Time: " + process.getTurnaroundTime());
//        }


        System.out.println("Helloooooooooooooo world!");
    }
}