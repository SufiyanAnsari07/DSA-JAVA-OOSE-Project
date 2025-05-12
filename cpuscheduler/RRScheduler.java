package cpuscheduler;

import java.io.*;
import java.util.*;

public class RRScheduler implements Scheduler {
    public void schedule ( ArrayList<Process> processList , BufferedWriter bw , PriorityQueue<AlgoResult> pq ) throws IOException {
        int timeQuantum = 4 ;
        int currentTime = 0 ;
        int totalWaitingTime = 0 ;
        int totalTurnaroundTime = 0 ;

        Collections.sort ( processList , new Comparator<Process> () {
            public int compare ( Process p1 , Process p2 ) {
                return Integer.compare ( p1.arrival , p2.arrival ) ;
            }
        } ) ;
        
        Queue<Process> queue = new LinkedList<> () ;

        for ( Process p : processList ) {
            queue.add ( p ) ;
        }

        while ( !queue.isEmpty() ) {
            Process p = queue.poll() ;
            if ( p.remaining <= timeQuantum ) {
                currentTime += p.remaining ;
                p.remaining = 0 ;
                totalTurnaroundTime += currentTime - p.arrival ;
                totalWaitingTime += currentTime - p.arrival - p.burst ;

                bw.write ( "PID : " + p.pid +
                        ", Arrival Time : " + p.arrival +
                        ", Burst Time: " + p.burst +
                        ", Completion Time : " + currentTime +
                        ", Waiting Time : " + (currentTime - p.arrival - p.burst) +
                        ", Turnaround Time : " + (currentTime - p.arrival) + "\n" ) ;
            } else {
                p.remaining -= timeQuantum ;
                currentTime += timeQuantum ;
                queue.add(p) ;
            }
        }

        bw.write ( "Average Waiting Time: " + ( ( double ) totalWaitingTime / processList.size ( ) ) + "\n" ) ;
        bw.write ( "Average Turnaround Time: " + ( ( double ) totalTurnaroundTime / processList.size ( ) ) + "\n" ) ;
        pq.add ( new AlgoResult( "Round Robin Scheduling :", totalWaitingTime , totalTurnaroundTime ) ) ;
    }
}
