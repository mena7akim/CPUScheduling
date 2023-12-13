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


class ProcessesComparatorByBurst implements Comparator<Process>{
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
    PriorityQueue<Process> processQueue = new PriorityQueue<>(new ProcessesComparatorByBurst());
    Process[] processes;
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
                currentProcess = null;
            }
            if(currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                processExitTime = currentTime + nextProcess.getBurstTime();
                currentProcess = nextProcess;

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


class SRTFScheduler implements Scheduler {
    double waitingTimeAvg = 0;
    double turnaroundTimeAvg = 0;
    PriorityQueue<Process> processQueue = new PriorityQueue<>(new ProcessesComparatorByBurst());
    Process[] processes;

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
                if (currentProcess != null && processes[lastProcessIndex].getBurstTime() < currentProcess.getBurstTime()) {
                    processQueue.add(currentProcess);
                    currentProcess = null;
                }
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }

            if (processExitTime == currentTime) {
                currentProcess = null;
            }

            if (currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                currentProcess = nextProcess;
                processExitTime = currentTime + currentProcess.getBurstTime();
                System.out.print(currentProcess.getName() + " ");
            } else if (!processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                if (nextProcess.getBurstTime() < currentProcess.getBurstTime()) {
                    int remainingTime = currentProcess.getBurstTime() - (currentTime - currentProcess.getArrivalTime());
                    currentProcess.setBurstTime(remainingTime);
                    processQueue.add(currentProcess);
                    currentProcess = nextProcess;
                    processExitTime = currentTime + currentProcess.getBurstTime();
                    System.out.print(currentProcess.getName() +  " ");
                } else {
                    processQueue.add(nextProcess);
                }
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

class ProcessesComparatorByPriority implements Comparator<Process>{
    @Override
    public int compare(Process p1, Process p2) {
        if(p1.getPriority() == p2.getPriority()){
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
        return p1.getPriority() - p2.getPriority();
    }
}
class PriorityScheduler implements Scheduler{
    double waitingTimeAvg = 0;
    double turnaroundTimeAvg = 0;
    PriorityQueue<Process> processQueue = new PriorityQueue<>(new ProcessesComparatorByPriority());
    Process[] processes;

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
                if (currentProcess != null && processes[lastProcessIndex].getBurstTime() < currentProcess.getBurstTime()) {
                    processQueue.add(currentProcess);
                    currentProcess = null;
                }
                processQueue.add(processes[lastProcessIndex]);
                lastProcessIndex++;
            }

            if (processExitTime == currentTime) {
                currentProcess = null;
            }

            if (currentProcess == null && !processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                currentProcess = nextProcess;
                processExitTime = currentTime + currentProcess.getBurstTime();
                System.out.print(currentProcess.getName() + " ");
            }
            else if (!processQueue.isEmpty()) {
                Process nextProcess = processQueue.poll();
                if (nextProcess.getPriority() < currentProcess.getPriority()) {
                    int remainingTime = currentProcess.getBurstTime() - (currentTime - currentProcess.getArrivalTime());
                    currentProcess.setBurstTime(remainingTime);
                    processQueue.add(currentProcess);
                    currentProcess = nextProcess;
                    processExitTime = currentTime + currentProcess.getBurstTime();
                    System.out.print(currentProcess.getName() + " ");

                } else {
                    processQueue.add(nextProcess);
                }
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

        Process pro1 = new Process("P1", 7, 0);

        Process pro2 = new Process("P2", 4, 2);

        Process pro3 = new Process("P3", 1, 4);

        Process pro4 = new Process("P4", 4, 5);


        System.out.println("SJF: ");
        SJFScheduler sjfScheduler = new SJFScheduler();
        sjfScheduler.addProcesses(new Process[]{pro1, pro2, pro3, pro4});
        sjfScheduler.runScheduler();

        System.out.println("SRTF: ");
        SRTFScheduler srtfScheduler = new SRTFScheduler();
        srtfScheduler.addProcesses(new Process[]{pro1, pro2, pro3, pro4});
        srtfScheduler.runScheduler();

        System.out.println("\n" + "===========================================");
        Process p1 = new Process("P1", 10, 0, 3);

        Process p2 = new Process("P2", 1, 0, 1);

        Process p3 = new Process("P3", 2, 0, 4);

        Process p4 = new Process("P4", 1, 0, 5);

        Process p5 = new Process("P5", 5, 0, 2);

        System.out.println("Priority: ");
        PriorityScheduler pScheduler = new PriorityScheduler();
        pScheduler.addProcesses(new Process[]{p1, p2, p3, p4, p5});
        pScheduler.runScheduler();



//        System.out.println("Helloooooooooooooo world!");
    }
}