package cpuscheduler ;

import java.io.* ;
import java.util.* ;

public class SJFPreemptiveScheduler implements Scheduler {
    public void schedule ( ArrayList<Process> processList , BufferedWriter bw , PriorityQueue<AlgoResult> pq ) throws IOException {
        int n = processList.size() ;
        int[] remainingTime = new int[n] ;
        int[] waitingTime = new int[n] ;
        int[] turnaroundTime = new int[n] ;
        int[] completionTime = new int[n] ;
        boolean[] isCompleted = new boolean[n] ;

        for ( int i = 0 ; i < n ; i++ ) {
            remainingTime[i] = processList.get(i).burst ;
        }

        int currentTime = 0 ;
        int completed = 0 ;
        int totalWaitingTime = 0 ;
        int totalTurnaroundTime = 0 ;

        while ( completed < n ) {
            int shortestIndex = -1 ;
            int minRemaining = Integer.MAX_VALUE ;

            for ( int i = 0 ; i < n ; i++ ) {
                if ( processList.get(i).arrival <= currentTime && !isCompleted[i] && remainingTime[i] < minRemaining && remainingTime[i] > 0 ) {
                    minRemaining = remainingTime[i] ;
                    shortestIndex = i ;
                }
            }

            if ( shortestIndex == -1 ) {
                currentTime++ ;
                continue ;
            }

            remainingTime[shortestIndex]-- ;
            currentTime++ ;

            if ( remainingTime[shortestIndex] == 0 ) {
                completionTime[shortestIndex] = currentTime ;
                turnaroundTime[shortestIndex] = completionTime[shortestIndex] - processList.get(shortestIndex).arrival ;
                waitingTime[shortestIndex] = turnaroundTime[shortestIndex] - processList.get(shortestIndex).burst ;

                totalWaitingTime += waitingTime[shortestIndex] ;
                totalTurnaroundTime += turnaroundTime[shortestIndex] ;
                isCompleted[shortestIndex] = true ;
                completed++ ;

                bw.write ( "PID : " + processList.get(shortestIndex).pid +
                        ", Arrival Time : " + processList.get(shortestIndex).arrival +
                        ", Burst Time : " + processList.get(shortestIndex).burst +
                        ", Completion Time : " + completionTime[shortestIndex] +
                        ", Waiting Time : " + waitingTime[shortestIndex] +
                        ", Turnaround Time : " + turnaroundTime[shortestIndex] + "\n" ) ;
            }
        }

        bw.write ( "Average Waiting Time : " + ( ( double ) totalWaitingTime / n ) + "\n" ) ;
        bw.write ( "Average Turnaround Time : " + ( ( double ) totalTurnaroundTime / n ) + "\n" ) ;
        pq.add ( new AlgoResult ( "SJF Preemptive Scheduling : " , totalWaitingTime , totalTurnaroundTime ) ) ;
    }
}

