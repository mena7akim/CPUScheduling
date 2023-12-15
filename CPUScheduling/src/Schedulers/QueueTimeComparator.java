package Schedulers;

import java.util.Comparator;

class QueueTimeComparator implements Comparator<RoundRobinProcess> {
    @Override
    public int compare(RoundRobinProcess o1, RoundRobinProcess o2) {
        if (o1.getQueueTime() == o2.getQueueTime()) {
            return o1.getAGFactor() - o2.getAGFactor();
        }
        return o1.getQueueTime() - o2.getQueueTime();
    }
}
