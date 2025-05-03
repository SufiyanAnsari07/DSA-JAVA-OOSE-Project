package cpuscheduler;

public class Process {
    int pid , arrival , burst , priority , remaining ;
    Process ( int pid , int arrival , int burst , int priority ) {
        this.pid = pid ;
        this.arrival = arrival ;
        this.burst = burst ;
        this.priority = priority ;
        this.remaining = burst ;
    }
}