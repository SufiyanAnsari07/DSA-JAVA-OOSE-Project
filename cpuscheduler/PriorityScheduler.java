package cpuscheduler;

import java.io.*;
import java.util.*;

public class PriorityScheduler implements Scheduler {
    public void schedule ( ArrayList<Process> processList , BufferedWriter bw , PriorityQueue<AlgoResult> pq ) throws IOException {
        int currentTime = 0 ;
        int totalWaitingTime = 0 ;
        int totalTurnaroundTime = 0 ;

        Collections.sort ( processList , new Comparator<Process> ( ) {
            public int compare ( Process p1 , Process p2 ) {
                if ( p1.priority == p2.priority ) {
                    return Integer.compare ( p1.arrival , p2.arrival ) ;
                }
                return Integer.compare ( p1.priority , p2.priority ) ;
            }
        } ) ;

        for ( Process p : processList ) {
            int waitingTime = 0 ;
            int turnaroundTime = 0 ;

            if ( currentTime < p.arrival ) {
                currentTime = p.arrival ;
            }

            waitingTime = currentTime - p.arrival ;
            turnaroundTime = waitingTime + p.burst ;

            totalWaitingTime += waitingTime ;
            totalTurnaroundTime += turnaroundTime ;

            currentTime += p.burst ;

            bw.write ( "PID : " + p.pid +
                    ", Arrival Time : " + p.arrival +
                    ", Burst Time : " + p.burst +
                    ", Priority : " + p.priority +
                    ", Completion Time : " + currentTime +
                    ", Waiting Time : " + waitingTime +
                    ", Turnaround Time : " + turnaroundTime + "\n" ) ;
        }

        bw.write ( "Average Waiting Time : " + ( ( double ) totalWaitingTime / processList.size ( ) ) + "\n" ) ;
        bw.write ( "Average Turnaround Time : " + ( ( double ) totalTurnaroundTime / processList.size ( ) ) + "\n" ) ;
        pq.add ( new AlgoResult ( "Priority Scheduling : " , totalWaitingTime , totalTurnaroundTime ) ) ;
    }
}
