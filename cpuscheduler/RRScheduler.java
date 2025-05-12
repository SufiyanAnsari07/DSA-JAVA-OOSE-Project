package cpuscheduler;

import java.io.* ;
import java.util.* ;

public class RRScheduler implements Scheduler {
    public void schedule ( ArrayList<Process> processList , BufferedWriter bw , PriorityQueue<AlgoResult> pq ) throws IOException {
        int timeQuantum = 2 ;
        int n = processList.size() ;
        Queue<Process> readyQueue = new LinkedList<>() ;
        int[] remainingBurst = new int[n] ;
        int[] completionTime = new int[n] ;
        int[] waitingTime = new int[n] ;
        int[] turnaroundTime = new int[n] ;
        boolean[] isEnqueued = new boolean[n] ;

        for ( int i = 0 ; i < n ; i++ ) {
            remainingBurst[i] = processList.get(i).burst ;
        }

        int currentTime = 0 ;
        int completed = 0 ;

        while ( completed < n ) {
            for ( int i = 0 ; i < n ; i++ ) {
                if ( !isEnqueued[i] && processList.get(i).arrival <= currentTime ) {
                    readyQueue.offer ( processList.get(i) ) ;
                    isEnqueued[i] = true ;
                }
            }

            if ( readyQueue.isEmpty() ) {
                currentTime++ ;
                continue ;
            }

            Process current = readyQueue.poll() ;
            int idx = processList.indexOf ( current ) ;

            int execTime = Math.min ( timeQuantum , remainingBurst[idx] ) ;
            remainingBurst[idx] -= execTime ;
            currentTime += execTime ;

            for ( int i = 0 ; i < n ; i++ ) {
                if ( !isEnqueued[i] && processList.get(i).arrival <= currentTime ) {
                    readyQueue.offer ( processList.get(i) ) ;
                    isEnqueued[i] = true ;
                }
            }

            if ( remainingBurst[idx] > 0 ) {
                readyQueue.offer ( current ) ;
            }
            else {
                completionTime[idx] = currentTime ;
                turnaroundTime[idx] = completionTime[idx] - current.arrival ;
                waitingTime[idx] = turnaroundTime[idx] - current.burst ;
                completed++ ;
            }
        }

        int totalWaitingTime = 0 , totalTurnaroundTime = 0 ;

        for ( int i = 0 ; i < n ; i++ ) {
            Process p = processList.get(i) ;
            bw.write ( "PID : " + p.pid +
                       ", Arrival Time : " + p.arrival +
                       ", Burst Time : " + p.burst +
                       ", Completion Time : " + completionTime[i] +
                       ", Waiting Time : " + waitingTime[i] +
                       ", Turnaround Time : " + turnaroundTime[i] + "\n" ) ;
            totalWaitingTime += waitingTime[i] ;
            totalTurnaroundTime += turnaroundTime[i] ;
        }

        bw.write ( "Average Waiting Time: " + ( ( double ) totalWaitingTime / n ) + "\n" ) ;
        bw.write ( "Average Turnaround Time: " + ( ( double ) totalTurnaroundTime / n ) + "\n" ) ;

        pq.add ( new AlgoResult ( "Round Robin Scheduling :" , totalWaitingTime , totalTurnaroundTime ) ) ;
    }
}
