public class Main {
    class Process{
        private int burstTime;
        private int arrivalTime;
        private int priority = 0;

        public Process(int burstTime, int arrivalTime){
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
        }

        public Process(int burstTime, int arrivalTime, int priority){
            this.burstTime = burstTime;
            this.arrivalTime = arrivalTime;
            this.priority = priority;
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

    class SJFScheduler{

    }

    class SRTFScheduler{

    }

    class PriorityScheduler{

    }

    class AGScheduler{

    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}