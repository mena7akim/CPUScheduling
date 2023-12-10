import java.util.Random;

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

    public String getNAme()
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
        System.out.println("Helloooooooooooooo world!");
    }
}