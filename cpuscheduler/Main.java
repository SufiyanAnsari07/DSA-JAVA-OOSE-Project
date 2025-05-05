package cpuscheduler;

import java.io.*;
import java.util.*;

public class Main {
    public static void main ( String[] args ) throws IOException {
        ArrayList<Process> processList = new ArrayList<Process>() ;
        Scanner sc = new Scanner ( System.in ) ;
        BufferedWriter writer = new BufferedWriter ( new FileWriter ( "cpuscheduler/Files/input.txt" ) ) ;

        while ( true ) {

            System.out.println ( "Enter process details (PID Arrival Burst [Priority]) or press 'q' to quit:" ) ;
            String input = sc.nextLine().trim() ;
            if ( input.equals ("q") ) break ;
            String[] details = input.split ( " " ) ;

            if ( details.length != 3 && details.length != 4 ) {
                System.out.println ( "Invalid input, please enter PID, Arrival, Burst, and optionally Priority." ) ;
                continue ;
            }

            int pid = Integer.parseInt ( details[0] ) ;
            int arrival = Integer.parseInt ( details[1] ) ;
            int burst = Integer.parseInt ( details[2] ) ;
            int priority = ( details.length == 4 ) ? Integer.parseInt ( details[3] ) : 0 ;

            writer.write ( pid + " " + arrival + " " + burst + " " + priority + "\n" ) ;

            Process process = new Process ( pid , arrival , burst , priority ) ;
            processList.add ( process ) ;
        }
        writer.close() ;

        BufferedWriter bw = new BufferedWriter ( new FileWriter ( "cpuscheduler/Files/output.txt" ) ) ;

        System.out.println ( "\nSelect the scheduling algorithm to use or press -1 to quit : " ) ;
        System.out.println ( "1. FCFS (First Come First Serve)" ) ;
        System.out.println ( "2. SJF (Shortest Job First)" ) ;
        System.out.println ( "3. Priority Scheduling" ) ;
        System.out.println ( "4. SJF Preemptive Scheduling" ) ;
        System.out.println ( "5. Round Robin Scheduling" ) ;

        PriorityQueue<AlgoResult> pq = new PriorityQueue<>() ;
        while ( true ) {
            int choice = sc.nextInt() ;
            if ( choice == -1 ) break ;
            switch ( choice ) {
                case 1 :
                    bw.write ( "FCFS Scheduling :\n" ) ;
                    Scheduler fcfsScheduler = new FCFSScheduler() ;
                    fcfsScheduler.schedule ( processList , bw , pq ) ;
                    break ;

                case 2 :
                    bw.write ( "\nSJF Scheduling :\n" ) ;
                    Scheduler sjfScheduler = new SJFScheduler() ;
                    sjfScheduler.schedule ( processList , bw , pq ) ;
                    break ;

                case 3 :
                    bw.write ( "\nPriority Scheduling :\n" ) ;
                    Scheduler priorityScheduler = new PriorityScheduler() ;
                    priorityScheduler.schedule ( processList , bw , pq ) ;
                    break ;

                case 4 :
                    bw.write ( "\nSJF Preemptive Scheduling :\n" ) ;
                    Scheduler sjfPreemptiveScheduler = new SJFPreemptiveScheduler() ;
                    sjfPreemptiveScheduler.schedule ( processList , bw , pq ) ;
                    break ;

                case 5 :
                    bw.write ( "\nRound Robin Scheduling :\n" ) ;
                    Scheduler roundRobinScheduler = new RRScheduler() ;
                    roundRobinScheduler.schedule ( processList , bw , pq ) ;
                    break ;

                default :
                    System.out.println ( "Invalid choice. Please select a valid option (1-5) or 'q' to quit." ) ;
                    continue ;
            }
            bw.flush() ;
        }
        bw.write ("\n----- Algorithm Ranking by Minimum Waiting time and turnaround time -----\n" ) ;
        int rank = 1 ;
        while ( !pq.isEmpty() ) {
            AlgoResult result = pq.poll() ;
            bw.write ( rank + ". " + result.name + " Waiting Time : " + String.format ( "%.2f" , 
                    ( double ) result.waitingTime / processList.size() ) + " Turnaround Time : " + 
                    String.format ( "%.2f" , ( double ) result.turnAroundTime / processList.size() ) + "\n" ) ;
            rank++ ;
        }
        bw.close() ;
        System.out.println ( "Scheduling completed. Check 'Files/output.txt' for the results." ) ;
    }
}

class AlgoResult implements Comparable<AlgoResult> {
    String name ;
    double waitingTime ;
    double turnAroundTime ;

    AlgoResult ( String name , double waitingTime , double turnAroundTime ) {
        this.name = name ;
        this.waitingTime = waitingTime ;
        this.turnAroundTime = turnAroundTime ;
    }

    @Override
    public int compareTo ( AlgoResult other ) {
        if ( this.waitingTime < other.waitingTime ) return -1 ;   
        if ( this.waitingTime > other.waitingTime ) return 1 ;  
        if ( this.turnAroundTime < other.turnAroundTime ) return -1 ;
        if ( this.turnAroundTime > other.turnAroundTime ) return 1 ;  
        return 0 ;                                    
    }
}
