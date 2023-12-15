package Schedulers;

import java.util.Comparator;

class ProcessesComparatorByPriority implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        if (p1.getPriority() == p2.getPriority()) {
            if(p1.getArrivalTime() == p2.getArrivalTime())
                return p1.getName().compareTo(p2.getName());
            return p1.getArrivalTime() - p2.getArrivalTime();
        }
        return p1.getPriority() - p2.getPriority();
    }
}
